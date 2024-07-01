package cc.cassian.item_descriptions.client;

import cc.cassian.item_descriptions.client.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static cc.cassian.item_descriptions.ModHelpers.*;

public class TooltipClient implements ClientModInitializer {
    public static final String MOD_ID = "item-descriptions";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        ModConfig.load();
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            //Only show tooltip if key is pressed or "always on" is enabled.
            if (tooltipKeyPressed()) {
                //Convert the block/item key to a lore key or generic lore key.
                String translationKey = findLoreKey(stack);
                //Check if a translation exists.
                if (!translationKey.isEmpty()) {
                    //Translate the lore key.
                    String translatedKey =  I18n.translate(translationKey);
                    //Check if tooltip fix is enabled. If so, its wrapping will be used. If not, a custom wrapping is used.
                    if (!tooltipFixInstalled()) {
                        //Any tooltip longer than 25 characters should be shortened.
                        while (translatedKey.length() >= 25) {
                            //Find how much to shorten the tooltip by.
                            int index = getIndex(translatedKey);
                            //Add a shortened tooltip.
                            lines.add(Text.literal(translatedKey.substring(0, index)).formatted(getColor()));
                            //Remove the shortened tooltip substring from the tooltip. Repeat.
                            translatedKey = translatedKey.substring(index);
                        }
                    }
                    //Add the final tooltip.
                    lines.add(Text.literal(translatedKey).formatted(getColor()));
                }
            }
        });
    }

}
