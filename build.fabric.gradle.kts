@file:Suppress("UnstableApiUsage")

plugins {
    id("net.fabricmc.fabric-loom")
    id("dev.kikugie.postprocess.jsonlang")
    id("me.modmuss50.mod-publish-plugin")
    id("maven-publish")
}

val minecraft = stonecutter.current.version
val mcVersion = stonecutter.current.project.substringBeforeLast('-')

tasks.named<ProcessResources>("processResources") {
    fun prop(name: String) = project.property(name) as String

    val props = HashMap<String, String>().apply {
        this["version"] = prop("mod.version") + "+" + prop("deps.minecraft")
        this["minecraft"] = prop("mod.mc_dep_fabric")
    }

    filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "META-INF/mods.toml")) {
        expand(props)
    }

}

tasks.named("processResources") {
    dependsOn(":${stonecutter.current.project}:stonecutterGenerate")
}

version = "${property("mod.version")}+${property("deps.minecraft")}-fabric"
base.archivesName = property("mod.id") as String

//loom {
//    accessWidenerPath = rootProject.file("src/main/resources/${property("mod.id")}.accesswidener")
//}

jsonlang {
    languageDirectories = listOf("assets/${property("mod.id")}/lang")
    prettyPrint = true
}

repositories {
    mavenLocal()
    maven ( "https://maven.minecraftforge.net" ) {
        name = "Minecraft Forge"
    }
    maven {
        name = "shedaniel (Cloth Config)"
        url = uri("https://maven.shedaniel.me/")
        content {
            includeGroupAndSubgroups("me.shedaniel")
        }
    }
    maven {
        name = "Terraformers (Mod Menu)"
        url = uri("https://maven.terraformersmc.com/releases/")
        content {
            includeGroupAndSubgroups("com.terraformersmc")
        }
    }
    maven {
        name = "Wisp Forest Maven"
        url = uri("https://maven.wispforest.io/releases/")
        content {
            includeGroupAndSubgroups("io.wispforest")
        }
    }
    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
        content {
            includeGroupAndSubgroups("maven.modrinth")
        }
    }
    maven {
        name = "CurseForge"
        url = uri("https://cursemaven.com")
        content {
            includeGroupAndSubgroups("curse.maven")
        }
    }
    maven {
        name = "WTHIT"
        url = uri("https://maven2.bai.lol")
        content {
            includeGroupAndSubgroups("mcp.mobius.waila")
            includeGroupAndSubgroups("lol.bai")
        }
    }
    maven {
        name = "Sisby Maven"
        url = uri("https://repo.sleeping.town/")
        content {
            includeGroupAndSubgroups("folk.sisby")
        }
    }
    maven {
        name = "Xander Maven"
        url = uri("https://maven.isxander.dev/releases")
        content {
            includeGroupAndSubgroups("dev.isxander")
            includeGroupAndSubgroups("org.quiltmc.parsers")
        }
    }
    maven {
        name = "Nucleoid Maven (Polymer)"
        url = uri("https://maven.nucleoid.xyz")
        content {
            includeGroupAndSubgroups("eu.pb4")
            includeGroupAndSubgroups("xyz.nucleoid")
        }
    }
    maven {
        name = "Fuzs Mod Resources"
        url = uri("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
        content {
            includeGroupAndSubgroups("fuzs")
        }
    }
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:${property("deps.minecraft")}")
    implementation("net.fabricmc:fabric-loader:${property("deps.fabric-loader")}")

    implementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

    implementation("folk.sisby:kaleido-config:${property("deps.kaleido")}")
    include("folk.sisby:kaleido-config:${property("deps.kaleido")}")

    // YACL
    if (hasProperty("deps.yacl")) {
        compileOnly("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-fabric")
        localRuntime("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-fabric")
    } else {
        compileOnly("dev.isxander:yet-another-config-lib:3.7.1+1.21.6-neoforge") {
            isTransitive = false
        }
    }
    // Cloth Config
    if (hasProperty("deps.cloth")) {
        compileOnly("me.shedaniel.cloth:cloth-config-fabric:${property("deps.cloth")}")
    } else {
        compileOnly("me.shedaniel.cloth:cloth-config-neoforge:19.0.147")
    }
    // Mod Menu
    if (hasProperty("deps.modmenu")) {
        compileOnly("maven.modrinth:modmenu:${property("deps.modmenu")}")
        localRuntime("maven.modrinth:modmenu:${property("deps.modmenu")}")
    } else {
        compileOnly("com.terraformersmc:modmenu:18.0.0-alpha.8")
    }
    // Useful Spyglass
    if (hasProperty("deps.useful_spyglass")) {
        implementation("maven.modrinth:useful-spyglass:${property("deps.useful_spyglass")}")
    } else {
        compileOnly("maven.modrinth:useful-spyglass:e5CW6qWZ")
    }
    compileOnly("maven.modrinth:fast-item-frames:IFa0xZno")
    if (hasProperty("deps.puzzles_lib")) {
        compileOnly("maven.modrinth:puzzles-lib:${property("deps.puzzles_lib")}")
        localRuntime("maven.modrinth:puzzles-lib:${property("deps.puzzles_lib")}")
    } else {
        compileOnly("maven.modrinth:puzzles-lib:3OADGa7L")
    }
    if (hasProperty("deps.forge_config_api_port")) {
        localRuntime("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:${property("deps.forge_config_api_port")}")
    }
    // Jade
    if (hasProperty("deps.jade")) {
        compileOnly("maven.modrinth:jade:${property("deps.jade")}")
//        localRuntime("maven.modrinth:jade:${property("deps.jade")}")
    } else {
        compileOnly("maven.modrinth:jade:21.0.1+neoforge")
    }
    compileOnly("curse.maven:matrix-enchanting-1505679:7881482")
    compileOnly("eu.pb4:polymer-core:${property("deps.polymer")}")
    // WTHIT
    compileOnly("mcp.mobius.waila:wthit-api:fabric-${property("deps.wthit")}")
    if (hasProperty("deps.badpackets")) {
        localRuntime("mcp.mobius.waila:wthit:fabric-${property("deps.wthit")}")
        localRuntime("lol.bai:badpackets:fabric-${property("deps.badpackets")}")
    }
    implementation("cc.cassian.rrv:reliable-recipe-viewer-fabric:${property("deps.rrv")}")
}

configurations.all {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:${property("deps.fabric-loader")}")
    }
}

tasks {
    processResources {
        exclude("**/neoforge.mods.toml", "**/mods.toml")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(jar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }
}

loom.runs.named("server") {
    isIdeConfigGenerated = false
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

val additionalVersionsStr = findProperty("publish.additionalVersions") as String?
val additionalVersions: List<String> = additionalVersionsStr
    ?.split(",")
    ?.map { it.trim() }
    ?.filter { it.isNotEmpty() }
    ?: emptyList()

publishMods {
    file = tasks.jar.map { it.archiveFile.get() }

    // one of BETA, ALPHA, STABLE
    type = STABLE
    displayName = "${property("mod.name")} ${property("mod.version")} for ${stonecutter.current.version} Fabric"
    version = "${property("mod.version")}+${property("deps.minecraft")}-fabric"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("fabric")

    modrinth {
        additionalFile(tasks.named<org.gradle.jvm.tasks.Jar>("sourcesJar")) {
            type.set(SOURCES_JAR)
        }
        projectId = property("publish.modrinth") as String
        accessToken = env.MODRINTH_API_KEY.orNull()
        minecraftVersions.add(property("deps.minecraft").toString())
        minecraftVersions.addAll(additionalVersions)
        requires("fabric-api")
        optional("yacl")
        optional("jade")
        optional("modmenu")
    }

    curseforge {
        additionalFiles.from(tasks.named<org.gradle.jvm.tasks.Jar>("sourcesJar").map { it.archiveFile.get() })
        projectId = property("publish.curseforge") as String
        accessToken = env.CURSEFORGE_API_KEY.orNull()
        minecraftVersions.add(property("publish.curseforge_minecraft_version").toString())
        minecraftVersions.addAll(additionalVersions)
        requires("fabric-api")
        clientRequired=true
        serverRequired=false
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "cc.cassian.item-descriptions"
            artifactId = "item-descriptions-fabric"
            version = "${property("mod.version")}+${property("deps.minecraft")}"

            from(components["java"])
        }
    }
}