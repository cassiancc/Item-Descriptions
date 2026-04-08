package cc.cassian.item_descriptions.client.wthit;

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.descriptions.BlockDescriptions;
import cc.cassian.item_descriptions.client.descriptions.EntityDescriptions;
import cc.cassian.item_descriptions.client.helpers.TagHelpers;
import mcp.mobius.waila.api.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

import java.util.List;

import static cc.cassian.item_descriptions.client.ModClient.BLOCK_DESCRIPTIONS;
import static cc.cassian.item_descriptions.client.ModClient.ENTITY_DESCRIPTIONS;
import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class WTHITIntegration implements IWailaPlugin, IBlockComponentProvider, IEntityComponentProvider {

    @Override
    public void register(IRegistrar registrar) {
        //Register Block Descriptions plugin.
        registrar.addConfig( BLOCK_DESCRIPTIONS, true );
        registrar.body((IBlockComponentProvider) this, Block.class, 2000 );
        //Register Entity Descriptions plugin.
        registrar.addConfig( ENTITY_DESCRIPTIONS, true );
        registrar.body((IEntityComponentProvider) this, Entity.class, 2000 );
    }

    @Override
    public void appendBody(ITooltip lines, IBlockAccessor blockAccessor, IPluginConfig config) {
        //Check if block descriptions are enabled in mod config.
        if (BlockDescriptions.showBlockDescriptions() && config.getBoolean(BLOCK_DESCRIPTIONS)
        ) {
            List<Component> tooltip;
            if (ModClient.CONFIG.developerOptions.showAllPotentialKeys.value()) {
                tooltip = TagHelpers.findAllPotentialKeys(blockAccessor.getBlockState());
            }
            else {
                tooltip = createTooltip(blockAccessor.getBlock().getName(), BlockDescriptions.createBlockDescription(blockAccessor.getBlock(), blockAccessor.getLevel(), blockAccessor.getPosition(), blockAccessor.getBlockState(), blockAccessor.getBlockEntity()));
            }
            for (Component text : tooltip) {
                lines.addLine(text);
            }
        }
    }

    @Override
    public void appendBody(ITooltip lines, IEntityAccessor entityAccessor, IPluginConfig config) {
        //Check if entity descriptions are enabled in mod config.
        if (EntityDescriptions.showEntityDescriptions() && config.getBoolean(ENTITY_DESCRIPTIONS)) {
            List<Component> tooltip;
            if (ModClient.CONFIG.developerOptions.showAllPotentialKeys.value()) {
                tooltip = TagHelpers.findAllPotentialKeys(entityAccessor.getEntity());
            }
            else {
                tooltip = EntityDescriptions.createEntityDescription(entityAccessor.getEntity());
            }
            for (Component text : tooltip) {
                lines.addLine(text);
            }
        }
    }
}
