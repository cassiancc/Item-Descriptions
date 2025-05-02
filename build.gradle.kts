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
    else it.prop("loom.platform")
})

repositories {
    maven ( "https://api.modrinth.com/maven") // Jade/Useful Spyglass
    maven ( "https://maven2.bai.lol" ) // WTHIT
    maven ( "https://maven.nucleoid.xyz") // Polymer
    maven ("https://maven.terraformersmc.com/") // EMI
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings("net.fabricmc:yarn:$minecraft+build.${mod.dep("yarn_build")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")
    "io.github.llamalad7:mixinextras-common:${mod.dep("mixin_extras")}".let {
        annotationProcessor(it)
        implementation(it)
    }
    modApi("me.shedaniel.cloth:cloth-config-fabric:${mod.dep("cloth_version")}")
    modCompileOnly("maven.modrinth:jade:${mod.dep("jade_fabric_version")}")
    modCompileOnly("mcp.mobius.waila:wthit-api:fabric-${mod.dep("wthit_version")}")
    modCompileOnly("maven.modrinth:useful-spyglass:${mod.dep("useful_spyglass")}-fabric")
    modCompileOnly("maven.modrinth:fast-item-frames:${mod.dep("fast_item_frames")}")
    modCompileOnly("maven.modrinth:glowcase:${mod.dep("glowcase")}")
    if (stonecutter.eval(mcVersion, "<1.21.2")) {
        modCompileOnly("dev.emi:emi-xplat-intermediary:${mod.dep("emi")}+$minecraft")
    }


    if (stonecutter.eval(mcVersion, ">1.21")) {
        modCompileOnly("eu.pb4:polymer-core:${mod.dep("polymer")}")
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