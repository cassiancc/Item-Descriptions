package cc.cassian.item_descriptions.neoforge;

import cc.cassian.item_descriptions.client.TooltipClient;
import cc.cassian.item_descriptions.client.config.ModConfig;
import net.minecraft.text.Text;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.moddiscovery.ModInfo;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

@Mod("item_descriptions")
public final class ItemDescriptionsNeoForge {
    public ItemDescriptionsNeoForge(ModContainer container) {
        // Run our common setup.
        ModConfig.load();
        TooltipClient.LOGGER.info("Successfully initialized Item Descriptions. Your items are now described!");
        NeoForge.EVENT_BUS.addListener(this::onItemTooltipEvent);
        registerModsPage();

    }

    @SubscribeEvent
    public void onItemTooltipEvent(ItemTooltipEvent event) {
        //Only show tooltip if key is pressed or "always on" is enabled.
        if (ModConfig.get().itemDescriptions && (tooltipKeyPressed() || ModConfig.get().displayAlways)) {
            //Create and add tooltip. Tooltip will be wrapped, either by ToolTipFix if installed, or by custom wrapper if not.
            List<Text> tooltip = createTooltip(findItemLoreKey(event.getItemStack()), !tooltipFixInstalled());
            event.getToolTip().addAll(tooltip);

        }
    }
    public void registerModsPage() {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, ModConfigFactory::new);
    }
}
