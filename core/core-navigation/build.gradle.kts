plugins {
    id("poc.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.pocsirius.core.navigation"
}

dependencies {
    // Navigation
    implementation(libs.navigation.compose)

    // Serialization — rotas type-safe
    implementation(libs.kotlinx.serialization.json)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}