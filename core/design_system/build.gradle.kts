plugins {
    id("android.library")
    id("android.compose")
}

android {
    namespace = "com.anenha.superhero.core.designsystem"
}

dependencies {
    implementation(libs.androidx.compose.ui.text.google.fonts)
}
