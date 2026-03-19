package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.Platform;
import cc.cassian.item_descriptions.client.descriptions.*;
import net.minecraft.client.gui.screens.Screen;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.*;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.Identifier;


import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static cc.cassian.item_descriptions.client.ModClient.LOGGER;
import static cc.cassian.item_descriptions.client.ModClient.MOD_ID;

public class ModHelpers {
    public static final Identifier FABRIC_EVENT_PHASE = of("description_tooltip");

    public static Identifier of(String path) {
        return ModHelpers.of(MOD_ID, path);
    }

    public static Identifier of(String namespace, String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }

    /**
     * Check if ToolTipFix is installed and its wrapper should be used.
     */
    public static boolean useInternalWrapper() {
        return !isLoaded("tooltipfix");
    }

    /**
     * Check if a mod is loaded
     */
    public static boolean isLoaded(String mod) {
        return Platform.INSTANCE.isLoaded(mod);
    }

    /**
     * Used in Config to change the tooltip's formatting.
     */
    public static Style getStyle(int colour) {
        return Style.EMPTY.withColor(colour).withItalic(ModClient.CONFIG.style.italics.value()).withBold(ModClient.CONFIG.style.bold.getDefaultValue());
    }

    /**
     * Used in Config to change the tooltip's formatting.
     */
    public static MutableComponent getHintText() {
        var sb = new StringBuilder();
        var config = ModClient.CONFIG;
        var shift = config.keybinds.displayWhenShiftIsHeld.value();
        var ctrl = config.keybinds.displayWhenCtrlIsHeld.value();
        var alt = config.keybinds.displayWhenAltIsHeld.value();
        if (config.hint.showKeybinds.value()) {
            if (ctrl) {
                var ctrlText = I18n.get("key.keyboard.ctrl");
                if (ModClient.CONFIG.hint.uppercase.value()) ctrlText = ctrlText.toUpperCase(Locale.ROOT);
                sb.append(ctrlText);
                if (shift || alt) sb.append("/");
            }
            if (alt) {
                var altText = I18n.get("key.keyboard.alt");
                if (ModClient.CONFIG.hint.uppercase.value()) altText = altText.toUpperCase(Locale.ROOT);
                sb.append(altText);
                if (shift) sb.append("/");
            }
            if (shift) {
                var shiftText = I18n.get("key.keyboard.shift");
                if (ModClient.CONFIG.hint.uppercase.value()) shiftText = shiftText.toUpperCase(Locale.ROOT);
                sb.append(shiftText);
            }
            sb.append(": ");
        }
        if (config.keybinds.invert.value())
            sb.append(I18n.get("hint.item_descriptions.hint_inverted"));
        else
            sb.append(I18n.get("hint.item_descriptions.hint"));
        return Component.literal(sb.toString());
    }

    /**
     * Handles detection of when a line break should be added in a tooltip.
     */
    public static int getIndex(String translatedKey, int maxLength) {
        String subKey = translatedKey.substring(0, maxLength);
        int index;
        //Find the last space character in the substring, if not, default to the length of the substring.
        if (subKey.contains(" ")) {
            index = subKey.lastIndexOf(" ");
        }
        else index = maxLength;
        return index;
    }

    /**
     * Check if a keybind is pressed and a tooltip should be displayed.
     */
    public static boolean tooltipKeyPressed() {
        var ctrl =
            //? if >1.21.8 {
            Minecraft.getInstance()
            //?} else {
            /*Screen
            *///?}
            .hasControlDown();
        var alt =
            //? if >1.21.8 {
            Minecraft.getInstance()
            //?} else {
            /*Screen
            *///?}
            .hasAltDown();
        var shift =
            //? if >1.21.8 {
            Minecraft.getInstance()
            //?} else {
            /*Screen
            *///?}
            .hasShiftDown();
        if (ModClient.CONFIG.keybinds.displayWhenCtrlIsHeld.value() && ctrl) return checkKey(ctrl);
        else if (ModClient.CONFIG.keybinds.displayWhenShiftIsHeld.value() && shift) return checkKey(shift);
        else if (ModClient.CONFIG.keybinds.displayWhenAltIsHeld.value() && alt) return checkKey(alt);
        else return false;
    }

    /**
     * Check if a keybind is pressed. Contains the handling for if the key is inverted.
     */
    @SuppressWarnings({"DuplicateCondition", "ConstantValue"})
    public static boolean checkKey(boolean key) {
        boolean invert = ModClient.CONFIG.keybinds.invert.value();
        //If key is pressed, display the tooltip unless inverted.
        if (key) return !invert;
        //If key is not pressed, don't display the tooltip unless inverted.
        else if (!key) return invert;
        else return false;
    }

    public static String toTranslationKey(String string) {
        return string.toLowerCase().replaceAll("\"", "").replaceAll(" ", "_").replaceAll("[/:]", ".");
    }

    public static boolean hasTranslation(String key) {
        if (ModClient.CONFIG.developerOptions.showUntranslated.value()) return true;
        return I18n.exists(key);
    }

    public static boolean checkTranslatableText(Component text, Predicate<TranslatableContents> predicate) {
        ComponentContents contents = null;
        if (text instanceof MutableComponent mutable) {
            if (mutable.getSiblings().stream().anyMatch(c -> checkTranslatableText(c, predicate))) {
                return true;
            }
            contents = mutable.getContents();
        }
        if (contents == null)
            return false;
        return contents instanceof TranslatableContents translatable && predicate.test(translatable);
    }

    public static void addHint(List<Component> lines) {
        lines.add(1, getHintText().setStyle(ModStyle.HINT));
    }

    public static MutableComponent translatableWithFallback(String translatable, String fallback) {
        return Component.translatableWithFallback(translatable, fallback);
    }

    /**
     * Create a custom multi-line tooltip.
     *
     * @param loreKey The translation key that will be translated and wrapped.
     */
    public static List<Component> createTooltip(Component name, DescriptionKey loreKey) {
        return createTooltip(name, loreKey.toString(), true);
    }

    /**
     * Create a custom multi-line tooltip.
     *
     * @param loreKey The translation key that will be translated and wrapped.
     */
    public static List<Component> createTooltip(Component name, String loreKey) {
        return createTooltip(name, loreKey, true);
    }

    /**
     * Create a custom, potentially multi-line tooltip.
     * @param loreKey The translation key that will be translated.
     * @param wrap Whether to use the built-in wrapper.
     */
    public static List<Component> createTooltip(Component name, String loreKey, boolean wrap) {
        return createTooltip(name, loreKey, wrap, ModStyle.ITEM_DESCRIPTIONS);
    }

    public static List<Component> createTooltip(Component name, Component text, boolean wrap) {
        if (!wrap || text.getString().isEmpty())
            return List.of(text);
        //Setup list to store (potentially multi-line) tooltip.
        ArrayList<Component> lines = new ArrayList<>();
        //Check if the key exists.
        wrapTooltip(name, lines, List.of(text));
        resetWrapValues();
        return lines;
    }

    /**
     * Create a custom, potentially multi-line tooltip.
     *
     * @param loreKey The translation key that will be translated.
     * @param wrap    Whether to use the built-in wrapper.
     * @param style   How to style the text content
     */
    public static List<Component> createTooltip(Component name, String loreKey, boolean wrap, Style style) {
        //Setup list to store (potentially multi-line) tooltip.
        ArrayList<Component> lines = new ArrayList<>();
        //Check if the key exists.
        if (!loreKey.isBlank()) {
            //Translate the lore key.
            String translatedKey = I18n.get(loreKey);
            //Check if the translated key exists.
            if (hasTranslation(loreKey)) {
                if (!wrap) {
                    if (!translatedKey.isBlank()) lines.add(Component.translatable(loreKey).setStyle(style));
                }
                else {
                    wrapTooltip(name, lines, List.of(Component.literal(translatedKey).setStyle(style)));
                    resetWrapValues();
                }
            }
        }
        return lines;
    }

    // Store this outside of the method to make sure that it can be carried over to other calls of wrapTooltip.
    private static int lineTextWidth = 0;
    // This is a hook for any mod that wishes to indent the description text while it is translatable.
    private static boolean shouldIndent = false;
    private static Component indentationText = null;

    private static void resetWrapValues() {
        lineTextWidth = 0;
        shouldIndent = true;
        indentationText = null;
    }


    private static void wrapTooltip(Component name, List<Component> lines, List<Component> keys) {
        Minecraft.getInstance().execute(() -> wrapTooltipInternal(name, lines, keys));
    }

    /**
     * An internal method for wrapping this tooltip.
     * @param lines The lines for the final tooltip.
     * @param keys Any contents that make up this text object. Obtained through {@link Component#getSiblings()}.
     */
    @SuppressWarnings("all")
    private static void wrapTooltipInternal(Component name, List<Component> lines, List<Component> keys) {

        Font textRenderer = Minecraft.getInstance().font;
        if (textRenderer != null && ModClient.CONFIG.style.length.value() != 0) {
            int maxLength = Math.max(ModClient.CONFIG.style.length.value(), textRenderer.width(name));
            for (Component originalText : keys) {
                // Get the text without siblings, as they're individually handled after the initial content.
                Component translated = originalText.plainCopy().setStyle(originalText.getStyle());
                if (translated.getContents() instanceof TranslatableContents translatable)
                    translated = Component.literal(I18n.get(translatable.getKey())).setStyle(translated.getStyle());

                if (shouldIndent && translated.getString().isBlank() && !translated.getString().isEmpty()) {
                    indentationText = originalText.plainCopy();
                    // Before moving onto the next bit of text, handle any siblings of the original text.
                    wrapTooltipInternal(name, lines, originalText.getSiblings());
                    shouldIndent = false;
                    continue;
                }
                shouldIndent = false;
                //Any tooltip longer than XX pixels should be shortened.
                while (lineTextWidth + textRenderer.width(translated) >= maxLength && translated.getString().contains(" ")) {
                    // Reset the line width.
                    lineTextWidth = 0;
                    // Remove the line substring from the start of the remaining string. Repeat.
                    translated = createNewLine(lines, translated, textRenderer, maxLength);
                }
                //Add the remainder of this tooltip text.
                if (!translated.getString().isEmpty()) {
                    //Any additional tooltip less than XX pixels should be merged and shortened.
                    if (!lines.isEmpty() && lines.size() > 1 && textRenderer.width(lines.get(lines.size() - 1)) + textRenderer.width(translated) < maxLength && translated.getString().contains(" ")) {
                        // Remove the previous text...
                        Component oldText = lines.removeLast();
                        // And merge it into the new one.
                        Component newText = oldText.copy().append(translated);
                        // Create a new line with the merged text object.
                        createNewLine(lines, newText, textRenderer, maxLength);
                        // Set the text line width
                        lineTextWidth = 0;
                    } else {
                        // Set the text line width
                        lineTextWidth = textRenderer.width(translated);
                        createNewLine(lines, translated, textRenderer, maxLength);
                    }
                }

                // Before moving onto the next bit of text, handle any siblings of the original text.
                wrapTooltipInternal(name, lines, originalText.getSiblings());
            }
        }
    }

    private static Component createNewLine(List<Component> lines, Component text, Font textRenderer, int maxLength) {
        int lineLength = text.getString().length();
        // Find where to end this line, starting from the remaining string.
        if (text.getString().contains("\n")) {
            lineLength = text.getString().indexOf("\n");
        } else {
            while (text.getString().substring(0, lineLength).contains(" ") && textRenderer.width(Component.literal(text.getString().substring(0, lineLength))) >= maxLength) {
                lineLength = getIndex(text.getString(), lineLength);
            }
        }
        Component newLine = subText(text, 0, lineLength);
        if (indentationText != null) {
            newLine = indentationText.copy().append(newLine);
        }
        // Add the line.
        lines.add(newLine);
        // Return a new literal that removes the operated characters.
        return subText(text, lineLength + 1);
    }

    private static Component subText(Component text, int beginIndex) {
        return subText(text, beginIndex, text.getString().length());
    }

    private static Component subText(Component text, int beginIndex, int endIndex) {
        return subText(text, beginIndex, endIndex, 0);
    }

    private static Component subText(Component text, int beginIndex, int endIndex, int currentIndex) {
        MutableComponent mutable = text.plainCopy();

        if (beginIndex > mutable.getString().length()) {
            beginIndex -= mutable.getString().length();
            currentIndex += mutable.getString().length();
        } else {
            // Substring the beginning index.
            String string = mutable.getString();
            string = string.substring(beginIndex, Math.min(endIndex - currentIndex, string.length()));
            mutable = Component.literal(string).setStyle(text.getStyle());
            currentIndex += string.length();
        }

        if (currentIndex >= endIndex)
            return mutable;

        for (Component sibling : text.getSiblings()) {
            if (currentIndex >= endIndex)
                break;
            Component subTextSibling = subText(sibling, beginIndex, endIndex, currentIndex);
            currentIndex += subTextSibling.getString().length();
            mutable.append(subTextSibling);
        }

        return mutable;

    }

    /**
     * Automatically generate translation keys for config options.
     */
    public static Component fieldName(TrackedValue<?> field) {
        return Component.translatable("config.%s.%s".formatted(MOD_ID, toSnakeCase(field.key().toString())));
    }

    public static String toSnakeCase(String field) {
        return field.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
    
    /**
     * Automatically generate translation keys for config tooltips. Relies on custom tooltip wrapping.
     */

    public static Component[] fieldTooltip(TrackedValue<?> field, boolean wrap) {
        String tooltipKey = "config.%s.%s.tooltip".formatted(MOD_ID, toSnakeCase(field.key().toString()));
        if (wrap)
            return createTooltip(Component.empty(), tooltipKey).toArray(new Component[0]);
        else return List.of(ModHelpers.translatableWithFallback(tooltipKey, "")).toArray(new Component[0]);
    }

    /**
     * Set a config field.
     */
    public static void fieldSetter(boolean instance, TrackedValue<Boolean> field) {
        field.setValue(instance);
    }
    public static void fieldSetter(Integer instance, TrackedValue<Integer> field) {
        field.setValue(instance);
    }
    public static void fieldSetter(String instance, TrackedValue<String> field) {
        field.setValue(instance);
    }
    public static void fieldSetter(Color instance, TrackedValue<Integer> field) {
        field.setValue(instance.getRGB());
    }

    private static <T, V> void addMissingTranslations(Registry<T> registry, Map<String, Map<String, String>> namespaces, Function<T, V> valueTransform, Function<V, DescriptionKey> descGetter, Function<V, List<Component>> potentialKeys) {
        for (ResourceKey<T> key : registry.registryKeySet()) {
            V value = valueTransform.apply
            //? if >=1.21.2 {
            (registry.getValue(key));
            //?} else {
            /*(registry.get(key));
            *///?}
            DescriptionKey description = descGetter.apply(value);
            List<String> keys = new ArrayList<>(potentialKeys.apply(value).stream().map(Component::getString).toList());
            //? if >1.21.10 || fabric {
            var id = key.identifier();
            //?} else {
            /*var id = key.location();
            *///?}
            if (description.isEmpty()) {
                LOGGER.warn("[Item Descriptions] Couldn't get lore key for {}: {}!", registry.getAny().get(), id);
            } else if (keys.stream().noneMatch(I18n::exists)) {
                keys.remove(description.asLoreTranslation());
                if (value instanceof ItemStack stack) keys.remove(ItemDescriptions.getModdedNameMatch(stack).asLoreTranslation()); // Ugly
                namespaces.computeIfAbsent(id.getNamespace(), k -> new TreeMap<>()).compute(value instanceof Block || value instanceof Item ? description.asLoreTranslation() : description.asDescriptionTranslation(), (k, v) -> Objects.requireNonNullElse(v, " ??? ") + String.join(", ", keys));
            }
        }
    }
    
    public interface RegistryGetter {
        <E> Optional<? extends Registry<E>> get(ResourceKey<? extends Registry<? extends E>> key);
    }

    @SuppressWarnings("unchecked")
    public static void generateMissingTranslations(RegistryGetter registryGetter) {
        ModClient.LOGGER.info("[Item Descriptions] Creating missing translations files");
        File folder = Platform.INSTANCE.getMissingTranslationsPath();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Map<String, String>> namespaces = new HashMap<>();
        addMissingTranslations((Registry<EntityType<?>>) (Object) registryGetter.get(ResourceKey.createRegistryKey(ModHelpers.of("minecraft","entity_type"))).orElse(null), namespaces, Function.identity(), EntityDescriptions::getDescriptionKey, TagHelpers::findAllPotentialKeys);
        addMissingTranslations((Registry<Item>) (Object) registryGetter.get(ResourceKey.createRegistryKey(ModHelpers.of("minecraft", "item"))).orElse(null), namespaces, Item::getDefaultInstance, ItemDescriptions::getDescriptionKey, TagHelpers::findAllPotentialKeys);
        addMissingTranslations((Registry<Enchantment>) (Object) registryGetter.get(ResourceKey.createRegistryKey(ModHelpers.of("minecraft","enchantment"))).orElse(null), namespaces, Function.identity(), EnchantmentDescriptions::getDescriptionKey, TagHelpers::findAllPotentialKeys);
        addMissingTranslations((Registry<Block>) (Object) registryGetter.get(ResourceKey.createRegistryKey(ModHelpers.of("minecraft","block"))).orElse(null), namespaces, Block::defaultBlockState, BlockDescriptions::getDescriptionKey, TagHelpers::findAllPotentialKeys); // Blocks will overwrite items
        addMissingTranslations((Registry<MobEffect>) (Object) registryGetter.get(ResourceKey.createRegistryKey(ModHelpers.of("minecraft","mob_effect"))).orElse(null), namespaces, Function.identity(), EffectDescriptions::getDescriptionKey, TagHelpers::findAllPotentialKeys);
        Map<String, String> combined = new TreeMap<>();
        namespaces.values().forEach(combined::putAll);
        namespaces.put(ModClient.MOD_ID, combined);
        for (Map.Entry<String, Map<String, String>> entry : namespaces.entrySet()) {
            String namespace = entry.getKey();
            Map<String, String> map = entry.getValue();
            Path namespacePath = folder.toPath().resolve("assets").resolve(namespace).resolve("lang");
            namespacePath.toFile().mkdirs();
            try (FileWriter writer = new FileWriter(namespacePath.resolve("en_us.json").toFile())) {
                gson.toJson(map, writer);
            } catch (IOException e) {
                ModClient.LOGGER.error("[Item Descriptions] Failed to write missing descriptions file", e);
            }
        }
    }
}
