package cc.cassian.item_descriptions.client.mixin;

import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.gui.Font;
//? if >1.21.2 {
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
//?} else {
/*import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
*///?}
//? if >1.21 {
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
//?}
//? if >1.20 {
import net.minecraft.client.gui.GuiGraphics;
 //?}
//? if =1.19.2 {
/*import net.minecraft.client.util.math.MatrixStack;
*///?}
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

//? if >1.21.2 {
@Mixin(EffectsInInventory.class)
//?} else
/*@Mixin(EffectRenderingInventoryScreen.class)*/
public abstract class StatusEffectsDisplayMixin {

    //? if =1.21.5 || =1.21.4 {
    /*@Shadow protected abstract void renderLabels(GuiGraphics context, int x, int height, Iterable<MobEffectInstance> statusEffects);


    @Inject(method = "renderEffects", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/screens/inventory/EffectsInInventory;renderIcons(Lnet/minecraft/client/gui/GuiGraphics;IILjava/lang/Iterable;Z)V"))
    private void forceShowDescriptions(GuiGraphics context, int mouseX, int mouseY, CallbackInfo ci, @Local LocalBooleanRef booleanRef, @Local(ordinal = 2) int i, @Local Collection<MobEffectInstance> collection, @Local Iterable<MobEffectInstance> iterable) {
        if (booleanRef.get()) {
            int k = 33;
            if (collection.size() > 5) {
                k = 132 / (collection.size() - 1);
            }
            this.renderLabels(context, i, k, iterable);
        }
        booleanRef.set(false);
    }

    *///?}
    //? if >1.21.5 {
    @Shadow protected abstract void renderLabels(GuiGraphics context, int x, int height, Iterable<MobEffectInstance> statusEffects);

    @Redirect(method = "renderTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;setTooltipForNextFrame(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void renderEffects(GuiGraphics context, Font textRenderer, List<Component> text, Optional<TooltipComponent> data, int mouseX, int mouseY) {
        List<Component> tooltipText = ModHelpers.createEffectDescription(text);
        context.setComponentTooltipForNextFrame(textRenderer, tooltipText, mouseX, mouseY);
    }
    @Inject(method = "renderEffects", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/screens/inventory/EffectsInInventory;renderIcons(Lnet/minecraft/client/gui/GuiGraphics;IILjava/lang/Iterable;Z)V"))
    private void forceShowDescriptions(GuiGraphics context, int mouseX, int mouseY, CallbackInfo ci, @Local LocalBooleanRef booleanRef, @Local(ordinal = 2) int i, @Local Collection<MobEffectInstance> collection, @Local Iterable<MobEffectInstance> iterable) {
        if (booleanRef.get()) {
            int k = 33;
            if (collection.size() > 5) {
                k = 132 / (collection.size() - 1);
            }
            this.renderLabels(context, i, k, iterable);
        }
        booleanRef.set(false);
    }

    //?} else if >1.21.1 {
    /*@Redirect(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void renderEffects(GuiGraphics context, Font textRenderer, List<Component> text, Optional<TooltipComponent> data, int mouseX, int mouseY) {
        List<Component> tooltipText = ModHelpers.createEffectDescription(text);
        context.renderTooltip(textRenderer, tooltipText, Optional.empty(), mouseX, mouseY);
    }
    *///?} else if >1.20 {
    /*@Redirect(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void renderEffects(GuiGraphics context, Font textRenderer, List<Component> text, Optional data, int mouseX, int mouseY) {
        List<Component> tooltipText = ModHelpers.createEffectDescription(text);
        context.renderTooltip(textRenderer, tooltipText, Optional.empty(), mouseX, mouseY);
    }
    @Shadow protected abstract void renderLabels(GuiGraphics context, int x, int height, Iterable<MobEffectInstance> statusEffects);

    @Inject(method = "renderEffects", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;renderIcons(Lnet/minecraft/client/gui/GuiGraphics;IILjava/lang/Iterable;Z)V"))
    private void forceShowDescriptions(GuiGraphics context, int mouseX, int mouseY, CallbackInfo ci, @Local LocalBooleanRef booleanRef, @Local(ordinal = 2) int i, @Local Collection<MobEffectInstance> collection, @Local Iterable<MobEffectInstance> iterable) {
        if (booleanRef.get()) {
            int k = 33;
            if (collection.size() > 5) {
                k = 132 / (collection.size() - 1);
            }
            this.renderLabels(context, i, k, iterable);
        }
        booleanRef.set(false);
    }
    *///?} else {
    /*@Shadow protected abstract void drawMobEffectDescriptions(MatrixStack matrices, int x, int height, Iterable<MobEffectInstance> statusEffects);

    @Redirect(method = "drawMobEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/AbstractInventoryScreen;renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void renderEffects(AbstractInventoryScreen instance, MatrixStack stack, List<Text> text, Optional optional, int mouseX, int mouseY) {
        List<Text> tooltipText = ModHelpers.createEffectDescription(text);
        instance.renderTooltip(stack, tooltipText, Optional.empty(), mouseX, mouseY);
    }

    @Inject(method = "drawMobEffects", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/screen/ingame/AbstractInventoryScreen;drawMobEffectSprites(Lnet/minecraft/client/util/math/MatrixStack;IILjava/lang/Iterable;Z)V"))
    private void forceShowDescriptions(MatrixStack matrices, int mouseX, int mouseY, CallbackInfo ci, @Local LocalBooleanRef booleanRef, @Local(ordinal = 2) int i, @Local Collection<MobEffectInstance> collection, @Local Iterable<MobEffectInstance> iterable) {
        if (booleanRef.get()) {
            int k = 33;
            if (collection.size() > 5) {
                k = 132 / (collection.size() - 1);
            }
            this.drawMobEffectDescriptions(matrices, i, k, iterable);
        }
        booleanRef.set(false);
    }
    *///?}

}
