buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.kotlin.gradle)
        classpath(libs.dokka.classpath)
    }
}

val clean by tasks.creating(Delete::class) {
    delete(rootProject.buildDir)
}