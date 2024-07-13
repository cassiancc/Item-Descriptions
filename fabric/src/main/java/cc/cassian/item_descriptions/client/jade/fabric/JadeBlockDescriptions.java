package cc.cassian.item_descriptions.client.jade.fabric;

import cc.cassian.item_descriptions.client.TooltipClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import java.util.List;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public enum JadeBlockDescriptions implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip lines, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        //Check if block descriptions are enabled in mod config.
        if (showBlockDescriptions()) {
            //Create and add tooltip.
            List<Text> tooltip = createTooltip(getBlockAccessorLoreKey(blockAccessor.getBlock(), blockAccessor.getLevel(), blockAccessor.getPosition(), blockAccessor.getBlockState(), blockAccessor.getBlockEntity()), true);
            for (Text text : tooltip) {
                lines.add(text);
            }
        }
    }

    @Override
    public Identifier getUid() {
        return TooltipClient.BLOCK_DESCRIPTIONS;
    }
}
