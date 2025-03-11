package cc.cassian.item_descriptions.client.forge;

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.config.ModConfig;
import cc.cassian.item_descriptions.client.config.forge.ModConfigFactory;
import cc.cassian.item_descriptions.client.helpers.GenericKeys;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static cc.cassian.item_descriptions.client.ModClient.*;
import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

@Mod(MOD_ID_NEO)
public final class ItemDescriptionsForge {

    public ItemDescriptionsForge() {
        // Load config.
        ModClient.init();
        //Add Tooltips
        addTooltips();
        //Register config screen.
        registerModsPage();

    }

    public void addTooltips() {
        MinecraftForge.EVENT_BUS.addListener(this::onItemTooltipEvent);
    }

    //Add Item Descriptions to item tooltips.
    @SubscribeEvent
    public void onItemTooltipEvent(ItemTooltipEvent event) {
        //Only show tooltip if key is pressed or "always on" is enabled.
        if (showItemDescriptions()) {
            //Create and add tooltip. Tooltip will be wrapped.
            ItemStack stack = event.getItemStack();
            List<Text> tooltip;
            if (ModConfig.get().developer_showAllPotentialKeys) {
                tooltip = GenericKeys.findAllPotentialKeys(stack);
            }
            else {
                tooltip = createTooltip(findItemLoreKey(stack), false);
            }
            event.getToolTip().addAll(tooltip);
        }
    }

    //Integrate Cloth Config screen (if mod present) with Forge mod menu.
    public static void registerModsPage() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(ModConfigFactory::createScreen));
    }
}
