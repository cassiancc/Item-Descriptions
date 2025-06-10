package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.compat.EmiCompat;
import cc.cassian.item_descriptions.client.helpers.compat.FastItemFramesHelpers;
import cc.cassian.item_descriptions.client.helpers.compat.GlowcaseHelpers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;

import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.effect.StatusEffect;
//? if >1.21 {
import net.minecraft.component.ComponentType;
import cc.cassian.item_descriptions.client.helpers.compat.PolymerHelpers;
import net.minecraft.component.DataComponentTypes;
//?} else if >1.20.5 {
/*import net.minecraft.component.DataComponentType;
import net.minecraft.component.DataComponentTypes;
*///?} else {
/*import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
*///?}
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
//? if >1.21 {
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
//?} else if >1.20 {
/*import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.Registries;
*///?} else {
/*import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.Registry;
*///?}
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static cc.cassian.item_descriptions.client.ModClient.LOGGER;
import static cc.cassian.item_descriptions.client.ModClient.MOD_ID;
import static cc.cassian.item_descriptions.client.helpers.TagHelpers.*;
import static net.minecraft.client.resource.language.I18n.translate;

public class ModHelpers {
    public static final Identifier FABRIC_EVENT_PHASE = of("description_tooltip");

    public static Identifier of(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static Identifier of(String namespace, String path) {
        return Identifier.of(namespace, path);
    }

    /**
     * Check if Cloth Config is installed and its configuration can be used.
     */
    @ExpectPlatform
    public static boolean clothConfigInstalled() {
        throw new AssertionError();
    }

    /**
     * Check if ToolTipFix is installed and its wrapper should be used.
     */
    public static boolean useInternalWrapper() {
        return true;
    }

    /**
     * Check if another Enchantment Descriptions is installed and our descriptions should be disabled.
     */
    public static boolean useInternalEnchantmentDescriptions() {
        if (ModClient.CONFIG.developer.forceEnableEnchantmentDescriptions)
            return true;
        else return !(isLoaded("idwtialsimmoedm") || isLoaded("enchdesc"));
    }

    /**
     * Check if another Enchantment Descriptions is installed and our descriptions should be disabled.
     */
    public static boolean useInternalEffectDescriptions() {
        if (ModClient.CONFIG.developer.forceEnableEffectDescriptions)
            return true;
        else return !(isLoaded("potiondescriptions") || isLoaded("effectdescriptions"));
    }

    /**
     * Check if a mod is loaded
     */
    @ExpectPlatform
    public static boolean isLoaded(String mod) {
        throw new AssertionError();
    }

    /**
     * Used in Config to change the tooltip's formatting.
     */
    public static Style getStyle(String colour) {
        return Style.EMPTY.withColor(getColour(colour)).withItalic(ModClient.CONFIG.style.italics).withBold(ModClient.CONFIG.style.bold);
    }

    /**
     * Used in Config to change the tooltip's formatting.
     */
    public static Text getHintText() {
        var sb = new StringBuilder();
        var config = ModClient.CONFIG;
        var shift = config.keybinds.displayWhenShiftIsHeld;
        var ctrl = config.keybinds.displayWhenControlIsHeld;
        var alt = config.keybinds.displayWhenAltIsHeld;
        if (config.hint.showKeybind) {
            if (ctrl) {
                var ctrlText = I18n.translate("key.keyboard.ctrl");
                if (ModClient.CONFIG.hint.uppercase) ctrlText = ctrlText.toUpperCase(Locale.ROOT);
                sb.append(ctrlText);
                if (shift || alt) sb.append("/");
            }
            if (alt) {
                var altText = I18n.translate("key.keyboard.alt");
                if (ModClient.CONFIG.hint.uppercase) altText = altText.toUpperCase(Locale.ROOT);
                sb.append(altText);
                if (shift) sb.append("/");
            }
            if (shift) {
                var shiftText = I18n.translate("key.keyboard.shift");
                if (ModClient.CONFIG.hint.uppercase) shiftText = shiftText.toUpperCase(Locale.ROOT);
                sb.append(shiftText);
            }
            sb.append(": ");
        }
        if (config.keybinds.invert)
            sb.append(I18n.translate("hint.item-descriptions.hint_inverted"));
        else
            sb.append(I18n.translate("hint.item-descriptions.hint"));
        return Text.of(sb.toString());
    }

    /**
     * Used to check what colour a tooltip should be.
     */
    public static TextColor getColour(String colour) {
        int length = colour.length();
        if (length == 1) {
            return TextColor.fromFormatting(Formatting.byCode(colour.charAt(0)));
        }
        else {
            try {
                return TextColor.fromRgb(Integer.parseInt(colour));
            } catch (NumberFormatException ignored) {}
            String replacedColour = colour.toLowerCase().replace(" ", "_");
            return switch (replacedColour) {
                case "black", "dark_blue", "dark_green", "dark_red", "dark_purple",
                     "blue", "green", "aqua", "red", "yellow", "white" ->
                        TextColor.fromFormatting(Formatting.byName(colour));
                case "pink", "light_purple" ->
                        TextColor.fromFormatting(Formatting.byName("light_purple"));
                case "dark_gray", "dark_grey" ->
                        TextColor.fromFormatting(Formatting.byName("dark_gray"));
                case "cyan", "dark_aqua" ->
                        TextColor.fromFormatting(Formatting.byName("dark_aqua"));
                case "orange", "gold", "dark_yellow" ->
                        TextColor.fromFormatting(Formatting.byName("gold"));
                default -> TextColor.fromFormatting(Formatting.byName("gray"));
            };
        }
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
        if (ModClient.CONFIG.keybinds.displayWhenControlIsHeld && Screen.hasControlDown()) return checkKey(Screen.hasControlDown());
        else if (ModClient.CONFIG.keybinds.displayWhenShiftIsHeld && Screen.hasShiftDown()) return checkKey(Screen.hasShiftDown());
        else if (ModClient.CONFIG.keybinds.displayWhenAltIsHeld && Screen.hasAltDown()) return checkKey(Screen.hasAltDown());
        else return false;
    }

    /**
     * Check if a keybind is pressed. Contains the handling for if the key is inverted.
     */
    @SuppressWarnings({"DuplicateCondition", "ConstantValue"})
    public static boolean checkKey(boolean key) {
        boolean invert = ModClient.CONFIG.keybinds.invert;
        //If key is pressed, display the tooltip unless inverted.
        if (key) return !invert;
        //If key is not pressed, don't display the tooltip unless inverted.
        else if (!key) return invert;
        else return false;
    }

    /**
     * Create an item's lore key based off data from its Item Stack.
     */
    public static DescriptionKey findItemLoreKey(ItemStack stack) {
        //Ensure items with Custom Model Data get a custom key instead of a vanilla one.
        //? if >1.21 {
        if (PolymerHelpers.getServerIdentifier(stack) != null) {
            return new DescriptionKey(PolymerHelpers.getServerIdentifier(stack));
        }
        //?}
        //? if >1.20.5 {
            if (hasComponent(stack, DataComponentTypes.CUSTOM_MODEL_DATA)) {
                var data = Objects.requireNonNull(stack.getComponents().get(DataComponentTypes.CUSTOM_MODEL_DATA));
                //? if <1.21.4 {
                 /*var dataValue = data.value();
                *///?} else {
                var dataValue = data.getString(0);
                //?}
                DescriptionKey modelKey = getLoreKey(stack);
                modelKey.setSuffix(".custommodeldata." + dataValue);
                if (modelKey.hasTranslation()) {
                    return modelKey;
                }
            }
            else if (stack.isOf(Items.PAINTING) && hasComponent(stack, DataComponentTypes.ENTITY_DATA)) {
                var data = Objects.requireNonNull(stack.getComponents().get(DataComponentTypes.ENTITY_DATA));
                //? if >=1.21.5 {
                var variant = toTranslationKey(data.copyNbt().getString("variant").orElse(""));
                 //?} else {
                /*var variant = toTranslationKey(data.copyNbt().getString("variant"));
                *///?}
                var paintingKey = new DescriptionKey("lore", "minecraft", "painting", variant);
                if (paintingKey.hasTranslation() || ModClient.CONFIG.developer.showAllPotentialKeys) return paintingKey;
            }
            //Ensure player heads with Profile components get a custom key instead of a vanilla one.
            else if (hasComponent(stack, DataComponentTypes.PROFILE)) {
                DescriptionKey profileKey = getProfile(stack);
                if (profileKey.hasTranslation()) {
                    return profileKey;
                }
            }
        //?} else {
        /*NbtCompound s = stack.getNbt();
            if (s != null) {
                if (s.contains("CUSTOM_MODEL_DATA", NbtElement.NUMBER_TYPE)) {
                    var key = getLoreKey(stack);
                    key.setSuffix("custommodeldata." + Objects.requireNonNull(s.get("CUSTOM_MODEL_DATA")));
                }
                else if (s.contains("SkullOwner", NbtElement.STRING_TYPE)) {
                    DescriptionKey profileKey = getProfile(stack);
                    if (profileKey.hasTranslation()) {
                        return profileKey;
                    }
                }
            }
        *///?}
        DescriptionKey name = getModdedNameMatch(stack);
        if (name.hasTranslation()) {
            return name;
        }
        //Find the tooltip translation key for the provided item stack.
        return checkLoreKey(getLoreKey(stack));
    }

    public static DescriptionKey getModdedNameMatch(ItemStack stack) {
        var key = getDescriptionKey(stack);
        key.setSuffix(toTranslationKey(stack.getItem().getName(stack).getString()));
        return key;
    }

    public static String toTranslationKey(String string) {
        return string.toLowerCase().replaceAll("\"", "").replaceAll(" ", "_").replaceAll("[/:]", ".");
    }

    public static boolean hasTranslation(String key) {
        if (ModClient.CONFIG.developer.showUntranslated) return true;
        return I18n.hasTranslation(key);
    }

    /**
     * Create a block's lore key based off data from WAILA-based Block Accessors like Jade/WTHIT/HYWLA.
     */
    public static DescriptionKey createBlockDescription(Block block, World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        //Convert block translation key to lore translation key.
        DescriptionKey loreKey = findBlockLoreKey(block);
        //? if >1.21 {
        if (isLoaded("polymer-bundled"))
            if (PolymerHelpers.isPolymerBlock(pos)) {
                loreKey = new DescriptionKey(PolymerHelpers.findPolymerBlockIdentifier(pos));
            }
        //?}
        //Custom handling of Player Heads so custom profiles give custom descriptions.
        if (blockEntity instanceof SkullBlockEntity) {
            DescriptionKey profileKey = getProfile(blockEntity, loreKey);
            //Only show custom descriptions if a translation is present.
            if (profileKey.hasTranslation()) {
                return profileKey;
            }
        }
        if (isLoaded("fastitemframes")) {
            if (FastItemFramesHelpers.isFastItemFrame(blockEntity)) {
                var contents = FastItemFramesHelpers.getFastItemFrameContents(blockEntity);
                if (contents != null)
                    return findItemLoreKey(contents);
            }
        }
        if (isLoaded("glowcase")) {
            if (GlowcaseHelpers.isItemDisplay(blockEntity)) {
                var contents = GlowcaseHelpers.getItemDisplayContents(blockEntity);
                if (contents != null)
                    return findItemLoreKey(contents);
            }
        }
        //Check if translation exists. If not, see if an item exists for it - e.g. seeds.
        if (!loreKey.hasTranslation()) {
            //? if <1.21.2 {
            /*return findItemLoreKey(block.getPickStack(world, pos, state));
            *///?} else
            return findItemLoreKey(block.getDefaultState().getPickStack(world, pos, true));

        }
        return loreKey;
    }

    /**
     * Create an entity's lore key based off its entity data.
     */
    public static List<Text> createEntityDescription(Entity entity) {
        //Create and add tooltip.
        Text name = entity.getName();
        if (entity instanceof ItemFrameEntity itemFrameEntity && !itemFrameEntity.getHeldItemStack().isEmpty()) {
            return createTooltip(name, findItemLoreKey(itemFrameEntity.getHeldItemStack()));
        }
        //? if >1.21 {
        else if (entity instanceof PaintingEntity painting && painting.getVariant().hasKeyAndValue()) {
            var loreKey = new DescriptionKey("lore", "minecraft", "painting", toTranslationKey(painting.getVariant().getIdAsString()));
            if (loreKey.hasTranslation())
                return createTooltip(name, loreKey);
        }
        //?}
        return createTooltip(name, findEntityLoreKey(entity));
    }

    public static boolean createEnchantmentDescription(ItemStack stack, List<Text> lines) {
        boolean descriptionFound = false;
        if (ModClient.CONFIG.enchantmentDescriptions.enable && showEnchantmentDescriptions()) {
            if (ModClient.CONFIG.enchantmentDescriptions.onlyShowOnBooks && !stack.getItem().equals(Items.ENCHANTED_BOOK))
                return false;
            //? if >1.21 {
            final var enchantments = new HashSet<>(EnchantmentHelper.getEnchantments(stack).getEnchantments());
             //?} else if >1.20.5 {
            /*final var enchantments = new HashSet<>(EnchantmentHelper.getEnchantments(stack).getEnchantments());
            *///?} else {
            /*final var enchantments = EnchantmentHelper.get(stack).keySet();
            *///?}
            if (enchantments.isEmpty()) return false;
            for (var enchantmentEntry : enchantments) {
                //? if >1.21 {
                 var enchantment = enchantmentEntry.value();
                //?} else {
                /*var enchantment = enchantmentEntry;
                *///?}
                for (int i = 0; i < lines.size(); i++) {
                    //? if >1.21 {
                    if (!lines.get(i).getContent().equals(enchantment.description().getContent())) continue;
                     //?} else if >1.20.5 {
                    /*if (!(lines.get(i).getContent() instanceof TranslatableTextContent text)) continue;
                    if (!text.getKey().equals(enchantment.value().getTranslationKey())) continue;
                    *///?} else {
                    /*if (!(lines.get(i).getContent() instanceof TranslatableTextContent text)) continue;
                    if (!text.getKey().equals(enchantment.getTranslationKey())) continue;
                    *///?}
                    TextContent description = lines.get(i).getContent();
                    if (description instanceof TranslatableTextContent translatableTextContent) {
                        var descriptionKey = new DescriptionKey(translatableTextContent.getKey());
                        lines.add(i+1, descriptionKey.toText().setStyle(ModStyle.ENCHANTMENT_DESCRIPTIONS));
                    }
                }
            }
        }
        return descriptionFound;
    }

    private static boolean checkTranslatableText(Text text, Predicate<TranslatableTextContent> predicate) {
        TextContent contents = null;
        if (text instanceof MutableText mutable) {
            if (mutable.getSiblings().stream().anyMatch(c -> checkTranslatableText(c, predicate))) {
                return true;
            }
            contents = mutable.getContent();
        }
        if (contents == null)
            return false;
        return contents instanceof TranslatableTextContent translatable && predicate.test(translatable);
    }

    public static void fixEnchantmentDescription(ItemStack stack, List<Text> lines) {
        if (ModClient.CONFIG.enchantmentDescriptions.enable && useInternalEnchantmentDescriptions()) {
            if (ModClient.CONFIG.enchantmentDescriptions.onlyShowOnBooks && !stack.getItem().equals(Items.ENCHANTED_BOOK))
                return;

            //? if >1.20.5 {
            final var enchantments = new HashSet<>(EnchantmentHelper.getEnchantments(stack).getEnchantments());
            //?} else {
            /*final var enchantments = EnchantmentHelper.get(stack).keySet();
             *///?}

            if (enchantments.isEmpty())
                return;

            for (int i = 0; i < lines.size(); ++i) {
                if (isEnchantmentDescription(lines.get(i), (Set)enchantments)) {
                    // Create the tooltip.
                    var newLines = createTooltip(stack.getName(), lines.get(i), useInternalWrapper());
                    if (newLines.isEmpty())
                        continue;
                    // To avoid warnings, we don't remove from lines and instead modify the initial line to the first line.
                    lines.set(i, newLines.get(0));
                    // Add the remaining lines.
                    if (newLines.size() > 1) {
                        lines.addAll(i+1, newLines.subList(1, newLines.size()));
                    }
                }
            }
        }
    }

    private static boolean isEnchantmentDescription(Text text, Set<Object> enchantments) {
        // If the Minecraft world is null, we cannot check if this is an enchantment key.
        if (MinecraftClient.getInstance().world == null)
            return false;
        return checkTranslatableText(text, content -> {
            // Split the lang key of this translatable content.
            String[] split = content.getKey().split("\\.");
            // The split key array should always be at least 3 in length, so return false if that's not the case.
            if (split.length < 3)
                return false;
            String namespace = split[1];
            String path = split[2];
            // Check whether the translation exists, and if the key is either an enchantment.*.*.description/desc or lore.*.* key.
            if (hasTranslation(content.getKey()) && (split.length == 4 && split[0].equals("enchantment") && (split[3].equals("description") || split[3].equals("desc")) || split.length == 3 && split[0].equals("lore"))) {
                // Whether the namespace and path maps to an enchantment on this item. If so, return true.
                //? if >1.21 {
                return enchantments.stream().anyMatch(entry -> ((RegistryEntry<Enchantment>)(Object)entry).matchesId(Identifier.of(namespace, path)));
                //?} else if >1.20 {
                /*return enchantments.stream().anyMatch(entry -> Registries.ENCHANTMENT.getId((Enchantment)(Object)entry).equals(new Identifier(namespace, path)));
                 *///?} else {
                /*return enchantments.stream().anyMatch(entry -> Registry.ENCHANTMENT.getId((Enchantment)(Object)entry).equals(new Identifier(namespace, path)));
                 *///?}
            }
            return false;
        });
    }

    public static List<Text> createEffectDescription(List<Text> text) {
        ArrayList<Text> lines = new ArrayList<>(text);
        if (ModClient.CONFIG.effectDescriptions.enable) {
            for (Text text1 : text) {
                if (text1.getContent() instanceof TranslatableTextContent translatableTextContent) {
                    if (!translatableTextContent.getKey().startsWith("effect.duration")) {
                        var key = new DescriptionKey(translatableTextContent.getKey());
                        List<Text> tooltip = ModHelpers.createTooltip(text1, key.toString(), true, ModStyle.EFFECT_DESCRIPTIONS);
                        if (showEffectDescriptions()) {
                            lines.addAll(tooltip);
                        }
                        else if (ModClient.CONFIG.hint.enabled && (key.hasTranslation())) {
                            addHint(lines);
                        }
                    }
                }
            }
        }
        return lines;
    }

    public static void createEffectDescription(Text name, Consumer<Text> textConsumer, StatusEffectInstance statusEffectInstance) {
        if (ModClient.CONFIG.effectDescriptions.enable && showEffectDescriptions()) {
            var key = new DescriptionKey(statusEffectInstance.getTranslationKey());
            List<Text> tooltip = ModHelpers.createTooltip(name, key.toString(), true, ModStyle.EFFECT_DESCRIPTIONS);
            if (showEffectDescriptions()) {
                for (Text line : tooltip) {
                 textConsumer.accept(line);
                }
            }
        }
    }

    public static void createEffectDescription(Text name, List<Text> textConsumer, StatusEffectInstance statusEffectInstance) {
        if (ModClient.CONFIG.effectDescriptions.enable && showEffectDescriptions()) {
            var key = new DescriptionKey(statusEffectInstance.getTranslationKey());
            List<Text> tooltip = ModHelpers.createTooltip(name, key.toString(), true, ModStyle.EFFECT_DESCRIPTIONS);
            if (showEffectDescriptions()) {
                textConsumer.addAll(tooltip);
            }
        }
    }

    public static void addHint(List<Text> lines) {
        lines.add(1, getHintText().getWithStyle(ModStyle.HINT).get(0));
    }

    public static boolean createItemDescription(ItemStack stack, List<Text> lines) {
        if (ModClient.CONFIG.itemDescriptions) {
            //Create and add tooltip.
            List<Text> tooltip;
            DescriptionKey descriptionKey = findItemLoreKey(stack);
            if (ModClient.CONFIG.developer.showAllPotentialKeys) {
                tooltip = TagHelpers.findAllPotentialKeys(stack);
            } else if (descriptionKey.hasTranslation()) {
                tooltip = List.of(descriptionKey.toText());
            }
            else return false;
            tooltip = tooltip.stream().map(text -> (Text)text.copy().setStyle(ModStyle.ITEM_DESCRIPTIONS)).toList();
            if (showItemDescriptions())
                lines.addAll(tooltip);
            else return descriptionKey.hasTranslation();
        }
        return false;
    }

    public static void fixItemDescription(ItemStack stack, List<Text> lines) {
        if (ModClient.CONFIG.itemDescriptions && showItemDescriptions()) {
            //Find and wrap tooltip. Will be disabled if TooltipFix is installed.
            List<Text> tooltip;
            DescriptionKey descriptionKey = findItemLoreKey(stack);
            if (ModClient.CONFIG.developer.showAllPotentialKeys) {
                tooltip = TagHelpers.findAllPotentialKeys(stack);
            } else {
                tooltip = List.of(descriptionKey.toText());
            }
            for (int i = 0; i < lines.size(); ++i) {
                // Required for lambda comparison.
                int finalI = i;
                // Check if any of the tooltips' content matches the current line's content.
                if (tooltip.stream().anyMatch(text -> text.getContent().equals(lines.get(finalI).getContent()))) {
                    var newLines = createTooltip(stack.getName(), lines.get(i), useInternalWrapper());
                    lines.set(i, newLines.get(0));
                    if (newLines.size() > 1) {
                        lines.addAll(i+1, newLines.subList(1, newLines.size()));
                    }
                }
            }

        }
    }

    /**
     * Check if an Item Stack has a particular component.
     */
    //? if =1.20.6 {
    /*public static boolean hasComponent(ItemStack stack, DataComponentType<?> type) {
        return stack.getComponents().contains(type);
    }
    *///?}

    /**
     * Check if an Item Stack has a particular component.
     */
    //? if >1.21 {
    public static boolean hasComponent(ItemStack stack, ComponentType<?> type) {
        return stack.getComponents().contains(type);
    }
    //?}

    /**
     * Find a profile name in a Player Head Item Stack.
     */
    public static DescriptionKey getProfile(ItemStack stack) {
        //? if >1.20.5 {
        var optionalProfileName = Objects.requireNonNull(Objects.requireNonNull(stack.getComponents().get(DataComponentTypes.PROFILE)).name()).orElse("");
        //?} else {
        /*var optionalProfileName = Objects.requireNonNull(stack.getNbt().get("CUSTOM_MODEL_DATA")).toString();
         *///?}
        if (!optionalProfileName.isEmpty()) {
            DescriptionKey profileKey = getLoreKey(stack);
            profileKey.setSuffix("profile." + optionalProfileName);
            if (profileKey.hasTranslation()) {
                return profileKey;
            }
        }
        return DescriptionKey.empty();
    }

    /**
     * Find a profile name in a Player Head block.
     */
    public static DescriptionKey getProfile(BlockEntity blockEntity, DescriptionKey loreKey) {
        String optionalProfileName;
        try {
            //? if >1.20.5 {
            optionalProfileName = Objects.requireNonNull(((SkullBlockEntity) blockEntity).getOwner()).name().orElse("");
            //?} else
            /*optionalProfileName = Objects.requireNonNull(((SkullBlockEntity) blockEntity).getOwner()).getName();*/
        }
        catch (NullPointerException nullPointerException) {
            return loreKey;
        }
        loreKey.setSuffix("profile." + optionalProfileName);
        return loreKey;
    }

    /**
     * Check if block descriptions should be shown based off configuration.
     */
    public static boolean showBlockDescriptions() {
        return ModClient.CONFIG.blockDescriptions.enable && (tooltipKeyPressed() || ModClient.CONFIG.blockDescriptions.showAlways);
    }

    /**
     * Check if item descriptions should be shown based off configuration.
     */
    public static boolean showItemDescriptions() {
        return ModClient.CONFIG.itemDescriptions && (tooltipKeyPressed() || ModClient.CONFIG.displayAlways);
    }
    /**
     * Check if enchantment descriptions should be shown based off configuration.
     */
    public static boolean showEnchantmentDescriptions() {
        return ModClient.CONFIG.enchantmentDescriptions.enable && useInternalEnchantmentDescriptions() && (tooltipKeyPressed() || ModClient.CONFIG.enchantmentDescriptions.displayAlways);
    }
    /**
     * Check if entity descriptions should be shown based off configuration.
     */
    public static boolean showEntityDescriptions() {
        return ModClient.CONFIG.entityDescriptions.enable && (tooltipKeyPressed() || ModClient.CONFIG.entityDescriptions.showAlways);
    }
    /**
     * Check if effect descriptions should be shown based off configuration.
     */
    public static boolean showEffectDescriptions() {
        return ModClient.CONFIG.effectDescriptions.enable && useInternalEffectDescriptions() && (tooltipKeyPressed() || ModClient.CONFIG.effectDescriptions.displayAlways);
    }

    public static void createDescriptionsFromItemStack(ItemStack stack, List<Text> lines) {
        if (ModClient.CONFIG.developer.hideOtherTooltips || ModLists.hidden_items.contains(stack.getItem())) {
            var first = lines.get(0);
            lines.clear();
            lines.add(first);
        }
        boolean enchant = createEnchantmentDescription(stack, lines);
        boolean effect = checkForEffectDescription(stack);
        if (ModClient.CONFIG.effectDescriptions.onlyShowEffectDescriptions && effect) return;
        boolean item = createItemDescription(stack, lines);
        if (ModClient.CONFIG.hint.enabled && (item || enchant)) {
            addHint(lines);
        }
        if ((tooltipKeyPressed() || ModClient.CONFIG.displayAlways) && ModClient.CONFIG.showModName) {
            //? if >1.20 {
            var registry = Registries.ITEM;
            //?} else {
            /*var registry = Registry.ITEM;
             *///?}
            Identifier id = registry.getId(stack.getItem());
            String namespace = id.getNamespace();
            Text text;
            String key = "modmenu.nameTranslation."+namespace;
            if (I18n.hasTranslation(key)) {
                text = Text.translatable(key);
            } else {
                if (ModHelpers.isLoaded("emi")) {
                    text = Text.literal(EmiCompat.getModName(namespace));
                } else {
                    text = Text.literal(WordUtils.capitalize(namespace));
                }
            }
            lines.add(text.getWithStyle(ModStyle.MOD_NAME).get(0));
        }
    }

    public static MutableText translatableWithFallback(String translatable, String fallback) {
        //? if >1.20 {
        return Text.translatableWithFallback(translatable, fallback);
         //?} else {
        /*if (ModHelpers.hasTranslation(translatable)) {
            return Text.translatable(translatable);
        }
        return Text.literal(fallback);
        *///?}
    }

    private static boolean checkForEffectDescription(ItemStack stack) {
        //? if >1.20.5 {
        if (stack.getComponents().contains(DataComponentTypes.POTION_CONTENTS)) {
            var contents = stack.getComponents().get(DataComponentTypes.POTION_CONTENTS);
            if (contents == null) return false;
            for (StatusEffectInstance effect : contents.getEffects()) {
                var key = new DescriptionKey(effect.getTranslationKey());
                if (key.hasTranslation()) {
                    return true;
                }
            }
        }
        //?} else {
        /*if (stack.hasNbt()) {
            assert stack.getNbt() != null;
            String potion = stack.getNbt().getString("Potion");
            return new DescriptionKey("effect", new Identifier(potion)).hasTranslation();
        }
        *///?}
        return false;
    }

    public static void fixItemStackDescriptionTooltip(ItemStack stack, List<Text> lines) {
        if (!useInternalWrapper())
            return;
        fixEnchantmentDescription(stack, lines);
        if (useInternalWrapper())
            fixItemDescription(stack, lines);
    }

    /**
     * Find a profile name
     */
    public static String getProfileName(Optional<String> optionalProfileName) {
        String profileName;
        if (optionalProfileName.isPresent()) {
            profileName = optionalProfileName.get();
            return profileName;
        } else {
            return "";
        }
    }

    /**
     * Consistency feature for 1.20.
     */
    public static String getProfileName(String optionalProfileName) {
        return optionalProfileName;
    }

    /**
     * Shorthand to check a block's lore key.
     */
    public static DescriptionKey findBlockLoreKey(Block block) {
        return checkLoreKey(getLoreKey(block));
    }

    /**
     * Shorthand to check an entity's lore key.
     */
    public static DescriptionKey findEntityLoreKey(Entity entity) {
        return checkLoreKey(getLoreKey(entity));
    }

    /**
     * Check if a lore key exists or if a generic tooltip should be used.
     */
    public static DescriptionKey checkLoreKey(DescriptionKey loreKey) {
        //Check if the tooltip translation key exists. If so, use the provided tooltip.
        if (loreKey.hasTranslation()) return loreKey;
        else return DescriptionKey.empty();
    }

    /**
     * Check if a tag exists, or if a generic one should be used.
     */
    private static @NotNull DescriptionKey getLoreKey(Object object) {
        @NotNull DescriptionKey key = getDescriptionKey(object);
        if (key.hasTranslation()) {
            return key;
        } else {
            return getGenericKey(object);
        }
    }

    /**
     * Convert block/item/entity translation keys to lore translation keys.
     */
    public static @NotNull DescriptionKey convertToLoreKey(String translationKey) {
        DescriptionKey loreKey;
        //Find the translation key for blocks.
        if (translationKey.contains("block.")) loreKey = new DescriptionKey(translationKey);
        //Find the translation key for items.
        else if ((translationKey.contains("item."))) loreKey = new DescriptionKey(translationKey);
        //Find the translation key for entities.
        else if ((translationKey.contains("entity."))) {
            //Entity descriptions use a different format as to avoiding colliding with items of the same name.
            loreKey = new DescriptionKey(translationKey);
            //Tropical fish have 20 different variants and their description should be the same.
            if (translationKey.contains("tropical_fish")) {
                loreKey = new DescriptionKey("entity", "minecraft", "tropical_fish");
            }
            //In case an entity tooltip is misconfigured, try checking for an "old style" key.
            else if (loreKey.hasTranslation()) return loreKey;
        }
        else return DescriptionKey.empty();
        return loreKey;
    }

    /**
     * Convert block/item/entity translation keys to description keys.
     */
    public static @NotNull DescriptionKey getDescriptionKey(Object object) {
        if (object instanceof ItemStack stack) {
            return convertToLoreKey(stack.getItem().getTranslationKey());
        } else if (object instanceof BlockState blockState) {
            return convertToLoreKey(blockState.getBlock().getTranslationKey());
        } else if (object instanceof Block block) {
            return convertToLoreKey(block.getTranslationKey());
        } else if (object instanceof EntityType<?> entityType) {
            return convertToLoreKey(entityType.getTranslationKey());
        } else if (object instanceof Entity entity) {
            return convertToLoreKey(getEntityTranslationKey(entity));
        } else if (object instanceof StatusEffect effect) {
            return new DescriptionKey(effect.getTranslationKey());
        } else if (object instanceof Enchantment enchantment) {
            return getEnchantmentDescriptionKey(enchantment);
        }
        return DescriptionKey.empty();
    }

    public static DescriptionKey getEnchantmentDescriptionKey(Enchantment enchantment) {
        //? if >1.21 {
        return enchantment.description().getContent() instanceof TranslatableTextContent translatable ? new DescriptionKey(translatable.getKey()) : DescriptionKey.empty();
         //?} else
        /*return new DescriptionKey(enchantment.getTranslationKey());*/
    }

    /**
     * Find an entity's translation key
     */
    public static String getEntityTranslationKey(Entity entity) {
        //Allow for custom player descriptions
        if (entity instanceof PlayerEntity) {
            //? if >1.21 {
            String playerKey = "entity.minecraft.player.%s".formatted(entity.getName().getLiteralString());;
            //?} else
            /*String playerKey = "entity.minecraft.player." + entity.getName().getString();;*/
            //Check if a custom player description exists.
            if (hasTranslation(playerKey)) return playerKey;
            //If not, use the default one.
            else return "entity.minecraft.player";
        } else {
            return entity.getType().getTranslationKey();
        }
    }

    /**
     * Create a custom multi-line tooltip.
     *
     * @param loreKey The translation key that will be translated and wrapped.
     */
    public static List<Text> createTooltip(Text name, DescriptionKey loreKey) {
        return createTooltip(name, loreKey.toString(), true);
    }

    /**
     * Create a custom multi-line tooltip.
     *
     * @param loreKey The translation key that will be translated and wrapped.
     */
    public static List<Text> createTooltip(Text name, String loreKey) {
        return createTooltip(name, loreKey, true);
    }

    /**
     * Create a custom, potentially multi-line tooltip.
     * @param loreKey The translation key that will be translated.
     * @param wrap Whether to use the built-in wrapper.
     */
    public static List<Text> createTooltip(Text name, String loreKey, boolean wrap) {
        return createTooltip(name, loreKey, wrap, ModStyle.ITEM_DESCRIPTIONS);
    }


    public static List<Text> createTooltip(Text name, Text text, boolean wrap) {
        if (!wrap || text.getString().isEmpty())
            return List.of(text);
        //Setup list to store (potentially multi-line) tooltip.
        ArrayList<Text> lines = new ArrayList<>();
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
    public static List<Text> createTooltip(Text name, String loreKey, boolean wrap, Style style) {
        //Setup list to store (potentially multi-line) tooltip.
        ArrayList<Text> lines = new ArrayList<>();
        //Check if the key exists.
        if (!loreKey.isBlank()) {
            //Translate the lore key.
            String translatedKey = translate(loreKey);
            //Check if the translated key exists.
            if (hasTranslation(loreKey)) {
                if (!wrap) {
                    if (!translatedKey.isBlank()) lines.add(Text.translatable(loreKey).setStyle(style));
                }
                else {
                    wrapTooltip(name, lines, List.of(Text.literal(translatedKey).setStyle(style)));
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
    private static Text indentationText = null;

    private static void resetWrapValues() {
        lineTextWidth = 0;
        shouldIndent = true;
        indentationText = null;
    }

    /**
     * An internal method for wrapping this tooltip.
     * @param lines The lines for the final tooltip.
     * @param keys Any contents that make up this text object. Obtained through {@link Text#getSiblings()}.
     */
    private static void wrapTooltip(Text name, List<Text> lines, List<Text> keys) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        if (textRenderer != null && ModClient.CONFIG.style.length != 0) {
            int maxLength = Math.max(ModClient.CONFIG.style.length, textRenderer.getWidth(name));
            for (Text originalText : keys) {
                // Get the text without siblings, as they're individually handled after the initial content.
                Text translated = originalText.copyContentOnly().setStyle(originalText.getStyle());
                if (translated.getContent() instanceof TranslatableTextContent translatable)
                    translated = Text.literal(translate(translatable.getKey())).setStyle(translated.getStyle());

                if (shouldIndent && translated.getString().isBlank() && !translated.getString().isEmpty()) {
                    indentationText = originalText.copyContentOnly();
                    // Before moving onto the next bit of text, handle any siblings of the original text.
                    wrapTooltip(name, lines, originalText.getSiblings());
                    shouldIndent = false;
                    continue;
                }
                shouldIndent = false;
                //Any tooltip longer than XX pixels should be shortened.
                while (lineTextWidth + textRenderer.getWidth(translated) >= maxLength && translated.getString().contains(" ")) {
                    // Reset the line width.
                    lineTextWidth = 0;
                    // Remove the line substring from the start of the remaining string. Repeat.
                    translated = createNewLine(lines, translated, textRenderer, maxLength);
                }
                //Add the remainder of this tooltip text.
                if (!translated.getString().isEmpty()) {
                    //Any additional tooltip less than XX pixels should be merged and shortened.
                    if (!lines.isEmpty() && lines.size() > 1 && textRenderer.getWidth(lines.get(lines.size() - 1)) + textRenderer.getWidth(translated) < maxLength && translated.getString().contains(" ")) {
                        // Remove the previous text...
                        Text oldText = lines.remove(lines.size() - 1);
                        // And merge it into the new one.
                        Text newText = oldText.copy().append(translated);
                        // Create a new line with the merged text object.
                        createNewLine(lines, newText, textRenderer, maxLength);
                        // Set the text line width
                        lineTextWidth = 0;
                    } else {
                        // Set the text line width
                        lineTextWidth = textRenderer.getWidth(translated);
                        createNewLine(lines, translated, textRenderer, maxLength);
                    }
                }

                // Before moving onto the next bit of text, handle any siblings of the original text.
                wrapTooltip(name, lines, originalText.getSiblings());
            }
        }
    }

    private static Text createNewLine(List<Text> lines, Text text, TextRenderer textRenderer, int maxLength) {
        int lineLength = text.getString().length();
        // Find where to end this line, starting from the remaining string.
        while (text.getString().substring(0, lineLength).contains(" ") && textRenderer.getWidth(Text.of(text.getString().substring(0, lineLength))) >= maxLength) {
            lineLength = getIndex(text.getString(), lineLength);
        }
        Text newLine = subText(text, 0, lineLength);
        if (indentationText != null) {
            newLine = indentationText.copy().append(newLine);
        }
        // Add the line.
        lines.add(newLine);
        // Return a new literal that removes the operated characters.
        return subText(text, lineLength + 1);
    }

    private static Text subText(Text text, int beginIndex) {
        return subText(text, beginIndex, text.getString().length());
    }

    private static Text subText(Text text, int beginIndex, int endIndex) {
        return subText(text, beginIndex, endIndex, 0);
    }

    private static Text subText(Text text, int beginIndex, int endIndex, int currentIndex) {
        MutableText mutable = text.copyContentOnly();

        if (beginIndex > mutable.getString().length()) {
            beginIndex -= mutable.getString().length();
            currentIndex += mutable.getString().length();
        } else {
            // Substring the beginning index.
            String string = mutable.getString();
            string = string.substring(beginIndex, Math.min(endIndex - currentIndex, string.length()));
            mutable = Text.literal(string).setStyle(text.getStyle());
            currentIndex += string.length();
        }

        if (currentIndex >= endIndex)
            return mutable;

        for (Text sibling : text.getSiblings()) {
            if (currentIndex >= endIndex)
                break;
            Text subTextSibling = subText(sibling, beginIndex, endIndex, currentIndex);
            currentIndex += subTextSibling.getString().length();
            mutable.append(subTextSibling);
        }

        return mutable;

    }

    /**
     * Automatically generate translation keys for config options.
     */
    public static Text fieldName(Field field, String category) {
        if (category == null) {
            category = "config";
        }
        return Text.translatable("config.%s.%s.%s".formatted(MOD_ID, category, field.getName()));
    }
    
    /**
     * Automatically generate translation keys for config tooltips. Relies on custom tooltip wrapping.
     */
    public static Text[] fieldTooltip(Field field, String category) {
        if (category == null) {
            category = "config";
        }
        String tooltipKey = "config.%s.%s.%s.tooltip".formatted(MOD_ID, category, field.getName());
        return createTooltip(Text.empty(), tooltipKey).toArray(new Text[0]);
    }

    /**
     * Get the current value of a config field.
     */
    @SuppressWarnings("unchecked")
    public static <T> T fieldGet(Object instance, Field field) {
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set a config field.
     */
    public static <T> Consumer<T> fieldSetter(Object instance, Field field) {
        return t -> {
            try {
                field.set(instance, t);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Checks if a mod is loaded while the mod is loading.
     * Required on Forge.
     */
    @ExpectPlatform
    public static boolean isLoadingLoaded(String mod) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static File getMissingTranslationsPath() {
        throw new AssertionError();
    }

    private static <T> void addMissingTranslations(Registry<T> registry, Map<String, Map<String, String>> namespaces, Function<T, ?> valueTransform) {
        for (RegistryKey<T> key : registry.getKeys()) {
            Object value = valueTransform.apply(registry.get(key));
            DescriptionKey description = getDescriptionKey(value);
            List<String> keys = new ArrayList<>(TagHelpers.findAllPotentialKeys(value).stream().map(Text::getString).toList());
            if (description.isEmpty()) {
              LOGGER.warn("[Item Descriptions] Couldn't get lore key for {}: {}!", registry.getKey().getValue(), key.getValue());
            } else if (keys.stream().noneMatch(I18n::hasTranslation)) {
                keys.remove(description.asLoreTranslation());
                if (value instanceof ItemStack stack) keys.remove(getModdedNameMatch(stack).asLoreTranslation()); // Ugly
                namespaces.computeIfAbsent(key.getValue().getNamespace(), k -> new TreeMap<>()).put(description.asDescriptionTranslation(), " ??? %s".formatted(String.join(", ", keys)));
            }
        }
    }
    
    public interface RegistryGetter {
        <E> Optional<? extends Registry<E>> get(RegistryKey<? extends Registry<? extends E>> key);
    }

    @SuppressWarnings("unchecked")
    public static void generateMissingTranslations(RegistryGetter registryGetter) {
        ModClient.LOGGER.info("[Item Descriptions] Creating missing translations files");
        File folder = getMissingTranslationsPath();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Map<String, String>> namespaces = new HashMap<>();
        addMissingTranslations((Registry<EntityType<?>>) (Object) registryGetter.get(RegistryKey.ofRegistry(Identifier.of("minecraft", "entity_type"))).orElse(null), namespaces, Function.identity());
        addMissingTranslations((Registry<Item>) (Object) registryGetter.get(RegistryKey.ofRegistry(Identifier.of("minecraft", "item"))).orElse(null), namespaces, Item::getDefaultStack);
        addMissingTranslations((Registry<Enchantment>) (Object) registryGetter.get(RegistryKey.ofRegistry(Identifier.of("minecraft", "enchantment"))).orElse(null), namespaces, Function.identity());
        addMissingTranslations((Registry<Block>) (Object) registryGetter.get(RegistryKey.ofRegistry(Identifier.of("minecraft", "block"))).orElse(null), namespaces, Block::getDefaultState); // Blocks will overwrite items
        addMissingTranslations((Registry<StatusEffect>) (Object) registryGetter.get(RegistryKey.ofRegistry(Identifier.of("minecraft", "mob_effect"))).orElse(null), namespaces, Function.identity());
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
