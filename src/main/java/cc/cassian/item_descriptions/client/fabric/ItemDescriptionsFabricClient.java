package cc.cassian.item_descriptions.client.fabric;

//? fabric {

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.Platform;
import cc.cassian.item_descriptions.client.descriptions.ItemDescriptions;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import cc.cassian.item_descriptions.client.helpers.ModLists;
import cc.cassian.item_descriptions.client.helpers.compat.UsefulSpyglassHelpers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public final class ItemDescriptionsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ModClient.init();
        addTooltips();
    }

    public void addTooltips() {
        //Only show tooltip if key is pressed or "always on" is enabled.
        ItemTooltipCallback.EVENT.register(ItemDescriptions::createDescriptionsFromItemStack);
        if (ModClient.CONFIG.developerOptions.generateMissing.value()) {
            CommonLifecycleEvents.TAGS_LOADED.register(ModHelpers.of("missing"), (manager, b) -> ModHelpers.generateMissingTranslations(manager::lookup));
        }
        ItemTooltipCallback.EVENT.addPhaseOrdering(Event.DEFAULT_PHASE, FABRIC_EVENT_PHASE);
        // Fix tooltips.
        ItemTooltipCallback.EVENT.register(FABRIC_EVENT_PHASE, ItemDescriptions::fixItemStackDescriptionTooltip);
        ClientLifecycleEvents.CLIENT_STARTED.register((client -> {
            ModLists.loadLists();
        }));
        if (Platform.INSTANCE.isLoaded("usefulspyglass")) {
            UsefulSpyglassHelpers.register();
        }
    }
}
//?}