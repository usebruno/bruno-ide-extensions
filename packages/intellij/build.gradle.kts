plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.10.1"
}

group = "com.usebruno.plugin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2024.1")
    type.set("IC") // Target IDE Platform

    plugins.set(
        listOf(
            "org.jetbrains.plugins.textmate"
        )
    )
}

tasks {
    val copyVscodeTextMateBundle by registering(Copy::class) {
        from("../vscode/")
        include("package.json")
        include("language-configuration.json")
        include("syntaxes/bruno.tmLanguage.json")
        into(layout.buildDirectory.dir("resources/main/textmate/bruno-bundle"))
    }

    processResources {
        dependsOn(copyVscodeTextMateBundle)
    }

    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    patchPluginXml {
        sinceBuild.set("241")
        untilBuild.set("241.*")
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
