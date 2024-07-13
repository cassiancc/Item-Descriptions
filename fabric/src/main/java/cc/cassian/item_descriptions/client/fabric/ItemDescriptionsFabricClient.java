package cc.cassian.item_descriptions.client.fabric;

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;

import java.util.List;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public final class ItemDescriptionsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ModConfig.load();
        ModClient.LOGGER.info("Successfully initialized Item Descriptions. Your items are now described!");
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            //Only show tooltip if key is pressed or "always on" is enabled.
            if (showItemDescriptions()) {
                //Create and add tooltip. Tooltip will be wrapped, either by ToolTipFix if installed, or by custom wrapper if not.
                List<Text> tooltip = createTooltip(findItemLoreKey(stack), !tooltipFixInstalled());
                lines.addAll(tooltip);
            }
        });
    }
}
