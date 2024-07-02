package cc.cassian.item_descriptions.client.jade;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import static cc.cassian.item_descriptions.ModHelpers.*;
import static cc.cassian.item_descriptions.ModHelpers.getColor;

public enum JadeTooltipProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip lines, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (tooltipKeyPressed()) {
            //Convert the block/item key to a lore key or generic lore key.
            String translationKey = findLoreKey(blockAccessor.getBlock().getPickStack(blockAccessor.getLevel(),blockAccessor.getPosition(), blockAccessor.getBlockState()));
            //Check if a translation exists.
            if (!translationKey.isEmpty()) {
                //Translate the lore key.
                String translatedKey =  I18n.translate(translationKey);
                //Check if tooltip fix is enabled. If so, its wrapping will be used. If not, a custom wrapping is used.
                    //Any tooltip longer than 25 characters should be shortened.
                    while (translatedKey.length() >= 25) {
                        //Find how much to shorten the tooltip by.
                        int index = getIndex(translatedKey);
                        //Add a shortened tooltip.
                        lines.add(Text.literal(translatedKey.substring(0, index)).formatted(getColor()));
                        //Remove the shortened tooltip substring from the tooltip. Repeat.
                        translatedKey = translatedKey.substring(index);
                    }
                //Add the final tooltip.
                lines.add(Text.literal(translatedKey).formatted(getColor()));
            }
        }

    }

    @Override
    public Identifier getUid() {
        return JadeIntegration.BLOCK_TOOLTIP;
    }
}
