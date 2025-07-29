package cc.cassian.item_descriptions.client.jade.fabric;

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.jade.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
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
        //Register Block Descriptions plugin.
        //? if >1.20
        registration.markAsClientFeature(ModClient.BLOCK_DESCRIPTIONS);
        registration.registerBlockComponent(JadeBlockDescriptions.INSTANCE, Block.class);
        //Register Entity Descriptions plugin.
        //? if >1.20
        registration.markAsClientFeature(ModClient.ENTITY_DESCRIPTIONS);
        registration.registerEntityComponent(JadeEntityDescriptions.INSTANCE, Entity.class);

    }
}
