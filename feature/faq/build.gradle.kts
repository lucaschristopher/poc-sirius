plugins {
    id("poc.android.feature")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.pocsirius.feature.faq"
    buildFeatures { compose = true }
}