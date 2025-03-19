package cc.cassian.item_descriptions.client.neoforge;

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.config.ModConfig;
import cc.cassian.item_descriptions.client.config.neoforge.ModConfigFactory;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import cc.cassian.item_descriptions.client.helpers.TagHelpers;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

import static cc.cassian.item_descriptions.client.ModClient.*;
import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

@Mod(MOD_ID_NEO)
public final class ItemDescriptionsNeoForge {
    public ItemDescriptionsNeoForge() {
        // Load config.
        ModClient.init();
        //Add Tooltips
        addTooltips();
        //Register config screen.
        registerModsPage();

    }

    public void addTooltips() {
        NeoForge.EVENT_BUS.addListener(this::onItemTooltipEvent);
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
                tooltip = TagHelpers.findAllPotentialKeys(stack);
            }
            else {
                tooltip = createTooltip(findItemLoreKey(stack), false);
            }
            event.getToolTip().addAll(tooltip);
        }
        else if (ModConfig.get().hint_enabled) {
            event.getToolTip().addAll(getHintText().getWithStyle(ModHelpers.getStyle(ModConfig.get().hint_color)));
        }
    }

    //Integrate Cloth Config screen (if mod present) with NeoForge mod menu.
    public void registerModsPage() {
        if (clothConfigInstalled()) ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, ModConfigFactory::new);
    }
}
