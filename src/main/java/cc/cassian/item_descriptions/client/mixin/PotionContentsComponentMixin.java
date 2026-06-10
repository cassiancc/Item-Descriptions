package cc.cassian.item_descriptions.client.mixin;

import cc.cassian.item_descriptions.client.descriptions.EffectDescriptions;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(PotionContents.class)
public class PotionContentsComponentMixin {

    @Inject(method = "addPotionTooltip(Ljava/lang/Iterable;Ljava/util/function/Consumer;FF)V", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 0))
    private static void mixin(Iterable<MobEffectInstance> effects, Consumer<Component> textConsumer, float durationMultiplier, float tickRate, CallbackInfo ci, @Local MobEffectInstance statusEffectInstance) {
        EffectDescriptions.createEffectDescription(Component.translatable(statusEffectInstance.getDescriptionId()), textConsumer, statusEffectInstance);
    }
}
