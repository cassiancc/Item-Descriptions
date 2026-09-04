package cc.cassian.item_descriptions.client.mixin;

import cc.cassian.item_descriptions.client.descriptions.EffectDescriptions;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mixin(EffectsInInventory.class)
public abstract class StatusEffectsDisplayMixin {

    //? if fabric {

    @WrapOperation(method = "extractText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;setTooltipForNextFrame(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void renderEffects(GuiGraphicsExtractor instance, Font font, List<Component> lines, Optional<TooltipComponent> tooltipImage, int x, int y, Operation<Void> original) {
        List<Component> tooltipText = EffectDescriptions.createEffectDescription(lines);
        original.call(instance, font, tooltipText, tooltipImage, x, y);
    }

    @Inject(method = "extractText", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"))
    private void forceShowDescriptions(GuiGraphicsExtractor graphics, Component effectText, Component duration, Font font, int x0, int y0, int textureWidth, int yStep, int mouseX, int mouseY, CallbackInfo ci, @Local(name = "shouldClip") LocalBooleanRef bl) {
        if (!bl.get())
            bl.set(true);
    }

    //?}
}
