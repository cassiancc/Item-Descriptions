package cc.cassian.item_descriptions.client;

//? if >1.21.10 {
/*import net.minecraft.resources.Identifier;
*///?} else {
import net.minecraft.resources.ResourceLocation;
 //?}

import static cc.cassian.item_descriptions.client.ModClient.MOD_ID;

public record NamespacedKey(
        //? if >1.21.10 {
        /*Identifier
        *///?} else {
        ResourceLocation
         //?}
        identifier) {

    public static
    //? if >1.21.10 {
    /*Identifier
     *///?} else {
    ResourceLocation
    //?}
    of(String path) {
        return NamespacedKey.of(MOD_ID, path);
    }

    public static
    //? if >1.21.10 {
    /*Identifier
     *///?} else {
    ResourceLocation
    //?}
    ofVanilla(String path) {
        return NamespacedKey.of("minecraft", path);
    }

    public static
    //? if >1.21.10 {
    /*Identifier
    *///?} else {
    ResourceLocation
     //?}
    ofVanillaId(String path) {
        return ofVanilla(path);
    }

    public static
        //? if >1.21.10 {
        /*Identifier
         *///?} else {
    ResourceLocation
    //?}
    of(String namespace, String path) {
        //? if >1.21.10 {
        /*return Identifier.fromNamespaceAndPath(namespace, path);
        *///?} else if >=1.21 {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
        //?} else {
        /*return new ResourceLocation(namespace, path);
        *///?}
    }
}
