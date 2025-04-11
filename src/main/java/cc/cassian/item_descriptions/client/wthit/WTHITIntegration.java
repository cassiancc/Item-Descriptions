package cc.cassian.item_descriptions.client.wthit;

import cc.cassian.item_descriptions.client.config.ModConfig;
import cc.cassian.item_descriptions.client.helpers.TagHelpers;
import mcp.mobius.waila.api.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;

import java.util.List;

import static cc.cassian.item_descriptions.client.ModClient.BLOCK_DESCRIPTIONS;
import static cc.cassian.item_descriptions.client.ModClient.ENTITY_DESCRIPTIONS;
import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class WTHITIntegration implements IWailaPlugin, IBlockComponentProvider, IEntityComponentProvider {

    @Override
    public void register(IRegistrar registrar) {
        //Register Block Descriptions plugin.
        registrar.addConfig( BLOCK_DESCRIPTIONS, true );
        registrar.addComponent((IBlockComponentProvider) this, TooltipPosition.BODY, Block.class, 2000 );
        //Register Entity Descriptions plugin.
        registrar.addConfig( ENTITY_DESCRIPTIONS, true );
        registrar.addComponent((IEntityComponentProvider) this, TooltipPosition.BODY, Entity.class, 2000 );

    }

    @Override
    public void appendBody(ITooltip lines, IBlockAccessor blockAccessor, IPluginConfig config) {
        //Check if block descriptions are enabled in mod config.
        if (showBlockDescriptions() && config.getBoolean(BLOCK_DESCRIPTIONS)) {
            List<Text> tooltip;
            if (ModConfig.get().developer_showAllPotentialKeys) {
                tooltip = TagHelpers.findAllPotentialKeys(blockAccessor.getBlockState());
            }
            else {
                tooltip = createTooltip(blockAccessor.getBlock().getName(), createBlockDescription(blockAccessor.getBlock(), blockAccessor.getWorld(), blockAccessor.getPosition(), blockAccessor.getBlockState(), blockAccessor.getBlockEntity()));
            }
            for (Text text : tooltip) {
                lines.addLine(text);
            }
        }
    }

    @Override
    public void appendBody(ITooltip lines, IEntityAccessor entityAccessor, IPluginConfig config) {
        //Check if entity descriptions are enabled in mod config.
        if (showEntityDescriptions()  && config.getBoolean(ENTITY_DESCRIPTIONS)) {
            List<Text> tooltip;
            if (ModConfig.get().developer_showAllPotentialKeys) {
                tooltip = TagHelpers.findAllPotentialKeys(entityAccessor.getEntity());
            }
            else {
                tooltip = createEntityDescription(entityAccessor.getEntity());
            }
            for (Text text : tooltip) {
                lines.addLine(text);
            }
        }
    }


}
