package cc.cassian.item_descriptions.client;

import cc.cassian.item_descriptions.client.config.ModConfig;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModClient {
    public static final String MOD_ID = "item-descriptions";
    public static final String MOD_ID_NEO = "item_descriptions";
    public static final String MOD_NAME = "Item Descriptions";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static final Identifier BLOCK_DESCRIPTIONS = Identifier.of(MOD_ID, "block_descriptions");
    public static final Identifier ENTITY_DESCRIPTIONS = Identifier.of(MOD_ID, "entity_descriptions");
    public static void init() {
        ModConfig.load();
        ModClient.LOGGER.info("Successfully initialized Item Descriptions. Your items are now described!");

    }
}
