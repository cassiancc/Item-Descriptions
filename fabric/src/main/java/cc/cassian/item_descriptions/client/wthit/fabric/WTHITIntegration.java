package cc.cassian.item_descriptions.client.wthit.fabric;

import cc.cassian.item_descriptions.client.ModClient;
import mcp.mobius.waila.api.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;

import java.util.List;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class WTHITIntegration implements IWailaPlugin, IBlockComponentProvider, IEntityComponentProvider {

    @Override
    public void register(IRegistrar registrar) {
        registrar.addConfig( ModClient.BLOCK_DESCRIPTIONS, true );
        registrar.addConfig( ModClient.ENTITY_DESCRIPTIONS, true );
        registrar.addComponent((IBlockComponentProvider) this, TooltipPosition.BODY, Block.class, 2000 );
        registrar.addComponent((IEntityComponentProvider) this, TooltipPosition.BODY, Entity.class, 2000 );

    }

    @Override
    public void appendBody(ITooltip lines, IBlockAccessor blockAccessor, IPluginConfig config) {
        //Check if block descriptions are enabled in mod config.
        if (showBlockDescriptions()) {
            List<Text> tooltip = createTooltip(getBlockAccessorLoreKey(blockAccessor.getBlock(), blockAccessor.getWorld(), blockAccessor.getPosition(), blockAccessor.getBlockState(), blockAccessor.getBlockEntity()), true);
            for (Text text : tooltip) {
                lines.addLine(text);
            }
        }
    }

    @Override
    public void appendBody(ITooltip lines, IEntityAccessor entityAccessor, IPluginConfig config) {
        //Check if block descriptions are enabled in mod config.
        if (showEntityDescriptions()) {
            List<Text> tooltip = createTooltip(getEntityAccessorLoreKey(entityAccessor.getEntity()), true);
            for (Text text : tooltip) {
                lines.addLine(text);
            }
        }
    }


}
