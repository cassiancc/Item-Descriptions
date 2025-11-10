plugins {
    id("dev.kikugie.stonecutter")
    id("co.uzzu.dotenv.gradle") version "4.0.0"
    id("fabric-loom") version "1.13-SNAPSHOT" apply false
    id("net.fabricmc.fabric-loom-no-remap") version "1.14.0-alpha.20" apply false
    id("net.neoforged.moddev") version "2.0.116" apply false
    id("net.neoforged.moddev.legacyforge") version "2.0.91" apply false
    id ("dev.kikugie.postprocess.jsonlang") version "2.1-beta.4" apply false
    id("me.modmuss50.mod-publish-plugin") version "0.8.+" apply false
}

stonecutter active "1.21.10-fabric"

stonecutter parameters {
    constants.match(node.metadata.project.substringAfterLast('-'), "fabric", "unobf", "neoforge", "forge")
    filters.include("**/*.fsh", "**/*.vsh")
}

stonecutter tasks {
    order("publishModrinth")
    order("publishCurseforge")
}

for (version in stonecutter.versions.map { it.version }.distinct()) tasks.register("publish$version") {
    group = "publishing"
    dependsOn(stonecutter.tasks.named("publishMods") { metadata.version == version })
}
