plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    implementation(gradleKotlinDsl())
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "poc.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "poc.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidFeature") {
            id = "poc.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidApplication") {
            id = "poc.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}
