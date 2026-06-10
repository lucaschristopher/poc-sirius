# POC Sirius

Prova de conceito de um app Android multimódulo, desenvolvida para validar decisões de arquitetura, stack tecnológica e padrões de desenvolvimento que serão adotados no projeto real.

---

## Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | Kotlin 2.2.10 |
| UI | Jetpack Compose + Material3 |
| Injeção de dependência | Hilt 2.56.2 |
| Processamento de anotações | KSP 2.2.10-2.0.2 |
| Navegação | Navigation Compose 2.8.0 |
| Rede | Retrofit 2.11.0 + OkHttp 4.12.0 |
| Serialização | kotlinx.serialization 1.7.3 |
| Armazenamento seguro | EncryptedSharedPreferences |
| Logs | Timber |
| Build system | AGP 8.6.0 + Gradle convention plugins |

---

## Estrutura de módulos

```
poc-sirius/
├── build-logic/                    ← convention plugins
│   └── src/main/kotlin/
│       ├── AndroidLibraryConventionPlugin.kt
│       ├── AndroidHiltConventionPlugin.kt
│       ├── AndroidFeatureConventionPlugin.kt
│       └── AndroidApplicationConventionPlugin.kt
├── app/                            ← módulo principal
├── core/
│   ├── core-common/                ← base de apresentação e utilitários
│   ├── core-navigation/            ← rotas centralizadas
│   └── core-network/               ← infraestrutura de rede
└── feature/
    ├── auth/                       ← login, cadastro, recuperação de senha
    ├── home/                       ← dashboard, rota coach, configurações
    └── faq/                        ← fale conosco
```

---

## Decisões de arquitetura

### Modularização por feature + core

Cada feature é um módulo Android independente. Módulos `core` centralizam infraestrutura compartilhada. Nenhuma feature conhece outra diretamente — a comunicação acontece via `core:core-navigation`.

```
:feature:auth    →  :core:core-common
                 →  :core:core-navigation
                 →  :core:core-network

:feature:home    →  :core:core-common
                 →  :core:core-navigation
                 →  :core:core-network

:app             →  todas as features (só :app conhece todas)
```

**Vantagem:** build incremental mais rápido, isolamento de responsabilidades, sem dependências cíclicas.

### Convention plugins com `build-logic`

Em vez de repetir configurações de Gradle em cada módulo, usamos convention plugins organizados em hierarquia:

```
poc.android.library      ← compileSdk, minSdk, jvmTarget
  └── poc.android.hilt   ← + Hilt + KSP
        └── poc.android.feature      ← + módulos core + Compose + Navigation
poc.android.application  ← configuração completa do :app
```

Mudanças de `compileSdk`, versões de dependências ou configurações de build são feitas em um único lugar e propagadas automaticamente para todos os módulos.

**Por que `build-logic` e não `buildSrc`:** o `build-logic` com `includeBuild` acessa o `libs.versions.toml` diretamente, tem cache independente e não invalida o build completo a cada mudança.

### MVVM com BaseViewModel genérico

Todas as ViewModels herdam de `BaseViewModel<S, A, E>` onde:

- `S : UiState` — estado imutável da tela (`data class` com `copy()`)
- `A : UiAction` — ações do usuário (`sealed interface`)
- `E : UiEvent` — eventos de efeito colateral como navegação (`sealed interface`)

```kotlin
abstract class BaseViewModel<S : UiState, A : UiAction, E : UiEvent>(
    initialState: S,
) : ViewModel() {
    val uiState: StateFlow<S>
    val uiEvent: Flow<E>        // Channel — entrega única, sem reemissão
    abstract fun onAction(action: A)
    protected fun updateState(reducer: (S) -> S)
    protected fun sendEvent(event: E)
}
```

**Por que `Channel` para eventos e não `SharedFlow`:** o `Channel` garante entrega única. Um `SharedFlow` pode reemitir o evento se a tela recompuser — causando navegação duplicada, por exemplo.

**Por que `UiState` como `sealed class`:** telas com estados mutuamente exclusivos (Loading, Content, Error) se beneficiam do `when` exaustivo no Composable. Telas complexas com estados parciais (paginação, loading parcial) usam `data class` com flags.

### Clean Architecture por feature

Cada feature segue a separação em camadas:

```
feature/auth/
├── data/
│   ├── model/          ← modelos de dados (@Serializable)
│   └── repository/     ← implementação do repositório
├── domain/
│   ├── model/          ← modelos de domínio
│   ├── repository/     ← interface do repositório
│   └── usecase/        ← casos de uso (fun interface)
└── presentation/
    ├── LoginUiState.kt
    ├── LoginUiAction.kt
    ├── LoginUiEvent.kt
    ├── LoginViewModel.kt
    └── LoginScreen.kt
```

O `safeRunDispatcher` é aplicado na camada de DI do UseCase — não no repositório. O repositório só chama a API. O UseCase recebe o resultado como `Result<T>`.

### Navegação por grafo de feature

Cada feature expõe uma função de extensão do `NavGraphBuilder`:

```kotlin
// :feature:auth
fun NavGraphBuilder.authGraph(
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateBack: () -> Unit,
)

// :feature:home
fun NavGraphBuilder.homeGraph(
    onNavigateToNotifications: () -> Unit,
    onNavigateToConfigurations: () -> Unit,
    onNavigateToFAQ: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateBack: () -> Unit,
)
```

O `:app` orquestra tudo no `AppNavGraph` — é o único módulo que conhece todas as features e todas as rotas. As rotas ficam centralizadas em `core:core-navigation/Routes.kt`.

**Por que callbacks e não `NavController` na feature:** o Google desaconselha expor o `NavController` para dentro de features. Callbacks são explícitos, rastreáveis e não criam acoplamento com a infraestrutura de navegação.

### Serialização com kotlinx.serialization

Adotamos `kotlinx.serialization` em vez de Gson ou Moshi pelos seguintes motivos:

- **Null safety real** — respeita os tipos Kotlin, não ignora `?`
- **Sem reflection** — usa geração de código via KSP, mais rápido e compatível com R8
- **Manutenção** — mantido pela JetBrains, alinhado com o ecossistema Kotlin
- **Multiplatform ready** — caso o projeto evolua para KMP no futuro

### Segurança de rede

O `AuthInterceptor` adiciona automaticamente os headers padrão e o token de autenticação em todas as requisições. O token é armazenado via `EncryptedSharedPreferences` com fallback em caso de corrupção da chave.

As URLs de API são centralizadas no `SecurityProperties` dentro de `core:core-common` e variam por `BuildType` — sem strings hardcodadas nos módulos de rede.

---

## Padrão de testes

Os testes de ViewModel seguem o padrão **Given/When/Then** com comparação de objetos inteiros:

```kotlin
@Test
fun `dado credenciais invalidas, quando login clicado, entao estado deve ser Unauthorized`() = runTest {
    // Given
    coEvery {
        loginUseCase(email = "joao@gmail.com", password = "senhaerrada")
    } returns Result.Failure(Error.Unauthorized)

    val expectedState = LoginUiState.Error(error = Error.Unauthorized)

    viewModel.onAction(LoginUiAction.EmailChanged("joao@gmail.com"))
    viewModel.onAction(LoginUiAction.PasswordChanged("senhaerrada"))

    // When
    viewModel.onAction(LoginUiAction.LoginClicked)

    // Then — objeto inteiro, não propriedade isolada
    assertEquals(expectedState, viewModel.uiState.value)
}
```

**Stack de testes:** JUnit + MockK + Turbine (Flow testing) + `MainCoroutineRule`.

**Por que evitar `any()` no MockK:** dados exatos nos mocks garantem que o teste está validando o contrato correto, não apenas que alguma chamada foi feita.

---

## Simulação de API

Para esta POC, a camada de dados foi simplificada — o `AuthRepositoryImpl` simula latência e resultado aleatório sem depender de backend:

```kotlin
override suspend fun login(email: String, password: String) {
    delay(1_500L)
    if (Random.nextBoolean()) {
        sessionStorage.saveToken(MOCK_TOKEN)
    } else {
        throw HttpException(Response.error<Any>(401, "{}".toResponseBody(...)))
    }
}
```

Em produção, o repositório chama a `AuthApi` via Retrofit e o `safeRunDispatcher` captura as exceções convertendo para `Result.Failure`.

---

## Referências

- [Now in Android](https://github.com/android/nowinandroid) — referência de modularização e convention plugins
- [Philipp Lackner — Navigation in Multi-Module Apps](https://www.youtube.com/watch?v=lv1raAvwcgI) — padrão de grafo por feature
- [Google — Guide to app architecture](https://developer.android.com/topic/architecture)
- [Kotlin Symbol Processing](https://github.com/google/ksp)
