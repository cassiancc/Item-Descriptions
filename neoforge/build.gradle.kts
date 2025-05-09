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
    neoForge()
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
    get("developmentNeoForge").extendsFrom(commonBundle)
}

val mcVersion = stonecutter.current.project.substringBeforeLast('-')

repositories {
    maven("https://maven.neoforged.net/releases/")
    maven ( "https://maven.shedaniel.me/" )
    maven ( "https://maven.terraformersmc.com/releases/" )
    maven ( "https://maven.wispforest.io/releases/" )
    maven ( "https://api.modrinth.com/maven")
    maven ( "https://maven2.bai.lol" )
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings(loom.layered {
        mappings("net.fabricmc:yarn:$minecraft+build.${common.mod.dep("yarn_build")}:v2")
        common.mod.dep("neoforge_patch").takeUnless { it.startsWith('[') }?.let {
            mappings("dev.architectury:yarn-mappings-patch-neoforge:$it")
        }
    })
    "neoForge"("net.neoforged:neoforge:${common.mod.dep("neoforge_loader")}")
    "io.github.llamalad7:mixinextras-neoforge:${mod.dep("mixin_extras")}".let {
        implementation(it)
        include(it)
    }

    // Cloth Config
    modApi("me.shedaniel.cloth:cloth-config-neoforge:${common.mod.dep("cloth_version")}")

    // Jade
    modImplementation("maven.modrinth:jade:${common.mod.dep("jade_version")}")

    //WTHIT
    modCompileOnly("mcp.mobius.waila:wthit-api:neo-${common.mod.dep("wthit_version")}")
    modRuntimeOnly("mcp.mobius.waila:wthit:neo-${common.mod.dep("wthit_version")}")
    modRuntimeOnly("lol.bai:badpackets:neo-${common.mod.dep("badpackets_version")}")

    // Useful Spyglass
    if (stonecutter.eval(mcVersion, "<1.21.3")) {
        modImplementation("maven.modrinth:useful-spyglass:${common.mod.dep("useful_spyglass")}")
    }

    commonBundle(project(common.path, "namedElements")) { isTransitive = false }
    shadowBundle(project(common.path, "transformProductionNeoForge")) { isTransitive = false }
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

tasks.jar {
    archiveClassifier = "dev"
}

tasks.remapJar {
    injectAccessWidener = true
    input = tasks.shadowJar.get().archiveFile
    archiveClassifier = null
    dependsOn(tasks.shadowJar)
}

tasks.shadowJar {
    configurations = listOf(shadowBundle)
    archiveClassifier = "dev-shadow"
    exclude("fabric.mod.json", "architectury.common.json")
}

tasks.processResources {
    properties(listOf("META-INF/neoforge.mods.toml", "pack.mcmeta"),
        "id" to mod.id,
        "name" to mod.name,
        "version" to mod.version + "+" + minecraft,
        "minecraft" to common.mod.prop("mc_dep_forgelike")
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
    displayName = "${mod.name} ${mod.version} for NeoForge $mcVersion"
    version = mod.version
    changelog = rootProject.file("CHANGELOG-LATEST.md").readText()
    type = STABLE
    modLoaders.add("neoforge")

    dryRun = false;

    modrinth {
        projectId = property("publish.modrinth").toString()
        accessToken = prop.getProperty("modrinth_token")
        minecraftVersions.add(mcVersion)
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
    }
}