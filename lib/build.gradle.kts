import java.net.URI
import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.dokka")
    id("maven-publish")
    signing
}
android {
    compileSdk = buildConfig.compileSdk

    defaultConfig {
        minSdk = buildConfig.minSdk
        targetSdk = buildConfig.targetSdk
        setProperty("archivesBaseName", "wlog")
    }

    buildTypes {
        getByName("release") {
            consumerProguardFiles("proguard.txt")
        }
    }
}

dependencies {
    implementation(libs.androidx.annotation)
    dokkaHtmlPlugin(libs.dokka.plugin)
}

tasks.dokkaHtml.configure {
    dokkaSourceSets {
        named("main") {
            noAndroidSdkLink.set(false)
        }
    }
}
val secrets = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

val ossrhUsername = secrets.getProperty("ossrhUsername", "")
val ossrhPassword = secrets.getProperty("ossrhPassword", "")

val androidSourcesJar by tasks.register<Jar>("androidSourcesJar") {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

val dokkaJavadocJar by tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.get().outputDirectory.get())
    archiveClassifier.set("javadoc")
}

val dokkaHtmlJar by tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.get().outputDirectory.get())
    archiveClassifier.set("html-doc")
}

artifacts {
    archives(androidSourcesJar)
    archives(dokkaJavadocJar)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "me.shikhov"
                artifactId = "wlog"
                version = buildConfig.libraryVersion

                artifact("$buildDir/outputs/aar/wlog-release.aar")
                artifact(androidSourcesJar)
                artifact(dokkaJavadocJar)

                pom {
                    name.set("wlog")
                    description.set("Android Log wrapper with handy methods to remove clutter & add formatted output")
                    url.set("https://github.com/andrey-shikhov/wlog")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("andrey-shikhov")
                            name.set("Andrew Shikhov")
                            email.set("andrew@shikhov.me")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/andrey-shikhov/wlog.git")
                        developerConnection.set("scm:git:ssh://github.com/andrey-shikhov/wlog.git")
                        url.set("https://github.com/andrey-shikhov/wlog")
                    }
                    withXml {
                        val depNode = asNode().appendNode("dependencies")

                        project.configurations.implementation.get().allDependencies.forEach {
                            val dNode = depNode.appendNode("dependency")
                            dNode.appendNode("groupId", it.group)
                            dNode.appendNode("artifactId", it.name)
                            dNode.appendNode("version", it.version)
                        }
                    }
                }
            }
        }

        repositories {
            maven {
                name = "sonatype"
                url = URI.create("https://oss.sonatype.org/service/local/staging/deploy/maven2/")

                credentials {
                    username = ossrhUsername
                    password = ossrhPassword
                }
            }
        }
    }

    signing {
        sign(publishing.publications["release"])
    }
}