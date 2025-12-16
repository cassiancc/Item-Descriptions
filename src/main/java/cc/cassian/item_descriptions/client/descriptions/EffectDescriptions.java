package cc.cassian.item_descriptions.client.descriptions;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import cc.cassian.item_descriptions.client.helpers.ModStyle;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EffectDescriptions {
    /**
     * Check if another Enchantment Descriptions is installed and our descriptions should be disabled.
     */
    public static boolean useInternalEffectDescriptions() {
        if (ModClient.CONFIG.developerOptions.forceEnableEffectDescriptions.value())
            return true;
        else return !(ModHelpers.isLoaded("potiondescriptions") || ModHelpers.isLoaded("effectdescriptions"));
    }

    public static List<Component> createEffectDescription(List<Component> text) {
        ArrayList<Component> lines = new ArrayList<>(text);
        if (ModClient.CONFIG.effectDescriptions.enable.value()) {
            for (Component text1 : text) {
                if (text1.getContents() instanceof TranslatableContents translatableTextContent) {
                    if (!translatableTextContent.getKey().startsWith("effect.duration")) {
                        var key = new DescriptionKey(translatableTextContent.getKey());
                        List<Component> tooltip = ModHelpers.createTooltip(text1, key.toString(), true, ModStyle.EFFECT_DESCRIPTIONS);
                        if (showEffectDescriptions()) {
                            lines.addAll(tooltip);
                        } else if (ModClient.CONFIG.hint.enabled.value() && (key.hasTranslation())) {
                            ModHelpers.addHint(lines);
                        }
                    }
                }
            }
        }
        return lines;
    }

    public static void createEffectDescription(Component name, Consumer<Component> textConsumer, MobEffectInstance statusEffectInstance) {
        if (ModClient.CONFIG.effectDescriptions.enable.value() && showEffectDescriptions()) {
            var key = new DescriptionKey(statusEffectInstance.getDescriptionId());
            List<Component> tooltip = ModHelpers.createTooltip(name, key.toString(), true, ModStyle.EFFECT_DESCRIPTIONS);
            if (showEffectDescriptions()) {
                for (Component line : tooltip) {
                    textConsumer.accept(line);
                }
            }
        }
    }

    public static void createEffectDescription(Component name, List<Component> textConsumer, MobEffectInstance statusEffectInstance) {
        if (ModClient.CONFIG.effectDescriptions.enable.value() && showEffectDescriptions()) {
            var key = new DescriptionKey(statusEffectInstance.getDescriptionId());
            List<Component> tooltip = ModHelpers.createTooltip(name, key.toString(), true, ModStyle.EFFECT_DESCRIPTIONS);
            if (showEffectDescriptions()) {
                textConsumer.addAll(tooltip);
            }
        }
    }

    /**
     * Check if effect descriptions should be shown based off configuration.
     */
    public static boolean showEffectDescriptions() {
        return ModClient.CONFIG.effectDescriptions.enable.value() && useInternalEffectDescriptions() && (ModHelpers.tooltipKeyPressed() || ModClient.CONFIG.effectDescriptions.displayAlways.value());
    }

    public static boolean checkForEffectDescription(ItemStack stack) {
        if (ItemDescriptions.hasComponent(stack, DataComponents.POTION_CONTENTS)) {
            var contents = stack.getComponents().get(DataComponents.POTION_CONTENTS);
            if (contents == null) return false;
            for (MobEffectInstance effect : contents.getAllEffects()) {
                var key = new DescriptionKey(effect.getDescriptionId());
                if (key.hasTranslation()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static @NotNull DescriptionKey getDescriptionKey(MobEffect effect) {
        return new DescriptionKey(effect.getDescriptionId());
    }
}
