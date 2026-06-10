package cc.cassian.item_descriptions.client.mixin;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import cc.cassian.item_descriptions.client.helpers.ModStyle;
import cc.cassian.item_descriptions.client.descriptions.EnchantmentDescriptions;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Font;
//? if >26 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
*///?} else {
import net.minecraft.client.gui.GuiGraphics;
 //?}
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
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderComponentTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;II)V"))
    private void addEnchantmentDescriptions(GuiGraphics
    instance, Font textRenderer, List<Component> components, int mouseX, int mouseY, Operation<Void> original, @Local Optional<Holder.Reference<Enchantment>> optional) {
        optional.ifPresent(enchantmentReference -> EnchantmentDescriptions.addEnchantmentDescription(components, enchantmentReference));
        original.call(instance, textRenderer, components, mouseX, mouseY);
    }
}
