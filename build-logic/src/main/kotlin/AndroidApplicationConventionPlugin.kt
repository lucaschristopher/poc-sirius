import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")
            pluginManager.apply("com.google.dagger.hilt.android")
            pluginManager.apply("com.google.devtools.ksp")

            val libs = extensions
                .getByType(org.gradle.api.artifacts.VersionCatalogsExtension::class.java)
                .named("libs")

            extensions.configure<ApplicationExtension> {
                compileSdk = 35

                defaultConfig {
                    minSdk = 24
                    targetSdk = 35
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
            }

            pluginManager.withPlugin("org.jetbrains.kotlin.android") {
                extensions.configure<KotlinAndroidProjectExtension> {
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_17)
                    }
                }
            }

            dependencies {
                // Compose
                add("implementation", platform(libs.findLibrary("androidx-compose-bom").get()))
                add("implementation", libs.findLibrary("androidx-compose-ui").get())
                add("implementation", libs.findLibrary("androidx-compose-ui-graphics").get())
                add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
                add("implementation", libs.findLibrary("androidx-compose-material3").get())
                add("implementation", libs.findLibrary("androidx-compose-icons-extended").get())
                add("implementation", libs.findLibrary("androidx-activity-compose").get())

                // Navigation
                add("implementation", libs.findLibrary("navigation-compose").get())
                add("implementation", libs.findLibrary("hilt-navigation-compose").get())

                // Hilt
                add("implementation", libs.findLibrary("hilt-android").get())
                add("ksp", libs.findLibrary("hilt-compiler").get())

                // AndroidX
                add("implementation", libs.findLibrary("androidx-core-ktx").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())

                // Timber
                add("implementation", libs.findLibrary("timber").get())

                // Debug
                add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
                add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())

                // Test
                add("testImplementation", libs.findLibrary("junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-espresso-core").get())
                add("androidTestImplementation", platform(libs.findLibrary("androidx-compose-bom").get()))
                add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test-junit4").get())
            }
        }
    }
}
