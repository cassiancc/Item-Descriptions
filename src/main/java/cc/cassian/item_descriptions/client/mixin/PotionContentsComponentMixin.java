package cc.cassian.item_descriptions.client.mixin;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

@Mixin(PotionContents.class)
public class PotionContentsComponentMixin {

    //? if >=1.21.5 {
    @Inject(method = "addPotionTooltip", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 0))
    private static void mixin(Iterable<MobEffectInstance> effects, Consumer<Component> textConsumer, float durationMultiplier, float tickRate, CallbackInfo ci, @Local MobEffectInstance statusEffectInstance) {
        ModHelpers.createEffectDescription(Component.translatable(statusEffectInstance.getDescriptionId()), textConsumer, statusEffectInstance);
    }
    //?} else if >1.20.5 {
    /*@Inject(method = "addPotionTooltip(Ljava/lang/Iterable;Ljava/util/function/Consumer;FF)V", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 0))
    private static void mixin(Iterable<MobEffectInstance> effects, Consumer<Component> textConsumer, float durationMultiplier, float tickRate, CallbackInfo ci, @Local MobEffectInstance statusEffectInstance) {
        ModHelpers.createEffectDescription(Component.translatable(statusEffectInstance.getDescriptionId()), textConsumer, statusEffectInstance);
    }
    *///?} else if >1.20 {
    /*@Inject(method = "addPotionTooltip(Ljava/util/List;Ljava/util/List;F)V", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 2))
    private static void mixin(List<MobEffectInstance> effects, List<Component> list, float durationFactor, CallbackInfo ci, @Local MobEffectInstance statusEffectInstance) {
        ModHelpers.createEffectDescription(Component.translatable(statusEffectInstance.getDescriptionId()), list, statusEffectInstance);
    }
    *///?} else {
    /*@Inject(method = "addPotionTooltip", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 2))
    private static void mixin(ItemStack stack, List<Component> list, float durationMultiplier, CallbackInfo ci, @Local MobEffectInstance statusEffectInstance) {
        ModHelpers.createEffectDescription(Component.translatable(statusEffectInstance.getDescriptionId()), list, statusEffectInstance);
    }
    *///?}
}
