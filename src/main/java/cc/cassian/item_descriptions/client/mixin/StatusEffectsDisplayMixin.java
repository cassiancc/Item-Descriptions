package cc.cassian.item_descriptions.client.mixin;

import cc.cassian.item_descriptions.client.descriptions.EffectDescriptions;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class StatusEffectsDisplayMixin {


    @WrapOperation(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void renderEffects(GuiGraphics context, Font textRenderer, List<Component> text, Optional<TooltipComponent> visualTooltipComponent, int mouseX, int mouseY, Operation<Void> original) {
        List<Component> tooltipText = EffectDescriptions.createEffectDescription(text);
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

}