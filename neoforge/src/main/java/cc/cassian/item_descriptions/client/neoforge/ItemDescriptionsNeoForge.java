package cc.cassian.item_descriptions.client.neoforge;

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.config.ModConfig;
import cc.cassian.item_descriptions.client.config.neoforge.ModConfigFactory;
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
            //Create and add tooltip. Tooltip will be wrapped, either by ToolTipFix if installed, or by custom wrapper if not.
            List<Text> tooltip = createTooltip(findItemLoreKey(event.getItemStack()), !tooltipFixInstalled());
            event.getToolTip().addAll(tooltip);
        }
    }

    //Integrate Cloth Config screen (if mod present) with NeoForge mod menu.
    public void registerModsPage() {
        if (clothConfigInstalled()) ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, ModConfigFactory::new);
    }
}
