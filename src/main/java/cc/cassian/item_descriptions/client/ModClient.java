package cc.cassian.item_descriptions.client;

import cc.cassian.item_descriptions.client.config.ModConfig;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import cc.cassian.item_descriptions.client.helpers.ModStyle;
//? if >1.21.1 {
import net.minecraft.core.RegistryAccess;
//?}
//? if >1.21.10 {
/*import net.minecraft.resources.Identifier;
 *///?} else {
import net.minecraft.resources.ResourceLocation;
//?}
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModClient {
    public static final String MOD_ID = "item-descriptions";
    public static final String MOD_ID_NEO = "item_descriptions";
    public static final String MOD_NAME = "Item Descriptions";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static final ModConfig CONFIG = ModConfig.createToml(Platform.INSTANCE.configPath(), "", ModClient.MOD_ID_NEO, ModConfig.class);

    public static final
    //? if >1.21.10 {
    /*Identifier
     *///?} else {
    ResourceLocation
    //?}
    BLOCK_DESCRIPTIONS = NamespacedKey.of("block_descriptions");
    public static final
    //? if >1.21.10 {
    /*Identifier
     *///?} else {
    ResourceLocation
    //?}
    ENTITY_DESCRIPTIONS = NamespacedKey.of("entity_descriptions");

    public static void init() {
        ModStyle.updateStyles();
    }
}
