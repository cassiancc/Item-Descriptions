package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.ModClient;
import net.minecraft.network.chat.Style;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.getStyle;

public class ModStyle {
    public static Style HINT = getStyle(ModClient.CONFIG.hint.colour.value()).withItalic(ModClient.CONFIG.hint.italics.value());
    public static Style MOD_NAME = getStyle(ModClient.CONFIG.style.modNameColour.value()).withItalic(true);
    public static Style ITEM_DESCRIPTIONS = getStyle(ModClient.CONFIG.style.colour.value());
    public static Style ENCHANTMENT_DESCRIPTIONS = getStyle(ModClient.CONFIG.enchantmentDescriptions.colour.value()).withItalic(ModClient.CONFIG.enchantmentDescriptions.italics.value());
    public static Style EFFECT_DESCRIPTIONS = getStyle(ModClient.CONFIG.effectDescriptions.colour.value());

    public static void updateStyles() {
        HINT = getStyle(ModClient.CONFIG.hint.colour.value()).withItalic(ModClient.CONFIG.hint.italics.value());
        MOD_NAME = getStyle(ModClient.CONFIG.style.modNameColour.value()).withItalic(ModClient.CONFIG.style.modNameItalics.value());
        ITEM_DESCRIPTIONS = getStyle(ModClient.CONFIG.style.colour.value());
        ENCHANTMENT_DESCRIPTIONS = getStyle(ModClient.CONFIG.enchantmentDescriptions.colour.value()).withItalic(ModClient.CONFIG.enchantmentDescriptions.italics.value());
        EFFECT_DESCRIPTIONS = getStyle(ModClient.CONFIG.effectDescriptions.colour.value());
    }
}
