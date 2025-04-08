package cc.cassian.item_descriptions.client.mixin;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
//? if >1.20.5 {
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.component.type.PotionContentsComponent;
//?} else {
/*import net.minecraft.potion.PotionUtil;
*///?}
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

//? if >1.20.5 {
@Mixin(PotionContentsComponent.class)
//?} else {

/*@Mixin(PotionUtil.class)
*///?}
public class PotionContentsComponentMixin {

    //? if =1.21.5 {
    @Inject(method = "buildTooltip", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 0))
    private static void mixin(Iterable<StatusEffectInstance> effects, Consumer<Text> textConsumer, float durationMultiplier, float tickRate, CallbackInfo ci, @Local StatusEffectInstance statusEffectInstance) {
        ModHelpers.createEffectDescription(textConsumer, statusEffectInstance);
    }
    //?} else if >1.20.5 {
    /*@Inject(method = "buildTooltip(Ljava/lang/Iterable;Ljava/util/function/Consumer;FF)V", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 0))
    private static void mixin(Iterable<StatusEffectInstance> effects, Consumer<Text> textConsumer, float durationMultiplier, float tickRate, CallbackInfo ci, @Local StatusEffectInstance statusEffectInstance) {
        ModHelpers.createEffectDescription(textConsumer, statusEffectInstance);
    }
    *///?} else if >1.20 {
    /*@Inject(method = "buildTooltip(Ljava/util/List;Ljava/util/List;F)V", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 2))
    private static void mixin(List<StatusEffectInstance> statusEffects, List<Text> list, float durationMultiplier, CallbackInfo ci, @Local StatusEffectInstance statusEffectInstance) {
        ModHelpers.createEffectDescription(list, statusEffectInstance);
    }
    *///?} else {
    /*@Inject(method = "buildTooltip", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 2))
    private static void mixin(ItemStack stack, List<Text> list, float durationMultiplier, CallbackInfo ci, @Local StatusEffectInstance statusEffectInstance) {
        ModHelpers.createEffectDescription(list, statusEffectInstance);
    }
    *///?}
}
