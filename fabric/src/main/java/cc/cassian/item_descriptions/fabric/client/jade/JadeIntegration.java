package cc.cassian.item_descriptions.fabric.client.jade;

import cc.cassian.item_descriptions.client.TooltipClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
    @Environment(EnvType.CLIENT)
    public void registerClient(IWailaClientRegistration registration) {
        client = registration;
        registration.markAsClientFeature(BLOCK_TOOLTIP);
        registration.registerBlockComponent(JadeTooltipProvider.INSTANCE, Block.class);


    }
}
