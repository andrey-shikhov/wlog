plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = buildConfig.compileSdk

    defaultConfig {
        minSdk = buildConfig.minSdk
        targetSdk = buildConfig.targetSdk
        versionCode = 4
        versionName = "1.1"
    }

    signingConfigs {
        create("release") {
            storeFile = file("keys/wlog.keystore.jks")
            storePassword = "android"
            keyAlias = "wlog"
            keyPassword = "wlogwlog"
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }

}

dependencies {
    implementation(projects.lib)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.ktx)
}