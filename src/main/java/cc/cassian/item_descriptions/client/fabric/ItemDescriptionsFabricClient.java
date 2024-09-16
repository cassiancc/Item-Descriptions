package cc.cassian.item_descriptions.client.fabric;

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.List;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public final class ItemDescriptionsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ModClient.init();
        addTooltips();
    }

    public void addTooltips() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            //Only show tooltip if key is pressed or "always on" is enabled.
            if (showItemDescriptions()) {
                //Create and add tooltip. Tooltip will be wrapped, either by ToolTipFix if installed, or by custom wrapper if not.
                List<Text> tooltip = (List<Text>) createTooltip(findItemLoreKey(stack), !tooltipFixInstalled(), "text");
                lines.addAll(tooltip);
            }
        });
    }
}
