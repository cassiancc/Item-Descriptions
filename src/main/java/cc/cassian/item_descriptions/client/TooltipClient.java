package cc.cassian.item_descriptions.client;

import cc.cassian.item_descriptions.client.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

import static cc.cassian.item_descriptions.ModHelpers.*;

public class TooltipClient implements ClientModInitializer {
    public static final String MOD_ID = "item-descriptions";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        ModConfig.load();
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            //Only show tooltip if key is pressed or "always on" is enabled.
            List<Text> tooltip = createTooltip(findLoreKey(stack), !tooltipFixInstalled());
            lines.addAll(tooltip);
        });
    }
}
