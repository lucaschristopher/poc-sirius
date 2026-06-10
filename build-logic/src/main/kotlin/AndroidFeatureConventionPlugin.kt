import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("poc.android.hilt")

            val libs = extensions
                .getByType(org.gradle.api.artifacts.VersionCatalogsExtension::class.java)
                .named("libs")

            dependencies {
                // Módulos core
                add("implementation", project(":core:core-common"))
                add("implementation", project(":core:core-navigation"))
                add("implementation", project(":core:core-network"))

                // Network
                add("implementation", libs.findLibrary("retrofit").get())
                add("implementation", libs.findLibrary("kotlinx-serialization-json").get())

                // Compose
                add("implementation", platform(libs.findLibrary("androidx-compose-bom").get()))
                add("implementation", libs.findLibrary("androidx-compose-ui").get())
                add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
                add("implementation", libs.findLibrary("androidx-compose-material3").get())

                // Navigation
                add("implementation", libs.findLibrary("hilt-navigation-compose").get())
                add("implementation", libs.findLibrary("navigation-compose").get())

                // ViewModel
                add("implementation", libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())

                // Debug
                add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())

                // Test
                add("testImplementation", libs.findLibrary("junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-espresso-core").get())
            }
        }
    }
}