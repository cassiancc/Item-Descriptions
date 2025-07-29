package cc.cassian.item_descriptions.client.mixin;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import cc.cassian.item_descriptions.client.helpers.ModStyle;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Optional;

@Mixin(EnchantmentScreen.class)
public class EnchantmentScreenMixin {
    //? if >1.21.5 {
    /*@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;setComponentTooltipForNextFrame(Lnet/minecraft/client/gui/Font;Ljava/util/List;II)V"))
       *///?} else {
       @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderComponentTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;II)V"))
       //?}
    private void addEnchantmentDescriptions(GuiGraphics instance, Font textRenderer, List<Component> components, int mouseX, int mouseY, Operation<Void> original, @Local
    //? if >=1.21 {
     Optional<Holder.Reference<Enchantment>> optional
       //?} else {
       /*Enchantment enchantment
       *///?}
    ) {
        if (//? if >=1.21 {
             optional.isPresent() &&
             //?}
         ModHelpers.showEnchantmentDescriptions() && ModClient.CONFIG.enchantmentDescriptions.enchantingTable.value()) {
            Component name =
            //? if >=1.21 {
             Enchantment.getFullname(optional.get(), 1);
            //?} else {
            /*enchantment.getFullname(1);
            *///?}
            if (name.getContents() instanceof TranslatableContents content) {
                List<Component> tooltip = ModHelpers.createTooltip(name, new DescriptionKey(content.getKey()).toText().setStyle(ModStyle.ENCHANTMENT_DESCRIPTIONS), true);
                components.addAll(tooltip);
            }
        }
        original.call(instance, textRenderer, components, mouseX, mouseY);
    }
}
