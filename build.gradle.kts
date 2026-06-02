buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.hilt.gradlePlugin)
        classpath(libs.ksp.gradlePlugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.secrets) apply false
}

subprojects {
    configurations.all {
        resolutionStrategy {
            eachDependency {
                if (requested.group == "org.jetbrains.kotlinx" && requested.name == "kotlinx-metadata-jvm") {
                    useTarget("org.jetbrains.kotlin:kotlin-metadata-jvm:2.3.21")
                }
                if (requested.group == "org.jetbrains.kotlin" && requested.name == "kotlin-metadata-jvm") {
                    useVersion("2.3.21")
                }
            }
        }
    }
}

