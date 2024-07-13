package cc.cassian.item_descriptions.client.jade.neoforge;

import cc.cassian.item_descriptions.client.ModClient;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeIntegration implements IWailaPlugin {

    static IWailaClientRegistration client;

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        client = registration;
        registration.markAsClientFeature(ModClient.BLOCK_DESCRIPTIONS);
        registration.markAsClientFeature(ModClient.ENTITY_DESCRIPTIONS);
        registration.registerBlockComponent(JadeBlockDescriptions.INSTANCE, Block.class);
        registration.registerEntityComponent(JadeEntityDescriptions.INSTANCE, Entity.class);


    }
}
