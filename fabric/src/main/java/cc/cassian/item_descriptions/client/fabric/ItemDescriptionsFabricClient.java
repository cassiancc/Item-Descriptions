package cc.cassian.item_descriptions.client.fabric;

import cc.cassian.item_descriptions.client.ModClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public final class ItemDescriptionsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ModClient.init();
        addTooltips();
    }

    public void addTooltips() {
        //? if >1.20.5 {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
        //?} else
        /*ItemTooltipCallback.EVENT.register((stack, context, lines) -> {*/
            //Only show tooltip if key is pressed or "always on" is enabled.
            createItemDescription(stack, lines);
        });
    }
}
