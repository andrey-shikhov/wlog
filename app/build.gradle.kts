plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    signingConfigs {
        create("release") {
            keyAlias("wlog")
            keyPassword("wlogwlog")
            storeFile(file("keys/wlog.keystore.jks"))
            storePassword("android")
        }
    }
    compileSdkVersion(30)

    defaultConfig {
        applicationId("me.shikhov.wlogtest")
        minSdkVersion(16)
        targetSdkVersion(30)
        versionCode(3)
        versionName("1.1")
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            minifyEnabled(true)
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":wlog"))
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.core:core-ktx:1.3.2")
}