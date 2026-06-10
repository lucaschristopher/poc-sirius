plugins {
    id("poc.android.application")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.pocsirius"

    defaultConfig {
        applicationId = "com.example.pocsirius"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug { }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // Módulos
    implementation(project(":core:core-common"))
    implementation(project(":core:core-navigation"))
    implementation(project(":core:core-network"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:faq"))
    implementation(project(":feature:home"))
}