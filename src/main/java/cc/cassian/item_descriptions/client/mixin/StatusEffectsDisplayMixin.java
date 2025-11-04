package cc.cassian.item_descriptions.client.mixin;

import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.Minecraft;
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
/*import com.mojang.blaze3d.vertex.PoseStack;
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

    //? if >1.21.10 {
    /*@WrapOperation(method = "renderText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;setTooltipForNextFrame(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void renderEffects(GuiGraphics instance, Font font, List<Component> lines, Optional<TooltipComponent> tooltipImage, int x, int y, Operation<Void> original) {
        List<Component> tooltipText = ModHelpers.createEffectDescription(lines);
        original.call(instance, font, tooltipText, tooltipImage, x, y);
    }

    @Inject(method = "renderText", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;III)V"))
    private void forceShowDescriptions(GuiGraphics guiGraphics, Component component, Component component2, Font font, int i, int j, int k, int l, int m, int n, CallbackInfo ci, @Local LocalBooleanRef bl) {
        if (!bl.get())
            bl.set(true);
    }

    *///?} else if >1.21.5 {
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
    /*@Shadow protected abstract void renderLabels(PoseStack matrices, int x, int height, Iterable<MobEffectInstance> statusEffects);

    @Redirect(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;renderTooltip(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void renderEffects(EffectRenderingInventoryScreen instance, PoseStack stack, List<Component> text, Optional optional, int mouseX, int mouseY) {
        List<Component> tooltipText = ModHelpers.createEffectDescription(text);
        instance.renderTooltip(stack, tooltipText, Optional.empty(), mouseX, mouseY);
    }

    @Inject(method = "renderEffects", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;renderIcons(Lcom/mojang/blaze3d/vertex/PoseStack;IILjava/lang/Iterable;Z)V"))
    private void forceShowDescriptions(PoseStack matrices, int mouseX, int mouseY, CallbackInfo ci, @Local LocalBooleanRef booleanRef, @Local(ordinal = 2) int i, @Local Collection<MobEffectInstance> collection, @Local Iterable<MobEffectInstance> iterable) {
        if (booleanRef.get()) {
            int k = 33;
            if (collection.size() > 5) {
                k = 132 / (collection.size() - 1);
            }
            this.renderLabels(matrices, i, k, iterable);
        }
        booleanRef.set(false);
    }
    *///?}

}
