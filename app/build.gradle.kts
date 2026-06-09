plugins {
    id("android.application")
    id("android.compose")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.anenha.superhero"
}

dependencies {
    implementation(project(":core:design_system"))
    implementation(project(":core:network"))
    implementation(project(":domain"))
    implementation(project(":features:archive"))
    implementation(project(":features:comparison"))
    
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigationevent)
    implementation(libs.androidx.navigationevent.compose)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
