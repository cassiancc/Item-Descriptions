package cc.cassian.item_descriptions.client.mixin;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
//? if (=1.21.1 && neoforge) {
/*import org.violetmoon.quark.addons.oddities.client.screen.MatrixEnchantingScreen;
import org.violetmoon.quark.addons.oddities.inventory.EnchantmentMatrix;
import net.minecraft.client.gui.GuiGraphics;
*///?} else {
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
 //?}
//? if 26.1 && fabric {
import spookipup.matrixenchanting.client.screen.MatrixEnchantingScreen;
import spookipup.matrixenchanting.enchanting.EnchantmentMatrix;
//?}
import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.descriptions.EnchantmentDescriptions;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import cc.cassian.item_descriptions.client.helpers.ModStyle;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Font;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;
import java.util.Optional;

@Pseudo
//? if (=1.21.1 && neoforge) || (26.1 && fabric) {
@Mixin(MatrixEnchantingScreen.class)
//?} else {
/*@Mixin(EnchantmentScreen.class)
*///?}
public class MatrixEnchantmentScreenMixin {
    //? if (=1.21.1 && neoforge) {
    /*@Shadow protected EnchantmentMatrix.Piece hoveredPiece;

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderComponentTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;II)V"))
    private void addEnchantmentDescriptions(GuiGraphics instance, Font textRenderer, List<Component> components, int mouseX, int mouseY, Operation<Void> original) {
        Holder<Enchantment> enchant = this.hoveredPiece.enchant;
        EnchantmentDescriptions.addEnchantmentDescription(components, enchant);
        original.call(instance, textRenderer, components, mouseX, mouseY);
    }
    *///?}

    //? if (=26.1 && fabric) {
    @WrapOperation(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;setComponentTooltipForNextFrame(Lnet/minecraft/client/gui/Font;Ljava/util/List;II)V"))
    private void addEnchantmentDescriptions(GuiGraphicsExtractor instance, Font textRenderer, List<Component> components, int mouseX, int mouseY, Operation<Void> original, @Local(name = "piece") EnchantmentMatrix.Piece piece) {
        Holder<Enchantment> enchant = piece.enchant;
        EnchantmentDescriptions.addEnchantmentDescription(components, enchant);
        original.call(instance, textRenderer, components, mouseX, mouseY);
    }


    //?}
}
