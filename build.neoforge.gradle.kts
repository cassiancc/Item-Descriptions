plugins {
    id("net.neoforged.moddev")
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
        this["minecraft"] = prop("mod.mc_dep_forge")
    }

    filesMatching(listOf("neoforge.mod.json", "META-INF/neoforge.mods.toml", "META-INF/mods.toml")) {
        expand(props)
    }
}

version = "${property("mod.version")}+${property("deps.minecraft")}-neoforge"
base.archivesName = property("mod.id") as String

jsonlang {
    languageDirectories = listOf("assets/${property("mod.id")}/lang")
    prettyPrint = true
}


repositories {
    mavenLocal()
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
    maven {
        name = "Kotlin for Forge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
        content {
            includeGroupAndSubgroups("thedarkcolour")
        }
    }
    flatDir { dirs("libs") }
    mavenCentral()
}

neoForge {
    version = property("deps.neoforge") as String
    validateAccessTransformers = true

    runs {
        register("client") {
            gameDirectory = file("run/")
            client()
        }
        register("server") {
            gameDirectory = file("run/")
            server()
        }
    }

    mods {
        register(property("mod.id") as String) {
            sourceSet(sourceSets["main"])
        }
    }
    sourceSets["main"].resources.srcDir("src/main/generated")
}

tasks {
    processResources {
        exclude("**/neoforge.mod.json", "**/*.accesswidener", "**/mods.toml")
    }

    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(jar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }
}

dependencies {
    implementation("folk.sisby:kaleido-config:${property("deps.kaleido")}")
    jarJar("folk.sisby:kaleido-config:${property("deps.kaleido")}")
    if (stonecutter.eval(mcVersion, "<1.21.9")) {
        "additionalRuntimeClasspath"("folk.sisby:kaleido-config:${property("deps.kaleido")}")
    }



    // YACL
    if (hasProperty("deps.yacl")) {
        compileOnly("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-neoforge")
        compileOnly("thedarkcolour:kotlinforforge-neoforge:5.10.0")
    } else {
        compileOnly("dev.isxander:yet-another-config-lib:3.8.0+1.21.9-neoforge")
    }
    // Cloth Config
    if (hasProperty("deps.cloth")) {
        compileOnly("me.shedaniel.cloth:cloth-config-neoforge:${property("deps.cloth")}")
        runtimeOnly("me.shedaniel.cloth:cloth-config-neoforge:${property("deps.cloth")}")
    } else {
        compileOnly("me.shedaniel.cloth:cloth-config-neoforge:19.0.147")
    }
    // Useful Spyglass
    if (hasProperty("deps.useful_spyglass")) {
        implementation("maven.modrinth:useful-spyglass:${property("deps.useful_spyglass")}")
    } else {
        compileOnly("maven.modrinth:useful-spyglass:e5CW6qWZ")
    }
    // Fast Item Frames
    if (hasProperty("deps.fast_item_frames")) {
        compileOnly("maven.modrinth:fast-item-frames:${property("deps.fast_item_frames")}")
        runtimeOnly("maven.modrinth:fast-item-frames:${property("deps.fast_item_frames")}")
    }
    if (hasProperty("deps.puzzles_lib")) {
        compileOnly("maven.modrinth:puzzles-lib:${property("deps.puzzles_lib")}")
        runtimeOnly("maven.modrinth:puzzles-lib:${property("deps.puzzles_lib")}")
    } else {
        compileOnly("maven.modrinth:puzzles-lib:W4cWteM4")
    }
    if (hasProperty("deps.forge_config_api_port")) {
        runtimeOnly("fuzs.forgeconfigapiport:forgeconfigapiport-neoforge:${property("deps.forge_config_api_port")}")
    }
    // Jade
    if (hasProperty("deps.jade")) {
        compileOnly("maven.modrinth:jade:${property("deps.jade")}")
        runtimeOnly("maven.modrinth:jade:${property("deps.jade")}")
    } else {
        compileOnly("maven.modrinth:jade:21.0.1+neoforge")
    }
    // WTHIT
    compileOnly("mcp.mobius.waila:wthit-api:neo-${property("deps.wthit")}")
    if (hasProperty("deps.badpackets_version")) {
        runtimeOnly("mcp.mobius.waila:wthit:neo-${property("deps.wthit")}")
        runtimeOnly("lol.bai:badpackets:neo-${property("deps.badpackets_version")}")
    }
    if (stonecutter.eval(mcVersion, "=1.21.1")) {
        compileOnly("maven.modrinth:quark:${property("deps.quark")}")
        runtimeOnly("maven.modrinth:quark:${property("deps.quark")}")
        runtimeOnly("maven.modrinth:zeta:${property("deps.zeta")}")

    }
    implementation("cc.cassian.rrv:reliable-recipe-viewer-neoforge:${property("deps.rrv")}")

}

java {
    withSourcesJar()
    val javaCompat = JavaVersion.VERSION_25
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
    additionalFiles.from(tasks.named<org.gradle.jvm.tasks.Jar>("sourcesJar").map { it.archiveFile.get() })

    type = STABLE
    displayName = "${property("mod.name")} ${property("mod.version")} for ${stonecutter.current.version} Neoforge"
    version = "${property("mod.version")}+${property("deps.minecraft")}-neoforge"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("neoforge")

    modrinth {
        projectId = property("publish.modrinth") as String
        accessToken = env.MODRINTH_API_KEY.orNull()
        minecraftVersions.add(property("deps.minecraft").toString())
        minecraftVersions.addAll(additionalVersions)
        optional("yacl")
        optional("jade")
    }

    curseforge {
        projectId = property("publish.curseforge") as String
        accessToken = env.CURSEFORGE_API_KEY.orNull()
        minecraftVersions.add(property("publish.curseforge_minecraft_version").toString())
        minecraftVersions.addAll(additionalVersions)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "cc.cassian.item-descriptions"
            artifactId = "item-descriptions-neoforge"
            version = "${property("mod.version")}+${property("deps.minecraft")}"

            from(components["java"])
        }
    }
}