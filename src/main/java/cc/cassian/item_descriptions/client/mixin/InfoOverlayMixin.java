package cc.cassian.item_descriptions.client.mixin;

import com.brokenkeyboard.usefulspyglass.InfoOverlay;
import com.brokenkeyboard.usefulspyglass.TooltipInfo;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.HitResult;
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

    @Inject(method = "setHitResult", at = @At(value = "INVOKE", target = "Lcom/brokenkeyboard/usefulspyglass/InfoOverlay;setComponent(Ljava/util/List;)V", ordinal = 0), remap = false, require = 0)
    private static void spyglassEntityDescriptions(HitResult result, CallbackInfo ci, @Local ArrayList<TooltipInfo> tooltipList, @Local LivingEntity entity) {
        if (showEntityDescriptions()) {
            //Create and add tooltip.
            List<Component> tooltip = createTooltip(entity.getName(), findEntityLoreKey(entity));
            for (Component text : tooltip) {
                tooltipList.add(new TooltipInfo.TextTooltip(ClientTooltipComponent.create(text.getVisualOrderText())));
            }
        }
    }

    @Inject(method = "setHitResult", at = @At(value = "INVOKE", target = "Lcom/brokenkeyboard/usefulspyglass/InfoOverlay;setComponent(Ljava/util/List;)V", ordinal = 1), remap = false, require = 0)
    private static void spyglassBlockDescriptions(HitResult result, CallbackInfo ci, @Local ArrayList<TooltipInfo> tooltipList, @Local BlockState state) {
        if (showBlockDescriptions()) {
            //Create and add tooltip.
            List<Component> tooltip = createTooltip(state.getBlock().getName(), findBlockLoreKey(state.getBlock()));
            for (Component text : tooltip) {
                tooltipList.add(new TooltipInfo.BlockInfo(ClientTooltipComponent.create(text.getVisualOrderText())));
            }
        }
    }
}
