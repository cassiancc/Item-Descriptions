package cc.cassian.item_descriptions.client.hwyla;

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.config.ModConfig;
import mcp.mobius.waila.api.*;
import net.minecraft.block.Block;
import net.minecraft.text.Text;

import java.util.List;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class HWYLAPlugin implements IWailaPlugin, IComponentProvider {

    @Override
    public void register(IRegistrar registrar) {
        registrar.addConfig(ModClient.BLOCK_DESCRIPTIONS, true );
        registrar.registerComponentProvider( this, TooltipPosition.BODY, Block.class);
    }

    @Override
    public void appendBody(List<Text> lines, IDataAccessor blockAccessor, IPluginConfig config) {
        //Check if block descriptions are enabled in mod config.
        if (ModConfig.get().blockDescriptions && (tooltipKeyPressed() || ModConfig.get().displayBlockDescriptionsAlways)) {
            //Convert block translation key to lore translation key.
            String loreKey = findBlockLoreKey(blockAccessor.getBlock());
            //Check if translation exists. If not, see if an item exists for it - e.g. seeds.
            if (!hasTranslation(loreKey)) {
                loreKey = findItemLoreKey(blockAccessor.getBlock().getPickStack(blockAccessor.getWorld(), blockAccessor.getPosition(), blockAccessor.getBlockState()));
            }
            //Create and add tooltip.
            List<Text> tooltip = (List<Text>) createTooltip(loreKey, true, "text");
            lines.addAll(tooltip);
        }
    }



}
