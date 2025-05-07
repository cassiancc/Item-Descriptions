package cc.cassian.item_descriptions.client.mixin;

import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.font.TextRenderer;


//? if >1.21.2 {
import net.minecraft.client.gui.screen.ingame.StatusEffectsDisplay;
//?} else {
/*import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
*///?}
//? if >1.21 {
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.tooltip.TooltipData;
//?}
//? if >1.20 {
import net.minecraft.client.gui.DrawContext;
 //?}
//? if =1.19.2 {
/*import net.minecraft.client.util.math.MatrixStack;
*///?}
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

//? if >1.21.2 {
@Mixin(StatusEffectsDisplay.class)
//?} else
/*@Mixin(AbstractInventoryScreen.class)*/
public abstract class StatusEffectsDisplayMixin {

    //? if =1.21.5 || =1.21.4 {
    @Shadow protected abstract void drawStatusEffectDescriptions(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects);

    @Inject(method = "drawStatusEffects(Lnet/minecraft/client/gui/DrawContext;II)V", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/screen/ingame/StatusEffectsDisplay;drawStatusEffectSprites(Lnet/minecraft/client/gui/DrawContext;IILjava/lang/Iterable;Z)V"))
    private void forceShowDescriptions(DrawContext context, int mouseX, int mouseY, CallbackInfo ci, @Local LocalBooleanRef booleanRef, @Local(ordinal = 2) int i, @Local(ordinal = 3) int k, @Local Iterable<StatusEffectInstance> iterable) {
        if (booleanRef.get())
            this.drawStatusEffectDescriptions(context, i, k, iterable);
        booleanRef.set(false);
    }

    //?}
    //? if >1.21.5 {
    /*@Shadow protected abstract void drawStatusEffectDescriptions(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects);

    @Redirect(method = "drawStatusEffectTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void renderEffects(DrawContext context, TextRenderer textRenderer, List<Text> text, Optional<TooltipData> data, int mouseX, int mouseY) {
        List<Text> tooltipText = ModHelpers.createEffectDescription(text);
        context.drawTooltip(textRenderer, tooltipText, Optional.empty(), mouseX, mouseY);
    }
    @Inject(method = "drawStatusEffects", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/screen/ingame/StatusEffectsDisplay;drawStatusEffectSprites(Lnet/minecraft/client/gui/DrawContext;IILjava/lang/Iterable;Z)V"))
    private void forceShowDescriptions(DrawContext context, int mouseX, int mouseY, CallbackInfo ci, @Local LocalBooleanRef booleanRef, @Local(name = "i") int i, @Local(name = "k") int k, @Local Iterable<StatusEffectInstance> iterable) {
        if (booleanRef.get())
            this.drawStatusEffectDescriptions(context, i, k, iterable);
        booleanRef.set(false);
    }

    *///?} else if >1.21.1 {
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
    @Shadow protected abstract void drawStatusEffectDescriptions(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects);

    @Inject(method = "drawStatusEffects(Lnet/minecraft/client/gui/DrawContext;II)V", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/screen/ingame/AbstractInventoryScreen;drawStatusEffectSprites(Lnet/minecraft/client/gui/DrawContext;IILjava/lang/Iterable;Z)V"))
    private void forceShowDescriptions(DrawContext context, int mouseX, int mouseY, CallbackInfo ci, @Local LocalBooleanRef booleanRef, @Local(ordinal = 2) int i, @Local(ordinal = 3) int k, @Local Iterable<StatusEffectInstance> iterable) {
        if (booleanRef.get())
            this.drawStatusEffectDescriptions(context, i, k, iterable);
        booleanRef.set(false);
    }
    *///?} else {
    /*@Shadow protected abstract void drawStatusEffectDescriptions(MatrixStack matrices, int x, int height, Iterable<StatusEffectInstance> statusEffects);

    @Redirect(method = "drawStatusEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/AbstractInventoryScreen;renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void renderEffects(AbstractInventoryScreen instance, MatrixStack stack, List<Text> text, Optional optional, int mouseX, int mouseY) {
        List<Text> tooltipText = ModHelpers.createEffectDescription(text);
        instance.renderTooltip(stack, tooltipText, Optional.empty(), mouseX, mouseY);
    }

    @Inject(method = "drawStatusEffects", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/screen/ingame/AbstractInventoryScreen;drawStatusEffectSprites(Lnet/minecraft/client/util/math/MatrixStack;IILjava/lang/Iterable;Z)V"))
    private void forceShowDescriptions(MatrixStack matrices, int mouseX, int mouseY, CallbackInfo ci, @Local LocalBooleanRef booleanRef, @Local(ordinal = 2) int i, @Local(ordinal = 3) int k, @Local Iterable<StatusEffectInstance> iterable) {
        if (booleanRef.get())
            this.drawStatusEffectDescriptions(matrices, i, k, iterable);
        booleanRef.set(false);
    }
    *///?}

}
