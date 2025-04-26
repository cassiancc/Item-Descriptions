package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.config.ModConfig;
import net.minecraft.text.Style;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.getStyle;

public class ModStyle {
    public static Style HINT = getStyle(ModConfig.get().hint_color).withItalic(ModConfig.get().hint_italics);
    public static Style MOD_NAME = getStyle(ModConfig.get().style_modNameColor).withItalic(true);
    public static Style ITEM_DESCRIPTIONS = getStyle(ModConfig.get().style_color);
    public static Style ENCHANTMENT_DESCRIPTIONS = getStyle(ModConfig.get().enchantmentDescriptions_color).withItalic(ModConfig.get().enchantmentDescriptions_italics);
    public static Style EFFECT_DESCRIPTIONS = getStyle(ModConfig.get().effect_descriptions_color);

    public static void updateStyles() {
        HINT = getStyle(ModConfig.get().hint_color).withItalic(ModConfig.get().hint_italics);
        MOD_NAME = getStyle(ModConfig.get().style_modNameColor).withItalic(true);
        ITEM_DESCRIPTIONS = getStyle(ModConfig.get().style_color);
        ENCHANTMENT_DESCRIPTIONS = getStyle(ModConfig.get().enchantmentDescriptions_color).withItalic(ModConfig.get().enchantmentDescriptions_italics);
        EFFECT_DESCRIPTIONS = getStyle(ModConfig.get().effect_descriptions_color);
    }
}
