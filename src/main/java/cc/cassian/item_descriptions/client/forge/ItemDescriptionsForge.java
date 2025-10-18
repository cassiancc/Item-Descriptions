package cc.cassian.item_descriptions.client.forge;

//? forge {

/*import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.Platform;
import cc.cassian.item_descriptions.client.config.ModConfigFactory;
import cc.cassian.item_descriptions.client.helpers.ModLists;
import cc.cassian.item_descriptions.client.helpers.compat.UsefulSpyglassHelpers;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
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
        //? if =1.20.1 {
        /^if (Platform.INSTANCE.isLoaded("usefulspyglass")) {
            MinecraftForge.EVENT_BUS.addListener(UsefulSpyglassHelpers::registerBlockEvent);
            MinecraftForge.EVENT_BUS.addListener(UsefulSpyglassHelpers::registerEntityEvent);
        }
        ^///?}
    }

    @SubscribeEvent
    public static void loadComplete(FMLClientSetupEvent event) {
        ModLists.loadLists();
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

    //Integrate Cloth Config screen (if mod present) with NeoForge mod menu.
    public void registerModsPage() {
        //Display Cloth Config/YACL screen if mod present, else error.
        if (ModList.get().isLoaded("cloth_config") && !ModClient.CONFIG.developerOptions.configScreen.equals("yacl")) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen)-> ModConfigFactory.createScreen(mc, screen, "cloth-config")));
        } else if (ModList.get().isLoaded("yet_another_config_lib_v3")) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen)-> ModConfigFactory.createScreen(mc, screen, "yacl")));
        }
    }
}
*///?}