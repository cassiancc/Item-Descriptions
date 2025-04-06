package cc.cassian.item_descriptions.client.mixin;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.config.ModConfig;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.StatusEffectsDisplay;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Debug(export = true)
@Mixin(StatusEffectsDisplay.class)
public class StatusEffectsDisplayMixin {
    @Redirect(method = "drawStatusEffects(Lnet/minecraft/client/gui/DrawContext;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void renderEffects(DrawContext context, TextRenderer textRenderer, List<Text> text, Optional<TooltipData> data, int mouseX, int mouseY) {
        ArrayList<Text> objects = new ArrayList<>(text);
        for (Text text1 : text) {
            if (text1.getContent() instanceof TranslatableTextContent translatableTextContent) {
                if (!translatableTextContent.getKey().startsWith("effect.duration")) {
                    List<Text> tooltip = ModHelpers.createTooltip(new DescriptionKey(translatableTextContent.getKey()).toString(), true, ModHelpers.getStyle(ModConfig.get().enchantmentDescriptions_color));
                    objects.addAll(tooltip);
                }
            }
        }
        context.drawTooltip(textRenderer, objects, Optional.empty(), mouseX, mouseY);
    }
}
