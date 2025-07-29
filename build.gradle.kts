import java.io.FileInputStream
import java.util.*

plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
}

val minecraft = stonecutter.current.version
val mcVersion = stonecutter.current.project.substringBeforeLast('-')

version = "${mod.version}+$minecraft"
base {
    archivesName.set("${mod.id}-common")
}

architectury.common(stonecutter.tree.branches.mapNotNull {
    if (stonecutter.current.project !in it) null
    else it.project.prop("loom.platform")
})

repositories {
    maven ( "https://api.modrinth.com/maven") // Jade/Useful Spyglass
    maven ( "https://maven2.bai.lol" ) // WTHIT
    maven ( "https://maven.nucleoid.xyz") // Polymer
    maven ( "https://maven.terraformersmc.com/") // EMI
    maven ( "https://repo.sleeping.town/" ) // Kaleido Config
    maven ( "https://maven.isxander.dev/releases") // YACL
    maven ( "https://maven.parchmentmc.org") // Parchment
    flatDir { dirs("libs") }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings(loom.layered {
        officialMojangMappings()
        if (stonecutter.eval(mcVersion, "<1.21.6")) {
            parchment("org.parchmentmc.data:parchment-${mod.dep("parchment")}@zip")
    }})
    modImplementation("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")
    "io.github.llamalad7:mixinextras-common:${mod.dep("mixin_extras")}".let {
        annotationProcessor(it)
        implementation(it)
    }

    modApi("me.shedaniel.cloth:cloth-config-fabric:${mod.dep("cloth_version")}")
    implementation("folk.sisby:kaleido-config:${mod.dep("kaleido")}")

    modCompileOnly("maven.modrinth:jade:${mod.dep("jade_fabric_version")}")
    modCompileOnly("mcp.mobius.waila:wthit-api:fabric-${mod.dep("wthit_version")}")
    modCompileOnly("maven.modrinth:useful-spyglass:${mod.dep("useful_spyglass")}-fabric")
    modCompileOnly("maven.modrinth:fast-item-frames:${mod.dep("fast_item_frames")}")
    modCompileOnly("maven.modrinth:puzzles-lib:${mod.dep("puzzles_lib")}")
    modCompileOnly("maven.modrinth:glowcase:${mod.dep("glowcase")}")
    if (stonecutter.eval(mcVersion, "<1.21.2")) {
        modCompileOnly("dev.emi:emi-xplat-intermediary:${mod.dep("emi")}+$minecraft")
    }
    modCompileOnly("dev.isxander:yet-another-config-lib:${mod.dep("yacl")}-fabric")


    if (stonecutter.eval(mcVersion, ">1.21")) {
        modCompileOnly("eu.pb4:polymer-core:${mod.dep("polymer")}")
    }
    if (stonecutter.eval(mcVersion, "=1.21.1")) {
        modCompileOnly("maven.local:quark:4.0-463")
    }
    if (stonecutter.eval(mcVersion, "=1.20.1")) {
        modCompileOnly("maven.local:quark:4.0-463-deobf")
    }

}

loom {
    decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }
}

java {
    withSourcesJar()
    val java = if (stonecutter.eval(minecraft, ">=1.20.5"))
        JavaVersion.VERSION_21 else JavaVersion.VERSION_17
    targetCompatibility = java
    sourceCompatibility = java
}

tasks.build {
    group = "versioned"
    description = "Must run through 'chiseledBuild'"
}
