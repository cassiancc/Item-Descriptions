package cc.cassian.item_descriptions.client.mixin;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import cc.cassian.item_descriptions.client.helpers.ModStyle;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Optional;

@Mixin(EnchantmentScreen.class)
public class EnchantmentScreenMixin {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;II)V"))
    private void forceShowDescriptions(DrawContext instance, TextRenderer textRenderer, List<Text> components, int mouseX, int mouseY, Operation<Void> original, @Local
    //? if >=1.21 {
     Optional<RegistryEntry.Reference<Enchantment>> optional
     //?} else {
       /*Enchantment enchantment
       *///?}
    ) {
        if (//? if >=1.21 {
             optional.isPresent() &&
             //?}
         ModHelpers.showEnchantmentDescriptions() && ModClient.CONFIG.enchantmentDescriptions.enchantingTable.value()) {
            Text name =
            //? if >=1.21 {
             Enchantment.getName(optional.get(), 1);
            //?} else {
            /*enchantment.getName(1);
            *///?}
            if (name.getContent() instanceof TranslatableTextContent content) {
                List<Text> tooltip = ModHelpers.createTooltip(name, new DescriptionKey(content.getKey()).toText().setStyle(ModStyle.ENCHANTMENT_DESCRIPTIONS), true);
                components.addAll(tooltip);
            }
        }
        original.call(instance, textRenderer, components, mouseX, mouseY);
    }
}
