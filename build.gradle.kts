plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "thriftlabs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    // 添加 GitHub Packages 仓库
    maven {
        url = uri("https://maven.pkg.github.com/thrift-labs/thrift-fmt-java")
        credentials {
            username = System.getenv("GITHUB_USERNAME") // GitHub 用户名
            password = System.getenv("GITHUB_TOKEN") // 个人访问令牌
        }
    }
}

dependencies {
    implementation("thriftlabs:thriftparser:0.0.4")
    implementation("thriftlabs:thriftfmt:0.0.2")
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.2.8")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("242.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
