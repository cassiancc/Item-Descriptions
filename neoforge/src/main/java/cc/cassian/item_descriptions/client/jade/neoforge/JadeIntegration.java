package cc.cassian.item_descriptions.client.jade.neoforge;

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.jade.*;
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
        //Register Block Descriptions plugin.
        registration.markAsClientFeature(ModClient.BLOCK_DESCRIPTIONS);
        registration.registerBlockComponent(JadeBlockDescriptions.INSTANCE, Block.class);
        //Register Entity Descriptions plugin.
        registration.markAsClientFeature(ModClient.ENTITY_DESCRIPTIONS);
        registration.registerEntityComponent(JadeEntityDescriptions.INSTANCE, Entity.class);


    }
}
