package cc.cassian.item_descriptions.client.mixin;

import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import net.minecraft.client.font.TextRenderer;


//? if >1.21.2 {
import net.minecraft.client.gui.screen.ingame.StatusEffectsDisplay;
//?} else {
/*import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
*///?}
//? if >1.21 {
import net.minecraft.item.tooltip.TooltipData;
//?}
//? if >1.20 {
import net.minecraft.client.gui.DrawContext;
 //?}
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Optional;

//? if >1.21.2 {
@Mixin(StatusEffectsDisplay.class)
//?} else
/*@Mixin(AbstractInventoryScreen.class)*/
public class StatusEffectsDisplayMixin {
    //? if >1.21.1 {
    @Redirect(method = "drawStatusEffects(Lnet/minecraft/client/gui/DrawContext;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void renderEffects(DrawContext context, TextRenderer textRenderer, List<Text> text, Optional<TooltipData> data, int mouseX, int mouseY) {
        List<Text> tooltipText = ModHelpers.createEffectDescription(text);
        context.drawTooltip(textRenderer, tooltipText, Optional.empty(), mouseX, mouseY);
    }
    //?} else if >1.20 {
    /*@Redirect(method = "drawStatusEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void renderEffects(DrawContext context, TextRenderer textRenderer, List<Text> text, Optional data, int mouseX, int mouseY) {
        List<Text> tooltipText = ModHelpers.createEffectDescription(text);
        context.drawTooltip(textRenderer, tooltipText, Optional.empty(), mouseX, mouseY);
    }
    *///?} else {
    /*@Redirect(method = "drawStatusEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/AbstractInventoryScreen;renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void renderEffects(AbstractInventoryScreen instance, MatrixStack stack, List<Text> text, Optional optional, int mouseX, int mouseY) {
        List<Text> tooltipText = ModHelpers.createEffectDescription(text);
        instance.renderTooltip(stack, tooltipText, Optional.empty(), mouseX, mouseY);
    }
    *///?}

}
