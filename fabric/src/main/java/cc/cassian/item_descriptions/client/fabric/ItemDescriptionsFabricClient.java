package cc.cassian.item_descriptions.client.fabric;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.config.ModConfig;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.text.TranslatableTextContent;

import java.awt.*;
import java.util.HashSet;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public final class ItemDescriptionsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ModClient.init();
        addTooltips();
    }

    public void addTooltips() {
        //? if >1.20.5 {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
        //?} else
        /*ItemTooltipCallback.EVENT.register((stack, context, lines) -> {*/
            //Only show tooltip if key is pressed or "always on" is enabled.
            createDescriptionsFromItemStack(stack, lines);
        });
    }
}
