package cc.cassian.item_descriptions.client;

import cc.cassian.item_descriptions.client.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static cc.cassian.item_descriptions.ModHelpers.*;

public class TooltipClient implements ClientModInitializer {
    public static final String MOD_ID = "item-descriptions";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        ModConfig.load();
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if (tooltipKeyPressed()) {
                String translationKey = findLoreKey(stack);
                if (!translationKey.isEmpty()) {

                    String translatedKey =  I18n.translate(translationKey);

                    while (translatedKey.length() >= 20) {
                        lines.add(new LiteralText(translatedKey.substring(0, 20)).formatted(getColor()));
                        translatedKey = translatedKey.substring(20);
                    }

                    lines.add(new LiteralText(translatedKey).formatted(getColor()));
                }
            }
        });
    }
}
