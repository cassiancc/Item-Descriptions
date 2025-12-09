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
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
//? if (=1.21.1 && neoforge) {
/*import org.violetmoon.quark.addons.oddities.client.screen.MatrixEnchantingScreen;
import org.violetmoon.quark.addons.oddities.inventory.EnchantmentMatrix;
*///?} else {
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
 //?}

import java.util.List;
import java.util.Optional;

@Pseudo
//? if (=1.21.1 && neoforge) {
/*@Mixin(MatrixEnchantingScreen.class)
*///?} else {
@Mixin(EnchantmentScreen.class)
//?}
public class MatrixEnchantmentScreenMixin {
    //? if (=1.21.1 && neoforge) {
    /*@Shadow protected EnchantmentMatrix.Piece hoveredPiece;

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderComponentTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;II)V"))
    private void addEnchantmentDescriptions(GuiGraphics instance, Font textRenderer, List<Component> components, int mouseX, int mouseY, Operation<Void> original) {
        //? if >=1.21 {
        Holder<Enchantment> enchant
        //?} else {
        /^Enchantment enchant
         ^///?}
        = this.hoveredPiece.enchant;
        if (ModHelpers.showEnchantmentDescriptions() && ModClient.CONFIG.enchantmentDescriptions.enchantingTable.value()) {
            Component name =
            //? if >=1.21 {
             Enchantment.getFullname(enchant, 1);
            //?} else {
            /^enchant.getFullname(1);
            ^///?}
            if (name.getContents() instanceof TranslatableContents content) {
                List<Component> tooltip = ModHelpers.createTooltip(name, new DescriptionKey(content.getKey()).toText().setStyle(ModStyle.ENCHANTMENT_DESCRIPTIONS), true);
                components.addAll(tooltip);
            }
        }
        original.call(instance, textRenderer, components, mouseX, mouseY);
    }
    *///?}
}
