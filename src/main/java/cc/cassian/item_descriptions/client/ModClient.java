package cc.cassian.item_descriptions.client;

import cc.cassian.item_descriptions.client.config.ModConfig;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import cc.cassian.item_descriptions.client.helpers.ModStyle;
//? if >1.21.1 {
import net.minecraft.core.RegistryAccess;
//?}
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModClient {
    public static final String MOD_ID = "item-descriptions";
    public static final String MOD_ID_NEO = "item_descriptions";
    public static final String MOD_NAME = "Item Descriptions";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static final ModConfig CONFIG = ModConfig.createToml(ModConfig.configPath(), "", ModClient.MOD_ID_NEO, ModConfig.class);

    public static final ResourceLocation BLOCK_DESCRIPTIONS = ModHelpers.of("block_descriptions");
    public static final ResourceLocation ENTITY_DESCRIPTIONS = ModHelpers.of("entity_descriptions");

    public static void init() {
        ModStyle.updateStyles();
        ModClient.LOGGER.info("Successfully initialized Item Descriptions. Your items are now described!");
    }
}
