package cc.cassian.item_descriptions.client.neoforge;

//? neoforge {

/*import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.Platform;
import cc.cassian.item_descriptions.client.config.ModConfigFactory;
import cc.cassian.item_descriptions.client.descriptions.ItemDescriptions;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import cc.cassian.item_descriptions.client.helpers.ModLists;
import cc.cassian.item_descriptions.client.helpers.compat.UsefulSpyglassHelpers;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import static cc.cassian.item_descriptions.client.ModClient.*;

@Mod(value = MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = MOD_ID)
public final class ItemDescriptionsNeoForge {
    public ItemDescriptionsNeoForge(IEventBus eventBus, ModContainer modContainer) {
        // Load config.
        ModClient.init();
        // Register config screen.
        registerModsPage();
        // Register Useful Spyglass compatibility
        if (Platform.INSTANCE.isLoaded("usefulspyglass")) {
            NeoForge.EVENT_BUS.addListener(UsefulSpyglassHelpers::registerBlockEvent);
            NeoForge.EVENT_BUS.addListener(UsefulSpyglassHelpers::registerEntityEvent);
        }
    }

    @SubscribeEvent
    public static void loadComplete(FMLClientSetupEvent event) {
        ModLists.loadLists();
    }

    //Add Item Descriptions to item tooltips.
    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event) {
        ItemDescriptions.createDescriptionsFromItemStack(event.getItemStack(), event.getContext(), event.getFlags(), event.getToolTip());
    }

    // Fix item descriptions
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemTooltipEventLowestPriority(ItemTooltipEvent event) {
        ItemDescriptions.fixItemStackDescriptionTooltip(event.getItemStack(), event.getContext(), event.getFlags(), event.getToolTip());
    }

    @SubscribeEvent
    public static void generateMissing(TagsUpdatedEvent event) {
        if (ModClient.CONFIG.developerOptions.generateMissing.value() && event.getUpdateCause().equals(TagsUpdatedEvent.UpdateCause.CLIENT_PACKET_RECEIVED)) {
            Minecraft.getInstance().execute(() -> ModHelpers.generateMissingTranslations(null));
        }
    }

    //Integrate Cloth Config screen (if mod present) with NeoForge mod menu.
    public void registerModsPage() {
        //Display Cloth Config/YACL screen if mod present, else error.
        if (ModList.get().isLoaded("yet_another_config_lib_v3")) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> new ModConfigFactory("yacl"));
        }
        else if (ModList.get().isLoaded("cloth_config") && !ModClient.CONFIG.developerOptions.configScreen.value().equals("yacl")) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> new ModConfigFactory("cloth-config"));
        }
    }
}
*///?}