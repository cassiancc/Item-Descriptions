@file:Suppress("UnstableApiUsage")


plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("com.github.johnrengelman.shadow")
}

val loader = prop("loom.platform")!!
val minecraft: String = stonecutter.current.version
val common: Project = requireNotNull(stonecutter.node.sibling("")) {
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
    maven ( "https://cursemaven.com" )
    maven ( "https://api.modrinth.com/maven")
    maven ( "https://maven2.bai.lol" )
    maven ("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
}

dependencies {
    // Minecraft
    minecraft("com.mojang:minecraft:$minecraft")
    mappings("net.fabricmc:yarn:$minecraft+build.${common.mod.dep("yarn_build")}:v2")

    // Fabric
    modImplementation("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${common.mod.dep("fabric_api")}")

    // Cloth Config
    modApi("me.shedaniel.cloth:cloth-config-fabric:${common.mod.dep("cloth_version")}")

    // Mod Menu
    modApi("com.terraformersmc:modmenu:${common.mod.dep("modmenu_version")}")

    // Jade
    modImplementation("maven.modrinth:jade:${common.mod.dep("jade_fabric_version")}")

    // WTHIT
    modCompileOnly("mcp.mobius.waila:wthit-api:fabric-${common.mod.dep("wthit_version")}")
    modRuntimeOnly("mcp.mobius.waila:wthit:fabric-${common.mod.dep("wthit_version")}")
    modRuntimeOnly("lol.bai:badpackets:fabric-${common.mod.dep("badpackets_version")}")

    // Limelight
    if (stonecutter.eval(mcVersion, "=1.21.1")) {
        modImplementation("io.wispforest:owo-lib:${common.mod.dep("owo_version")}")
        modCompileOnly("io.wispforest:limelight:${common.mod.dep("limelight_version")}")
        modLocalRuntime("io.wispforest:limelight:${common.mod.dep("limelight_version")}")
    }

    // Useful Spyglass
    modImplementation("curse.maven:useful-spyglass-840027:${common.mod.dep("useful_spyglass_fabric_version")}")
    if (stonecutter.eval(mcVersion, "=1.19.2")) {
        modLocalRuntime("net.minecraftforge:forgeconfigapiport-fabric:${common.mod.dep("forge_config_api_port")}")
    }
    else {
        modLocalRuntime("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:${common.mod.dep("forge_config_api_port")}")
    }



    // Stonecutter/Arch
    commonBundle(project(common.path, "namedElements")) { isTransitive = false }
    shadowBundle(project(common.path, "transformProductionFabric")) { isTransitive = false }
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
        "version" to mod.version,
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