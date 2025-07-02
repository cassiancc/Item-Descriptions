@file:Suppress("UnstableApiUsage")

import java.io.FileInputStream
import java.util.*


plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("com.github.johnrengelman.shadow")
    id("maven-publish")
    id("me.modmuss50.mod-publish-plugin") version "0.8.4"
}

val loader = prop("loom.platform")!!
val minecraft: String = stonecutter.current.version
val common: Project = requireNotNull(stonecutter.node.sibling("")?.project) {
    "No common project for $project"
}

version = "${mod.version}+$minecraft"
base {
    archivesName.set("${mod.id}-$loader")
}
architectury {
    platformSetupLoomIde()
    fabric()
}

val commonBundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

val shadowBundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

configurations {
    compileClasspath.get().extendsFrom(commonBundle)
    runtimeClasspath.get().extendsFrom(commonBundle)
    get("developmentFabric").extendsFrom(commonBundle)
}

val mcVersion = stonecutter.current.project.substringBeforeLast('-')

repositories {
    maven ( "https://maven.shedaniel.me/" )
    maven ( "https://maven.terraformersmc.com/releases/" )
    maven ( "https://maven.wispforest.io/releases/" )
    maven ( "https://api.modrinth.com/maven")
    maven ( "https://maven2.bai.lol" )
    maven ("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
    maven ("https://repo.sleeping.town/" )
    maven ( "https://maven.nucleoid.xyz" ) // Polymer
    maven("https://maven.isxander.dev/releases") {
        name = "Xander Maven"
    }
}

dependencies {
    // Minecraft
    minecraft("com.mojang:minecraft:$minecraft")
    mappings("net.fabricmc:yarn:$minecraft+build.${common.mod.dep("yarn_build")}:v2")

    // Fabric
    modImplementation("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${common.mod.dep("fabric_api")}")

    // Kaleido Config
    implementation("folk.sisby:kaleido-config:${common.mod.dep("kaleido")}")
    include("folk.sisby:kaleido-config:${common.mod.dep("kaleido")}")

    if (stonecutter.eval(mcVersion, "<1.21.8")) { // Runtime dependencies, used on release versions

        // Config APIs
        modApi("dev.isxander:yet-another-config-lib:${common.mod.dep("yacl")}-fabric")
        modApi("me.shedaniel.cloth:cloth-config-fabric:${common.mod.dep("cloth_version")}")


        // Mod Menu
        modApi("com.terraformersmc:modmenu:${common.mod.dep("modmenu_version")}")

        // Jade
        modImplementation("maven.modrinth:jade:${common.mod.dep("jade_fabric_version")}")

        // WTHIT
        if (stonecutter.eval(mcVersion, "<1.21.5")) {
            modRuntimeOnly("mcp.mobius.waila:wthit:fabric-${common.mod.dep("wthit_version")}")
            modRuntimeOnly("lol.bai:badpackets:fabric-${common.mod.dep("badpackets_version")}")
        }

        if (stonecutter.eval(mcVersion, "=1.21.1")) {
            modImplementation("io.wispforest:owo-lib:${common.mod.dep("owo_version")}") // Limelight dependency
            modCompileOnly("io.wispforest:limelight:${common.mod.dep("limelight_version")}") // Limelight "API"
            modLocalRuntime("io.wispforest:limelight:${common.mod.dep("limelight_version")}") // Limelight
        }
        if (stonecutter.eval(mcVersion, "=1.21.5")) {
            modImplementation("io.wispforest:owo-lib:${common.mod.dep("owo_version")}") // Limelight dependency
            modImplementation("io.wispforest:limelight:${common.mod.dep("limelight_version")}") // Limelight
        }

        // Useful Spyglass and Glowcase
        if (stonecutter.eval(mcVersion, "<1.21.4")) {
    //        modImplementation("maven.modrinth:useful-spyglass:${common.mod.dep("useful_spyglass")}-fabric,$minecraft") // Useful Spyglass - optional compat
    //        modLocalRuntime("maven.modrinth:glowcase:${common.mod.dep("glowcase")}") // Glowcase - optional compat
            modLocalRuntime("maven.modrinth:placeholder-api:${common.mod.dep("placeholder_api")}") // Glowcase dependency
        }
        if (stonecutter.eval(mcVersion, "=1.19.2")) {
            modLocalRuntime("net.minecraftforge:forgeconfigapiport-fabric:${common.mod.dep("forge_config_api_port")}")
        }
        else {
            modLocalRuntime("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:${common.mod.dep("forge_config_api_port")}")
        }

        //Polymer
        if (stonecutter.eval(mcVersion, ">1.21")) {
//            modLocalRuntime("eu.pb4:polymer-core:${common.mod.dep("polymer")}")
            if (stonecutter.eval(mcVersion, "<1.21.5")) {
                modLocalRuntime("maven.modrinth:puzzles-lib:${common.mod.dep("puzzles_lib")}")
                modLocalRuntime("maven.modrinth:fast-item-frames:${common.mod.dep("fast_item_frames")}")
            }
        }
    }
    else {
        // Mod Menu
        modCompileOnly("com.terraformersmc:modmenu:${common.mod.dep("modmenu_version")}")
        modCompileOnly("maven.modrinth:jade:${common.mod.dep("jade_fabric_version")}")

    }

    // Stonecutter/Arch
    commonBundle(project(common.path, "namedElements")) { isTransitive = false }
    shadowBundle(project(common.path, "transformProductionFabric")) { isTransitive = false }
}

configurations.all {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")
    }
}

loom {
    decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }

    runConfigs.all {
        isIdeConfigGenerated = true
        runDir = "../../../run"
        vmArgs("-Dmixin.debug.export=true")
    }
}

java {
    withSourcesJar()
    val java = if (stonecutter.eval(minecraft, ">=1.20.5"))
        JavaVersion.VERSION_21 else JavaVersion.VERSION_17
    targetCompatibility = java
    sourceCompatibility = java
}

tasks.shadowJar {
    configurations = listOf(shadowBundle)
    archiveClassifier = "dev-shadow"
}

tasks.remapJar {
    injectAccessWidener = true
    input = tasks.shadowJar.get().archiveFile
    archiveClassifier = null
    dependsOn(tasks.shadowJar)
}

tasks.jar {
    archiveClassifier = "dev"
}

tasks.processResources {
    properties(listOf("fabric.mod.json"),
        "id" to mod.id,
        "name" to mod.name,
        "version" to mod.version + "+" + minecraft,
        "minecraft" to common.mod.prop("mc_dep_fabric")
    )
}

tasks.build {
    group = "versioned"
    description = "Must run through 'chiseledBuild'"
}

tasks.register<Copy>("buildAndCollect") {
    group = "versioned"
    description = "Must run through 'chiseledBuild'"
    from(tasks.remapJar.get().archiveFile, tasks.remapSourcesJar.get().archiveFile)
    into(rootProject.layout.buildDirectory.file("libs/${mod.version}/$loader"))
    dependsOn("build")
}

publishing {
    publications {
        create<MavenPublication>("mod") {
            artifact(rootProject.layout.buildDirectory.file("libs/${mod.version}/$loader/${mod.id}-$loader-$version.jar"))
            artifactId = mod.id + "-" + loader
            group = mod.group
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}

val prop = Properties().apply {
    val properties = File(rootProject.rootDir, "local.properties")
    if (properties.exists())
        load(FileInputStream(properties))
}

publishMods {
    file = (rootProject.layout.buildDirectory.file("libs/${mod.version}/$loader/${mod.id}-$loader-${mod.version}+${mcVersion}.jar"))
    displayName = "${mod.name} ${mod.version} for Fabric $mcVersion"
    version = mod.version
    changelog = rootProject.file("CHANGELOG-LATEST.md").readText()
    type = STABLE
    modLoaders.add("fabric")

    dryRun = false;

    modrinth {
        projectId = property("publish.modrinth").toString()
        accessToken = prop.getProperty("modrinth_token")
        minecraftVersions.add(mcVersion)
        requires {
            slug = "fabric-api"
        }
        optional {
            slug = "cloth-config"
        }
    }

    curseforge {
        projectId = property("publish.curseforge").toString()
        accessToken = prop.getProperty("curseforge_token")
        if (stonecutter.eval(mcVersion, "25w19a")) {
            minecraftVersions.add("1.21.6-snapshot")
        } else {
            minecraftVersions.add(mcVersion)
        }
        requires {
            slug = "fabric-api"
        }
    }
}