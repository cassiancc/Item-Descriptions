package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.ModClient;
import net.minecraft.text.Style;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.getStyle;

public class ModStyle {
    public static Style HINT = getStyle(ModClient.CONFIG.hint.color).withItalic(ModClient.CONFIG.hint.italics);
    public static Style MOD_NAME = getStyle(ModClient.CONFIG.style.modNameColor).withItalic(true);
    public static Style ITEM_DESCRIPTIONS = getStyle(ModClient.CONFIG.style.color);
    public static Style ENCHANTMENT_DESCRIPTIONS = getStyle(ModClient.CONFIG.enchantmentDescriptions.color).withItalic(ModClient.CONFIG.enchantmentDescriptions.italics);
    public static Style EFFECT_DESCRIPTIONS = getStyle(ModClient.CONFIG.effectDescriptions.color);

    public static void updateStyles() {
        HINT = getStyle(ModClient.CONFIG.hint.color).withItalic(ModClient.CONFIG.hint.italics);
        MOD_NAME = getStyle(ModClient.CONFIG.style.modNameColor).withItalic(true);
        ITEM_DESCRIPTIONS = getStyle(ModClient.CONFIG.style.color);
        ENCHANTMENT_DESCRIPTIONS = getStyle(ModClient.CONFIG.enchantmentDescriptions.color).withItalic(ModClient.CONFIG.enchantmentDescriptions.italics);
        EFFECT_DESCRIPTIONS = getStyle(ModClient.CONFIG.effectDescriptions.color);
    }
}
