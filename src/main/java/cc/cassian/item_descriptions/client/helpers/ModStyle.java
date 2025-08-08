package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.ModClient;
import net.minecraft.network.chat.Style;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.getStyle;

public class ModStyle {
    public static Style HINT = getStyle(ModClient.CONFIG.hint.color.value()).withItalic(ModClient.CONFIG.hint.italics.value());
    public static Style MOD_NAME = getStyle(ModClient.CONFIG.style.modNameColor.value()).withItalic(true);
    public static Style ITEM_DESCRIPTIONS = getStyle(ModClient.CONFIG.style.color.value());
    public static Style ENCHANTMENT_DESCRIPTIONS = getStyle(ModClient.CONFIG.enchantmentDescriptions.color.value()).withItalic(ModClient.CONFIG.enchantmentDescriptions.italics.value());
    public static Style EFFECT_DESCRIPTIONS = getStyle(ModClient.CONFIG.effectDescriptions.color.value());

    public static void updateStyles() {
        HINT = getStyle(ModClient.CONFIG.hint.color.value()).withItalic(ModClient.CONFIG.hint.italics.value());
        MOD_NAME = getStyle(ModClient.CONFIG.style.modNameColor.value()).withItalic(ModClient.CONFIG.style.modNameItalics.value());
        ITEM_DESCRIPTIONS = getStyle(ModClient.CONFIG.style.color.value());
        ENCHANTMENT_DESCRIPTIONS = getStyle(ModClient.CONFIG.enchantmentDescriptions.color.value()).withItalic(ModClient.CONFIG.enchantmentDescriptions.italics.value());
        EFFECT_DESCRIPTIONS = getStyle(ModClient.CONFIG.effectDescriptions.color.value());
    }
}
