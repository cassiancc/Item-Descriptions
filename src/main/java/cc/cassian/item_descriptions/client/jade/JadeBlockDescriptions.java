package cc.cassian.item_descriptions.client.jade;
//? if !unobf {
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.helpers.TagHelpers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
            List<Component> tooltip;
            if (ModClient.CONFIG.developerOptions.showAllPotentialKeys.value()) {
                tooltip = TagHelpers.findAllPotentialKeys(blockAccessor.getBlockState());
            }
            else {
                tooltip = createTooltip(blockAccessor.getBlock().getName(), createBlockDescription(blockAccessor.getBlock(), blockAccessor.getLevel(), blockAccessor.getPosition(), blockAccessor.getBlockState(), blockAccessor.getBlockEntity()));
            }
            for (Component text : tooltip) {
                lines.add(text);
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ModClient.BLOCK_DESCRIPTIONS;
    }
}
//?}