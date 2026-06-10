plugins {
    id("poc.android.hilt")
}

android {
    namespace = "com.example.pocsirius.core.network"
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(project(":core:core-common"))

    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Security
    implementation(libs.security.crypto)

    // Coroutines
    implementation(libs.coroutines.android)

    // Timber
    implementation(libs.timber)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}