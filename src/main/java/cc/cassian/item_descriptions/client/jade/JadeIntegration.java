package cc.cassian.item_descriptions.client.jade;

import cc.cassian.item_descriptions.client.TooltipClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeIntegration implements IWailaPlugin {
    public static final Identifier BLOCK_TOOLTIP = Identifier.of(TooltipClient.MOD_ID, "block_tooltip");
    static IWailaClientRegistration client;


    @Override
    public void register(IWailaCommonRegistration registration) {
        //TODO register data providers
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerClient(IWailaClientRegistration registration) {
        client = registration;
        //TODO register component providers, icon providers, callbacks, and config options here
        registration.registerBlockComponent(JadeTooltipProvider.INSTANCE, Block.class);

    }
}
