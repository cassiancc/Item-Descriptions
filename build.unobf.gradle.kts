@file:Suppress("UnstableApiUsage")

plugins {
    id("net.fabricmc.fabric-loom-no-remap")
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
    maven ( "https://maven.shedaniel.me/" ) {
        name = "shedaniel (Cloth Config)"
    }
    maven ( "https://maven.terraformersmc.com/releases/" ) {
        name = "Terraformers (Mod Menu)"
    }
    maven ( "https://maven.wispforest.io/releases/" ) {
        name = "Wisp Forest Maven"
    }
    maven ( "https://api.modrinth.com/maven") {
        name = "Modrinth"
    }
    maven ( "https://maven2.bai.lol" ) {
        name = "WTHIT"
    }
    maven ( "https://repo.sleeping.town/" ) {
        name = "Sisby Maven"
    }
    maven ( "https://maven.parchmentmc.org" ) {
        name = "Parchment Mappings"
    }
    maven ( "https://maven.isxander.dev/releases") {
        name = "Xander Maven"
    }
    maven ( "https://maven.nucleoid.xyz" ) {
        name = "Nucleoid Maven (Polymer)"
    }
    maven ( "https://raw.githubusercontent.com/Fuzss/modresources/main/maven/") {
        name = "Fuzs Mod Resources"
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${property("deps.minecraft")}")
    implementation("net.fabricmc:fabric-loader:${property("deps.fabric-loader")}")
    implementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

    implementation("folk.sisby:kaleido-config:${property("deps.kaleido")}")
    include("folk.sisby:kaleido-config:${property("deps.kaleido")}")

    compileOnly("me.shedaniel.cloth:cloth-config-neoforge:19.0.147")
    compileOnly("dev.isxander:yet-another-config-lib:3.7.1+1.21.6-neoforge") {
        isTransitive = false
    }
    compileOnly("mcp.mobius.waila:wthit-api:neo-${property("deps.wthit_version")}")
    compileOnly("maven.modrinth:jade:19.3.1+neoforge")
    compileOnly("maven.modrinth:fast-item-frames:gAkkWcSn")
    compileOnly("maven.modrinth:puzzles-lib:W4cWteM4")

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

fabricApi {
    configureDataGeneration() {
        outputDirectory = file("$rootDir/src/main/generated")
        client = true
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
    file = tasks.jar.map { it.archiveFile.get() }

    // one of BETA, ALPHA, STABLE
    type = STABLE
    displayName = "${property("mod.name")} ${property("mod.version")} for ${stonecutter.current.version} Fabric"
    version = "${property("mod.version")}+${property("deps.minecraft")}-fabric"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("fabric")

    modrinth {
        projectId = property("publish.modrinth") as String
        accessToken = env.MODRINTH_API_KEY.orNull()
        if (!stonecutter.eval(mcVersion, ">1.21.10")) {
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