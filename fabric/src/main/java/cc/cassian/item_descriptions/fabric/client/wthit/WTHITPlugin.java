package cc.cassian.item_descriptions.fabric.client.wthit;

import cc.cassian.item_descriptions.client.TooltipClient;
import mcp.mobius.waila.api.*;
import net.minecraft.block.Block;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class WTHITPlugin implements IWailaPlugin, IBlockComponentProvider {
    public static final Identifier BLOCK_TOOLTIP = Identifier.of(TooltipClient.MOD_ID, "block_tooltip");

    @Override
    public void register(IRegistrar registrar) {
        registrar.addConfig( BLOCK_TOOLTIP, true );
        registrar.addComponent( this, TooltipPosition.BODY, Block.class, 2000 );
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


}
