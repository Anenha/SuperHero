plugins {
    id("android.library")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.anenha.superhero.core.network"
    
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))
    
    implementation(libs.androidx.activity.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.kotlinx.serialization)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
