package cc.cassian.item_descriptions.client.fabric;

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.config.ModConfig;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.util.Identifier;

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
            createDescriptionsFromItemStack(stack, lines);
        });

        if (ModConfig.get().developer_generateMissing) {
            CommonLifecycleEvents.TAGS_LOADED.register(Identifier.of(ModClient.MOD_ID, "missing"), (manager, b) -> ModHelpers.generateMissingTranslations(
                    manager::getOptional
            ));
        }
        ItemTooltipCallback.EVENT.addPhaseOrdering(Event.DEFAULT_PHASE, FABRIC_EVENT_PHASE);
        //? if >1.20.5 {
        ItemTooltipCallback.EVENT.register(FABRIC_EVENT_PHASE, (stack, context, type, lines) -> {
            //?} else
            /*ItemTooltipCallback.EVENT.register((stack, context, lines) -> {*/
            // Fix tooltips.
            fixItemStackDescriptionTooltip(stack, lines);
        });
    }
}
