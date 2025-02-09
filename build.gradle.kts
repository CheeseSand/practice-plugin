import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Dependency.Kotlin.Version
    kotlin("plugin.serialization") version "2.1.10"

    id("io.papermc.paperweight.userdev") version "1.7.7"
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(platform("com.intellectualsites.bom:bom-1.18.x:1.22"))

    compileOnly("io.papermc.paper:paper-api:${Dependency.PaperAPI.Version}")
    paperDevBundle(Dependency.PaperAPI.Version)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependency.Coroutine.Version}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Dependency.Serialization.Version}")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io/")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "21"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "21"
}

java {
    toolchain.apply {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

val pluginName = rootProject.name.capitalize()
val packageName = rootProject.name

val aliasName = packageName.capitalize()

extra.apply {
    set("pluginName", pluginName)
    set("packageName", packageName)
    set("aliasName", aliasName)

    set("kotlinVersion", Dependency.Kotlin.Version)
    set("coroutineVersion", Dependency.Coroutine.Version)
    set("serializationVersion", Dependency.Serialization.Version)
}

tasks {
    processResources {
        outputs.upToDateWhen { false }
        filesMatching("*.yml") {
            expand(project.properties)
            expand(extra.properties)
        }
    }

    register<Jar>("pluginJar") {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")

        from(sourceSets["main"].output)
    }
}
