plugins {
    id("poc.android.library")
}

android {
    namespace = "com.example.pocsirius.core.common"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // Compose
    implementation(platform(libs.androidx.compose.bom))

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Coroutines
    implementation(libs.coroutines.android)

    // Network — só pra ter acesso ao HttpException no ErrorHandler
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)

    // Timber
    implementation(libs.timber)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}