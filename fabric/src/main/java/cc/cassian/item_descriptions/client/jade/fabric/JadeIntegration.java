package cc.cassian.item_descriptions.client.jade.fabric;

import cc.cassian.item_descriptions.client.TooltipClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeIntegration implements IWailaPlugin {
    static IWailaClientRegistration client;

    @Override
    @Environment(EnvType.CLIENT)
    public void registerClient(IWailaClientRegistration registration) {
        client = registration;
        registration.markAsClientFeature(TooltipClient.BLOCK_DESCRIPTIONS);
        registration.registerBlockComponent(JadeBlockDescriptions.INSTANCE, Block.class);
        registration.markAsClientFeature(TooltipClient.ENTITY_DESCRIPTIONS);
        registration.registerEntityComponent(JadeEntityDescriptions.INSTANCE, Entity.class);

    }
}
