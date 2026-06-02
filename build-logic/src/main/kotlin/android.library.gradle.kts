import org.gradle.api.artifacts.VersionCatalogsExtension

plugins {
    id("com.android.library")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
val centralCompileSdk = libs.findVersion("compileSdk").get().requiredVersion.toInt()
val centralMinSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
val centralJavaVersion = JavaVersion.toVersion(libs.findVersion("java").get().requiredVersion)

android {
    compileSdk = centralCompileSdk

    defaultConfig {
        minSdk = centralMinSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = centralJavaVersion
        targetCompatibility = centralJavaVersion
    }
}
