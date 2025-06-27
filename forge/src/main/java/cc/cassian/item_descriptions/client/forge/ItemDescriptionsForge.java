package cc.cassian.item_descriptions.client.forge;

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.config.forge.ModConfigFactory;
import cc.cassian.item_descriptions.client.helpers.ModLists;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static cc.cassian.item_descriptions.client.ModClient.*;
import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

@Mod(MOD_ID_NEO)
public final class ItemDescriptionsForge {

    public ItemDescriptionsForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Load config.
        ModClient.init();
        //Add Tooltips
        addTooltips();

        //Register config screen.
        registerModsPage();
        eventBus.addListener(ItemDescriptionsForge::loadComplete);

    }

    public void addTooltips() {
        MinecraftForge.EVENT_BUS.addListener(this::onItemTooltipEvent);
        MinecraftForge.EVENT_BUS.addListener(this::onItemTooltipEventLowestPriority);
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

    // Fix item descriptions
    @SubscribeEvent
    public static void loadComplete(FMLClientSetupEvent event) {
        ModLists.loadLists();
    }

    //Integrate Cloth Config screen (if mod present) with Forge mod menu.
    public static void registerModsPage() {
        //Display Cloth Config/YACL screen if mod present, else error.
        if (ModList.get().isLoaded("cloth_config") && !CONFIG.developerOptions.configScreen.value().equals("yacl")) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((screen)-> ModConfigFactory.createScreen(null, screen, "cloth-config")));
        } else if (ModList.get().isLoaded("yet_another_config_lib_v3")) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((screen)-> ModConfigFactory.createScreen(null, screen, "yacl")));
        }
    }
}
