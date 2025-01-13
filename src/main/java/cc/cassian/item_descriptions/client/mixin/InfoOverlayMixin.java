package cc.cassian.item_descriptions.client.mixin;

import com.brokenkeyboard.usefulspyglass.InfoOverlay;
import com.brokenkeyboard.usefulspyglass.TooltipInfo;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

@Pseudo
@Mixin(InfoOverlay.class)
public class InfoOverlayMixin {
    //? if >1.20 {
    @Inject(method = "setHitResult", at = @At(value = "INVOKE", target = "Lcom/brokenkeyboard/usefulspyglass/InfoOverlay;setComponent(Ljava/util/List;)V", ordinal = 0), remap = false)
    private static void spyglassEntityDescriptions(HitResult result, CallbackInfo ci, @Local ArrayList<TooltipInfo> tooltipList, @Local LivingEntity entity) {
        if (showEntityDescriptions()) {
            //Create and add tooltip.
            List<Text> tooltip = createTooltip(findEntityLoreKey(entity), true);
            for (Text text : tooltip) {
                tooltipList.add(new TooltipInfo.TextTooltip(TooltipComponent.of(text.asOrderedText())));
            }
        }
    }

    @Inject(method = "setHitResult", at = @At(value = "INVOKE", target = "Lcom/brokenkeyboard/usefulspyglass/InfoOverlay;setComponent(Ljava/util/List;)V", ordinal = 1), remap = false)
    private static void spyglassBlockDescriptions(HitResult result, CallbackInfo ci, @Local ArrayList<TooltipInfo> tooltipList, @Local BlockState state) {
        if (showBlockDescriptions()) {
            //Create and add tooltip.
            List<Text> tooltip = createTooltip(findBlockLoreKey(state.getBlock()), true);
            for (Text text : tooltip) {
                tooltipList.add(new TooltipInfo.BlockInfo(TooltipComponent.of(text.asOrderedText())));
            }
        }
    }
    ///?}
}
