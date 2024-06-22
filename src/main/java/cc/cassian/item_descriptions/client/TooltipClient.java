package cc.cassian.item_descriptions.client;

import cc.cassian.item_descriptions.client.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static cc.cassian.item_descriptions.ModHelpers.findLoreKey;
import static cc.cassian.item_descriptions.ModHelpers.tooltipKeyPressed;

public class TooltipClient implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("item_descriptions");

    @Override
    public void onInitializeClient() {
        ModConfig.load();
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (tooltipKeyPressed()) {
                String translationKey = findLoreKey(stack);
                if (!translationKey.isEmpty()) {
                    lines.add(Text.translatable(translationKey).formatted(Formatting.GRAY));
                }
            }
        });
    }
}
