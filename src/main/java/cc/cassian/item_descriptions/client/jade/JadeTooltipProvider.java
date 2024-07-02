package cc.cassian.item_descriptions.client.jade;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.List;

import static cc.cassian.item_descriptions.ModHelpers.*;

public enum JadeTooltipProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip lines, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        List<Text> tooltip = createTooltip(findLoreKey(blockAccessor.getBlock()), true);
        for (Text text : tooltip) {
            lines.add(text);
        }

    }

    @Override
    public Identifier getUid() {
        return JadeIntegration.BLOCK_TOOLTIP;
    }
}
