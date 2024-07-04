package cc.cassian.item_descriptions.client.jade;

import cc.cassian.item_descriptions.client.config.ModConfig;
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
        //Check if block descriptions are enabled in mod config.
        if (ModConfig.get().blockDescriptions && (tooltipKeyPressed() || ModConfig.get().displayBlockDescriptionsAlways)) {
            //Convert block translation key to lore translation key.
            String loreKey = findBlockLoreKey(blockAccessor.getBlock());
            //Check if translation exists. If not, see if an item exists for it - e.g. seeds.
            if (!hasTranslation(loreKey)) {
                loreKey = findItemLoreKey(blockAccessor.getBlock().getPickStack(blockAccessor.getLevel(), blockAccessor.getPosition(), blockAccessor.getBlockState()));
            }
            //Create and add tooltip.
            List<Text> tooltip = createTooltip(loreKey, true);
            for (Text text : tooltip) {
                lines.add(text);
            }
        }

    }

    @Override
    public Identifier getUid() {
        return JadeIntegration.BLOCK_TOOLTIP;
    }
}
