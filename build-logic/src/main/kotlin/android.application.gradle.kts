import org.gradle.api.artifacts.VersionCatalogsExtension

plugins {
    id("com.android.application")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
val centralCompileSdk = libs.findVersion("compileSdk").get().requiredVersion.toInt()
val centralMinSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
val centralTargetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
val centralJavaVersion = JavaVersion.toVersion(libs.findVersion("java").get().requiredVersion)

android {
    compileSdk = centralCompileSdk

    defaultConfig {
        applicationId = "com.anenha.superhero"
        minSdk = centralMinSdk
        targetSdk = centralTargetSdk
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = centralJavaVersion
        targetCompatibility = centralJavaVersion
    }
}
