package cc.cassian.lore.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static cc.cassian.lore.ModHelpers.findLoreKey;

public class EarthlyLoreClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            lines.add(Text.translatable(findLoreKey(stack)).formatted(Formatting.GRAY));
        });
    }
}
