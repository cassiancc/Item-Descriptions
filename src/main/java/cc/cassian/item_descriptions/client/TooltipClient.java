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
    public static final String MOD_NAME = "Item Descriptions";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @Override
    public void onInitializeClient() {
        ModConfig.load();
        LOGGER.info("Successfully initialized Item Descriptions. Your items are now described!");
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            //Only show tooltip if key is pressed or "always on" is enabled.
            if (ModConfig.get().itemDescriptions && (tooltipKeyPressed() || ModConfig.get().displayAlways)) {
                //Create and add tooltip. Tooltip will be wrapped, either by ToolTipFix if installed, or by custom wrapper if not.
                List<Text> tooltip = createTooltip(findItemLoreKey(stack), !tooltipFixInstalled());
                lines.addAll(tooltip);
            }
        });
    }
}
