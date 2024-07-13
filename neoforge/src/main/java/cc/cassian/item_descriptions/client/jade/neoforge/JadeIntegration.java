package cc.cassian.item_descriptions.client.jade.neoforge;

import cc.cassian.item_descriptions.client.TooltipClient;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeIntegration implements IWailaPlugin {
    public static final Identifier BLOCK_TOOLTIP = Identifier.of(TooltipClient.MOD_ID, "block_tooltip");
    static IWailaClientRegistration client;

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        client = registration;
        registration.markAsClientFeature(BLOCK_TOOLTIP);
        registration.registerBlockComponent(JadeTooltipProvider.INSTANCE, Block.class);


    }
}
