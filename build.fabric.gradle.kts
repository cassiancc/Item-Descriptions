@file:Suppress("UnstableApiUsage")

plugins {
    id("net.fabricmc.fabric-loom-remap")
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
        name = "Parchment Mappings"
        url = uri("https://maven.parchmentmc.org")
        content {
            includeGroupAndSubgroups("org.parchmentmc")
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
}

dependencies {
    minecraft("com.mojang:minecraft:${property("deps.minecraft")}")
    mappings(loom.layered {
        officialMojangMappings()
        if (hasProperty("deps.parchment"))
            parchment("org.parchmentmc.data:parchment-${property("deps.parchment")}@zip")
    })
    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric-loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

    implementation("folk.sisby:kaleido-config:${property("deps.kaleido")}")
    include("folk.sisby:kaleido-config:${property("deps.kaleido")}")


    // YACL
    if (hasProperty("deps.yacl")) {
        modApi("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-fabric")
    } else {
        modCompileOnly("dev.isxander:yet-another-config-lib:3.7.1+1.21.6-fabric")
    }
    // Cloth Config
    if (hasProperty("deps.cloth_version")) {
        modApi("me.shedaniel.cloth:cloth-config-fabric:${property("deps.cloth_version")}")
    } else {
        modCompileOnly("me.shedaniel.cloth:cloth-config-fabric:19.0.147")
    }
    // Mod Menu
    if (hasProperty("deps.modmenu_version"))
        modApi("com.terraformersmc:modmenu:${property("deps.modmenu_version")}")
    else {
        modCompileOnly("com.terraformersmc:modmenu:15.0.0-beta.3")
    }
    // Useful Spyglass
    if (hasProperty("deps.useful_spyglass")) {
        modImplementation("maven.modrinth:useful-spyglass:${property("deps.useful_spyglass")}")
    } else {
        modCompileOnly("maven.modrinth:useful-spyglass:nX9apSkX")
    }
    // Fast Item Frames
    if (hasProperty("deps.fast_item_frames")) {
        modCompileOnly("maven.modrinth:fast-item-frames:${property("deps.fast_item_frames")}")
        modLocalRuntime("maven.modrinth:fast-item-frames:${property("deps.fast_item_frames")}")
    } else {
        modCompileOnly("maven.modrinth:fast-item-frames:7Km4n5kj")
    }
    if (hasProperty("deps.puzzles_lib")) {
        modCompileOnly("maven.modrinth:puzzles-lib:${property("deps.puzzles_lib")}")
        modLocalRuntime("maven.modrinth:puzzles-lib:${property("deps.puzzles_lib")}")
    } else {
        modCompileOnly("maven.modrinth:puzzles-lib:g7qeFvxG")
    }
    if (hasProperty("deps.forge_config_api_port")) {
        if (stonecutter.eval(mcVersion, "=1.19.2")) {
            modLocalRuntime("net.minecraftforge:forgeconfigapiport-fabric:${property("deps.forge_config_api_port")}")
        }
        else {
            modLocalRuntime("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:${property("deps.forge_config_api_port")}")
        }
    }
    // Jade
    if (hasProperty("deps.jade")) {
        modCompileOnly("maven.modrinth:jade:${property("deps.jade")}")
        modLocalRuntime("maven.modrinth:jade:${property("deps.jade")}")
    } else {
        modCompileOnly("maven.modrinth:jade:19.3.2+fabric")
    }
    // Glowcase
    modCompileOnly("maven.modrinth:glowcase:${property("deps.glowcase")}")
    // Polymer
    if (stonecutter.eval(mcVersion, ">1.21")) {
        modCompileOnly("eu.pb4:polymer-core:${property("deps.polymer")}")
    }
    // WTHIT
    modCompileOnly("mcp.mobius.waila:wthit-api:fabric-${property("deps.wthit_version")}")
    if (hasProperty("deps.badpackets_version")) {
        modLocalRuntime("mcp.mobius.waila:wthit:fabric-${property("deps.wthit_version")}")
        modLocalRuntime("lol.bai:badpackets:fabric-${property("deps.badpackets_version")}")
    }
    // Limelight
    if (hasProperty("deps.owo_version")) {
        modCompileOnly("io.wispforest:owo-lib:${property("deps.owo_version")}") // Limelight dependency
        modLocalRuntime("io.wispforest:owo-lib:${property("deps.owo_version")}") // Limelight dependency
    }
    if (hasProperty("deps.limelight")) {
        modCompileOnly("io.wispforest:limelight:${property("deps.limelight")}") // Limelight "API"
        modLocalRuntime("io.wispforest:limelight:${property("deps.limelight")}") // Limelight
    }

}

configurations.all {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:${property("deps.fabric-loader")}")
    }
}

stonecutter {
    replacements.string {
        direction = eval(current.version, ">1.21.10")
        replace("ResourceLocation", "Identifier")
    }
}

tasks {
    processResources {
        exclude("**/neoforge.mods.toml", "**/mods.toml")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(remapJar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }
}

loom.runs.named("server") {
    isIdeConfigGenerated = false
}

java {
    withSourcesJar()
    val javaCompat = if (stonecutter.eval(stonecutter.current.version, ">=1.21")) {
        JavaVersion.VERSION_21
    } else {
        JavaVersion.VERSION_17
    }
    sourceCompatibility = javaCompat
    targetCompatibility = javaCompat
}

val additionalVersionsStr = findProperty("publish.additionalVersions") as String?
val additionalVersions: List<String> = additionalVersionsStr
    ?.split(",")
    ?.map { it.trim() }
    ?.filter { it.isNotEmpty() }
    ?: emptyList()

publishMods {
    file = tasks.remapJar.map { it.archiveFile.get() }
    additionalFiles.from(tasks.remapSourcesJar.map { it.archiveFile.get() })

    // one of BETA, ALPHA, STABLE
    type = STABLE
    displayName = "${property("mod.name")} ${property("mod.version")} for ${stonecutter.current.version} Fabric"
    version = "${property("mod.version")}+${property("deps.minecraft")}-fabric"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("fabric")

    modrinth {
        projectId = property("publish.modrinth") as String
        accessToken = env.MODRINTH_API_KEY.orNull()
        if (!stonecutter.eval(mcVersion, ">1.21.11")) {
            minecraftVersions.add(stonecutter.current.version)
        } else {
            minecraftVersions.add(property("deps.minecraft").toString())
        }
        minecraftVersions.addAll(additionalVersions)
        requires("fabric-api")
        optional("cloth-config")
        optional("jade")
        optional("modmenu")
    }

    curseforge {
        projectId = property("publish.curseforge") as String
        accessToken = env.CURSEFORGE_API_KEY.orNull()
        minecraftVersions.add(stonecutter.current.version)
        minecraftVersions.addAll(additionalVersions)
        requires("fabric-api")
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