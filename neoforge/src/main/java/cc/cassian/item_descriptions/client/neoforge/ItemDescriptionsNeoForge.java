package cc.cassian.item_descriptions.client.neoforge;

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.config.neoforge.ModConfigFactory;
import cc.cassian.item_descriptions.client.helpers.ModLists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import static cc.cassian.item_descriptions.client.ModClient.*;
import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

@Mod(MOD_ID_NEO)
public final class ItemDescriptionsNeoForge {
    public ItemDescriptionsNeoForge(IEventBus eventBus, ModContainer modContainer) {
        // Load config.
        ModClient.init();
        //Add Tooltips
        addTooltips();
        //Register config screen.
        registerModsPage();
        eventBus.addListener(ItemDescriptionsNeoForge::loadComplete);
        //? if >1.21.1 {
        NeoForge.EVENT_BUS.addListener(ItemDescriptionsNeoForge::worldLoad);
        //?}
    }

    @SubscribeEvent
    public static void loadComplete(FMLClientSetupEvent event) {
        ModLists.loadLists();
    }

    //? if >1.21.1 {

    @SubscribeEvent
    public static void worldLoad(PlayerEvent.PlayerLoggedInEvent event) {
        lookup = MinecraftClient.getInstance().world.getRegistryManager();
    }
    //?}

    public void addTooltips() {
        NeoForge.EVENT_BUS.addListener(this::onItemTooltipEvent);
        NeoForge.EVENT_BUS.addListener(this::onItemTooltipEventLowestPriority);
    }

    //Add Item Descriptions to item tooltips.
    @SubscribeEvent
    public void onItemTooltipEvent(ItemTooltipEvent event) {
        createDescriptionsFromItemStack(event.getItemStack(), event.getToolTip());
    }

    // Fix item descriptions
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onItemTooltipEventLowestPriority(ItemTooltipEvent event) {
        fixItemStackDescriptionTooltip(event.getItemStack(), event.getToolTip());
    }

    //Integrate Cloth Config screen (if mod present) with NeoForge mod menu.
    public void registerModsPage() {
        //Display Cloth Config/YACL screen if mod present, else error.
        if (ModList.get().isLoaded("cloth_config") && !ModClient.CONFIG.developer.configScreen.equals("yacl")) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> new ModConfigFactory("cloth-config"));
        } else if (ModList.get().isLoaded("yet_another_config_lib_v3")) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> new ModConfigFactory("yacl"));
        }
    }
}
