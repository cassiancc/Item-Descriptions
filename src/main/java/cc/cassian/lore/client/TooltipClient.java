package cc.cassian.lore.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static cc.cassian.lore.ModHelpers.findLoreKey;

public class TooltipClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (Screen.hasControlDown()) {
                lines.add(Text.translatable(findLoreKey(stack)).formatted(Formatting.GRAY));
            }
        });
    }
}
