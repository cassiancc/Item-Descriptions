package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.Platform;
import cc.cassian.item_descriptions.client.helpers.compat.FastItemFramesHelpers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
//? if >1.21.10 {
/*import net.minecraft.world.entity.decoration.painting.Painting;
*///?} else {
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.decoration.Painting;
//?}
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.component.DataComponentType;
//? if fabric {
import cc.cassian.item_descriptions.client.helpers.compat.GlowcaseHelpers;
import cc.cassian.item_descriptions.client.helpers.compat.PolymerHelpers;
//?}
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.*;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static cc.cassian.item_descriptions.client.ModClient.LOGGER;
import static cc.cassian.item_descriptions.client.ModClient.MOD_ID;

public class ModHelpers {
    public static final ResourceLocation FABRIC_EVENT_PHASE = of("description_tooltip");

    public static ResourceLocation of(String path) {
        return ModHelpers.of(MOD_ID, path);
    }

    public static ResourceLocation of(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    /**
     * Check if ToolTipFix is installed and its wrapper should be used.
     */
    public static boolean useInternalWrapper() {
        return !isLoaded("tooltipfix");
    }

    /**
     * Check if another Enchantment Descriptions is installed and our descriptions should be disabled.
     */
    public static boolean useInternalEnchantmentDescriptions() {
        if (ModClient.CONFIG.developerOptions.forceEnableEnchantmentDescriptions.value())
            return true;
        else return !(isLoaded("idwtialsimmoedm") || isLoaded("enchdesc"));
    }

    /**
     * Check if another Enchantment Descriptions is installed and our descriptions should be disabled.
     */
    public static boolean useInternalEffectDescriptions() {
        if (ModClient.CONFIG.developerOptions.forceEnableEffectDescriptions.value())
            return true;
        else return !(isLoaded("potiondescriptions") || isLoaded("effectdescriptions"));
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
    public static Style getStyle(String colour) {
        return Style.EMPTY.withColor(getColour(colour)).withItalic(ModClient.CONFIG.style.italics.value()).withBold(ModClient.CONFIG.style.bold.getDefaultValue());
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
            sb.append(I18n.get("hint.item-descriptions.hint_inverted"));
        else
            sb.append(I18n.get("hint.item-descriptions.hint"));
        return Component.literal(sb.toString());
    }

    /**
     * Used to check what colour a tooltip should be.
     */
    public static TextColor getColour(String colour) {
        int length = colour.length();
        if (length == 1) {
            return TextColor.fromLegacyFormat(ChatFormatting.getByCode(colour.charAt(0)));
        }
        else {
            try {
                return TextColor.fromRgb(Integer.parseInt(colour));
            } catch (NumberFormatException ignored) {}
            String replacedColour = colour.toLowerCase().replace(" ", "_");
            return switch (replacedColour) {
                case "black", "dark_blue", "dark_green", "dark_red", "dark_purple",
                     "blue", "green", "aqua", "red", "yellow", "white" ->
                        TextColor.fromLegacyFormat(ChatFormatting.getByName(colour));
                case "pink", "light_purple" ->
                        TextColor.fromLegacyFormat(ChatFormatting.getByName("light_purple"));
                case "dark_gray", "dark_grey" ->
                        TextColor.fromLegacyFormat(ChatFormatting.getByName("dark_gray"));
                case "cyan", "dark_aqua" ->
                        TextColor.fromLegacyFormat(ChatFormatting.getByName("dark_aqua"));
                case "orange", "gold", "dark_yellow" ->
                        TextColor.fromLegacyFormat(ChatFormatting.getByName("gold"));
                default -> TextColor.fromLegacyFormat(ChatFormatting.getByName("gray"));
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

    /**
     * Create an item's lore key based off data from its Item Stack.
     */
    public static DescriptionKey findLoreKey(ItemStack stack) {
        // Disable Item Descriptions on Enchanted Books
        if (ModClient.CONFIG.enchantmentDescriptions.onlyEnchantmentDescriptionsOnBooks.value() && stack.is(Items.ENCHANTED_BOOK)) {
            return DescriptionKey.empty();
        }
        //Ensure items from Polymer get the correct key instead of a vanilla one.
        //? if fabric {
        if (PolymerHelpers.getServerResourceLocation(stack) != null) {
            return new DescriptionKey(PolymerHelpers.getServerResourceLocation(stack));
        }
        //?}
        //Ensure items with Custom Models get a custom key instead of a vanilla one.
        //? if >1.21.2 {
        if (hasComponent(stack, DataComponents.ITEM_MODEL))  {
            ResourceLocation data = Objects.requireNonNull(stack.getComponents().get(DataComponents.ITEM_MODEL));
            DescriptionKey modelKey = new DescriptionKey(data);
            if (modelKey.hasTranslation()) {
                return modelKey;
            }
        } else
        //?}
        //Ensure items with Custom Model Data get a custom key instead of a vanilla one.
        if (hasComponent(stack, DataComponents.CUSTOM_MODEL_DATA)) {
            var data = Objects.requireNonNull(stack.getComponents().get(DataComponents.CUSTOM_MODEL_DATA));
            //? if <1.21.4 {
             /*var dataValue = data.value();
            *///?} else {
            var dataValue = data.getString(0);
            //?}
            DescriptionKey key = getDescriptionKey(stack);
            DescriptionKey modelKey = key.hasTranslation() ? key : TagHelpers.checkGenericTagList(stack);
            modelKey.setSuffix(".custommodeldata." + dataValue);
            if (modelKey.hasTranslation()) {
                return modelKey;
            }
        }
        //Ensure Paintings get a custom key instead of a vanilla one.
        else if (stack.is(Items.PAINTING) && hasComponent(stack, DataComponents.ENTITY_DATA)) {
            var data = Objects.requireNonNull(stack.getComponents().get(DataComponents.ENTITY_DATA));
            //? if >1.21.8 {
            var variant = toTranslationKey(data.copyTagWithoutId().getStringOr("variant", ""));
            //?} else if >=1.21.5 {
            /*var variant = toTranslationKey(data.copyTag().getString("variant").orElse(""));
            *///?} else {
            /*var variant = toTranslationKey(data.copyTag().getString("variant"));
            *///?}
            var paintingKey = new DescriptionKey("lore", "minecraft", "painting", variant);
            if (paintingKey.hasTranslation() || ModClient.CONFIG.developerOptions.showAllPotentialKeys.value()) return paintingKey;
        }
        //Ensure player heads with Profile components get a custom key instead of a vanilla one.
        else if (hasComponent(stack, DataComponents.PROFILE)) {
            DescriptionKey profileKey = getProfile(stack);
            if (profileKey.hasTranslation()) {
                return profileKey;
            }
        }
        DescriptionKey name = getModdedNameMatch(stack);
        if (name.hasTranslation()) {
            return name;
        }
        //Find the tooltip translation key for the provided item stack.
        DescriptionKey key = getDescriptionKey(stack);
        return checkLoreKey(key.hasTranslation() ? key : TagHelpers.checkGenericTagList(stack));
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
        if (ModClient.CONFIG.developerOptions.showUntranslated.value()) return true;
        return I18n.exists(key);
    }

    /**
     * Create a block's lore key based off data from WAILA-based Block Accessors like Jade/WTHIT/HYWLA.
     */
    public static DescriptionKey createBlockDescription(Block block, Level world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        //Convert block translation key to lore translation key.
        DescriptionKey loreKey = findLoreKey(block);
        //? if fabric {
        if (isLoaded("polymer-bundled"))
            if (pos != null && PolymerHelpers.isPolymerBlock(pos)) {
                loreKey = new DescriptionKey(PolymerHelpers.findPolymerBlockResourceLocation(pos));
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
                    return findLoreKey(contents);
            }
        }
        //? if fabric {
        if (isLoaded("glowcase")) {
            if (GlowcaseHelpers.isItemDisplay(blockEntity)) {
                var contents = GlowcaseHelpers.getItemDisplayContents(blockEntity);
                if (contents != null)
                    return findLoreKey(contents);
            }
        }
        //?}
        //Check if translation exists. If not, see if an item exists for it - e.g. seeds.
        if (!loreKey.hasTranslation()) {
            if (pos == null) return findLoreKey(block.asItem().getDefaultInstance());
            //? if <1.21.2 {
            /*return findLoreKey(block.getCloneItemStack(world, pos, state));
            *///?} else
            return findLoreKey(state.getCloneItemStack(world, pos, true));
        }
        return loreKey;
    }

    /**
     * Create an entity's lore key based off its entity data.
     */
    public static List<Component> createEntityDescription(Entity entity) {
        //Create and add tooltip.
        Component name = entity.getName();
        if (entity instanceof ItemFrame itemFrameEntity && !itemFrameEntity.getItem().isEmpty()) {
            return createTooltip(name, findLoreKey(itemFrameEntity.getItem()));
        }
        else if (entity instanceof Painting painting && painting.getVariant().isBound()) {
            var loreKey = new DescriptionKey("lore", "minecraft", "painting", toTranslationKey(painting.getVariant().getRegisteredName()));
            if (loreKey.hasTranslation())
                return createTooltip(name, loreKey);
        }
        return createTooltip(name, findLoreKey(entity));
    }

    public static boolean createEnchantmentDescription(ItemStack stack, List<Component> lines) {
        boolean descriptionFound = false;
        if (ModClient.CONFIG.enchantmentDescriptions.enable.value() && showEnchantmentDescriptions()) {
            if (ModClient.CONFIG.enchantmentDescriptions.onlyShowOnBooks.value() && !stack.getItem().equals(Items.ENCHANTED_BOOK))
                return false;
            final var enchantments = new HashSet<>(EnchantmentHelper.getEnchantmentsForCrafting(stack).keySet());
            if (enchantments.isEmpty()) return false;
            for (var enchantmentEntry : enchantments) {
                var enchantment = enchantmentEntry.value();
                for (int i = 0; i < lines.size(); i++) {
                    if (!lines.get(i).getContents().equals(enchantment.description().getContents())) continue;
                    ComponentContents description = lines.get(i).getContents();
                    if (description instanceof TranslatableContents translatableTextContent) {
                        var descriptionKey = new DescriptionKey(translatableTextContent.getKey());
                        lines.add(i+1, descriptionKey.toText().setStyle(ModStyle.ENCHANTMENT_DESCRIPTIONS));
                    }
                }
            }
        }
        return descriptionFound;
    }

    private static boolean checkTranslatableText(Component text, Predicate<TranslatableContents> predicate) {
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

    public static void fixEnchantmentDescription(ItemStack stack, List<Component> lines) {
        if (ModClient.CONFIG.enchantmentDescriptions.enable.value() && useInternalEnchantmentDescriptions()) {
            if (ModClient.CONFIG.enchantmentDescriptions.onlyShowOnBooks.value() && !stack.getItem().equals(Items.ENCHANTED_BOOK))
                return;

            final var enchantments = new HashSet<>(EnchantmentHelper.getEnchantmentsForCrafting(stack).keySet());

            if (enchantments.isEmpty())
                return;

            for (int i = 0; i < lines.size(); ++i) {
                if (isEnchantmentDescription(lines.get(i), (Set)enchantments)) {
                    // Create the tooltip.
                    var newLines = createTooltip(stack.getDisplayName(), lines.get(i), useInternalWrapper());
                    if (newLines.isEmpty())
                        continue;
                    // To avoid warnings, we don't remove from lines and instead modify the initial line to the first line.
                    lines.set(i, newLines.getFirst());
                    // Add the remaining lines.
                    if (newLines.size() > 1) {
                        lines.addAll(i+1, newLines.subList(1, newLines.size()));
                    }
                }
            }
        }
    }

    private static boolean isEnchantmentDescription(Component text, Set<Object> enchantments) {
        // If the Minecraft world is null, we cannot check if this is an enchantment key.
        if (Minecraft.getInstance().level == null)
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
                return enchantments.stream().anyMatch(entry -> ((Holder<Enchantment>)(Object)entry).is(ModHelpers.of(namespace, path)));
            }
            return false;
        });
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
                        }
                        else if (ModClient.CONFIG.hint.enabled.value() && (key.hasTranslation())) {
                            addHint(lines);
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

    public static void addHint(List<Component> lines) {
        lines.add(1, getHintText().setStyle(ModStyle.HINT));
    }

    public static boolean createItemDescription(ItemStack stack, List<Component> lines) {
        if (ModClient.CONFIG.itemDescriptions.value()) {
            //Create and add tooltip.
            List<Component> tooltip;
            DescriptionKey descriptionKey = findLoreKey(stack);
            if (ModClient.CONFIG.developerOptions.showAllPotentialKeys.value()) {
                tooltip = TagHelpers.findAllPotentialKeys(stack);
            } else if (descriptionKey.hasTranslation()) {
                tooltip = List.of(descriptionKey.toText());
            }
            else return false;
            tooltip = tooltip.stream().map(text -> (Component)text.copy().setStyle(ModStyle.ITEM_DESCRIPTIONS)).toList();
            if (showItemDescriptions())
                lines.addAll(1, tooltip);
            else return descriptionKey.hasTranslation();
        }
        return false;
    }

    public static void fixItemDescription(ItemStack stack, List<Component> lines) {
        if (ModClient.CONFIG.itemDescriptions.value() && showItemDescriptions()) {
            //Find and wrap tooltip. Will be disabled if TooltipFix is installed.
            List<Component> tooltip;
            DescriptionKey descriptionKey = findLoreKey(stack);
            if (ModClient.CONFIG.developerOptions.showAllPotentialKeys.value()) {
                tooltip = TagHelpers.findAllPotentialKeys(stack);
            } else {
                tooltip = List.of(descriptionKey.toText());
            }
            for (int i = 0; i < lines.size(); ++i) {
                // Required for lambda comparison.
                int finalI = i;
                // Check if any of the tooltips' content matches the current line's content.
                if (tooltip.stream().anyMatch(text -> text.getContents().equals(lines.get(finalI).getContents()))) {
                    var newLines = createTooltip(stack.getDisplayName(), lines.get(i), useInternalWrapper());
                    lines.set(i, newLines.getFirst());
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
    public static boolean hasComponent(ItemStack stack, DataComponentType<?> type) {
        return stack.getComponents().has(type);
    }

    /**
     * Find a profile name in a Player Head Item Stack.
     */
    public static DescriptionKey getProfile(ItemStack stack) {
        var optionalProfileName = Objects.requireNonNull(Objects.requireNonNull(stack.getComponents().get(DataComponents.PROFILE)).name()).orElse("");
        if (!optionalProfileName.isEmpty()) {
            DescriptionKey key = getDescriptionKey(stack);
            DescriptionKey profileKey = key.hasTranslation() ? key : TagHelpers.checkGenericTagList(stack);
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
            optionalProfileName = Objects.requireNonNull(((SkullBlockEntity) blockEntity).getOwnerProfile()).name().orElse("");
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
        return ModClient.CONFIG.blockDescriptions.enable.value() && (tooltipKeyPressed() || ModClient.CONFIG.blockDescriptions.showAlways.value());
    }

    /**
     * Check if item descriptions should be shown based off configuration.
     */
    public static boolean showItemDescriptions() {
        return ModClient.CONFIG.itemDescriptions.value() && (tooltipKeyPressed() || ModClient.CONFIG.displayAlways.value());
    }
    /**
     * Check if enchantment descriptions should be shown based off configuration.
     */
    public static boolean showEnchantmentDescriptions() {
        return ModClient.CONFIG.enchantmentDescriptions.enable.value() && useInternalEnchantmentDescriptions() && (tooltipKeyPressed() || ModClient.CONFIG.enchantmentDescriptions.displayAlways.value());
    }
    /**
     * Check if entity descriptions should be shown based off configuration.
     */
    public static boolean showEntityDescriptions() {
        return ModClient.CONFIG.entityDescriptions.enable.value() && (tooltipKeyPressed() || ModClient.CONFIG.entityDescriptions.showAlways.value());
    }
    /**
     * Check if effect descriptions should be shown based off configuration.
     */
    public static boolean showEffectDescriptions() {
        return ModClient.CONFIG.effectDescriptions.enable.value() && useInternalEffectDescriptions() && (tooltipKeyPressed() || ModClient.CONFIG.effectDescriptions.displayAlways.value());
    }

    public static void createDescriptionsFromItemStack(ItemStack stack, Item.TooltipContext context, TooltipFlag type, List<Component> lines) {
        if (ModClient.CONFIG.developerOptions.hideOtherTooltips.value() || ModLists.hidden_items.contains(stack.getItem())) {
            var first = lines.getFirst();
            lines.clear();
            lines.add(first);
        }
        boolean enchant = createEnchantmentDescription(stack, lines);
        boolean effect = checkForEffectDescription(stack);
        if (ModClient.CONFIG.effectDescriptions.onlyShowEffectDescriptions.value() && effect) return;
        boolean item = createItemDescription(stack, lines);
        if (ModClient.CONFIG.hint.enabled.value() && (item || enchant)) {
            addHint(lines);
        }
        if ((tooltipKeyPressed() || ModClient.CONFIG.displayAlways.value()) && ModClient.CONFIG.showModName.value()) {
            addModName(stack, lines);
        }
    }

    private static void addModName(ItemStack stack, List<Component> lines) {
        String namespace = Platform.INSTANCE.getModName(stack);
        MutableComponent text = Component.literal(namespace);
        lines.add(text.setStyle(ModStyle.MOD_NAME));
    }

    public static String getModName(ItemStack stack) {
        return Platform.INSTANCE.getModName(stack);
    }

    public static MutableComponent translatableWithFallback(String translatable, String fallback) {
        return Component.translatableWithFallback(translatable, fallback);
    }

    private static boolean checkForEffectDescription(ItemStack stack) {
        if (hasComponent(stack, DataComponents.POTION_CONTENTS)) {
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

    public static void fixItemStackDescriptionTooltip(ItemStack stack, Item.TooltipContext context, TooltipFlag type, List<Component> lines) {
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
     * Shorthand to check a block's lore key.
     */
    public static DescriptionKey findLoreKey(Block block) {
        DescriptionKey key = getDescriptionKey(block);
        return checkLoreKey(key.hasTranslation() ? key : TagHelpers.checkGenericTagList(block));
    }

    /**
     * Shorthand to check a blockstate's lore key.
     */
    public static DescriptionKey findLoreKey(BlockState state) {
        DescriptionKey key = getDescriptionKey(state);
        return checkLoreKey(key.hasTranslation() ? key : TagHelpers.checkGenericTagList(state));
    }

    /**
     * Shorthand to check an entity's lore key.
     */
    public static DescriptionKey findLoreKey(Entity entity) {
        return findLoreKey(entity.getType());
    }

    /**
     * Shorthand to check an entity's lore key.
     */
    public static DescriptionKey findLoreKey(EntityType<?> entity) {
        DescriptionKey key = getDescriptionKey(entity);
        return checkLoreKey(key.hasTranslation() ? key : TagHelpers.checkGenericTagList(entity));
    }

    /**
     * Check if a lore key exists or if a generic tooltip should be used.
     */
    public static DescriptionKey checkLoreKey(DescriptionKey loreKey) {
        //Check if the tooltip translation key exists. If so, use the provided tooltip.
        if (loreKey != null && loreKey.hasTranslation()) return loreKey;
        else return DescriptionKey.empty();
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

    public static @NotNull DescriptionKey getDescriptionKey(ItemStack stack) {
        return getDescriptionKey(stack.getItem());
    }

    public static @NotNull DescriptionKey getDescriptionKey(Item item) {
        return convertToLoreKey(item.getDescriptionId());
    }

    public static @NotNull DescriptionKey getDescriptionKey(BlockState blockState) {
        return getDescriptionKey(blockState.getBlock());
    }

    public static @NotNull DescriptionKey getDescriptionKey(Block block) {
        return convertToLoreKey(block.getDescriptionId());
    }

    public static @NotNull DescriptionKey getDescriptionKey(EntityType<?> entityType) {
        return convertToLoreKey(entityType.getDescriptionId());
    }

    public static @NotNull DescriptionKey getDescriptionKey(Entity entity) {
        return convertToLoreKey(getEntityTranslationKey(entity));
    }

    public static @NotNull DescriptionKey getDescriptionKey(MobEffect effect) {
        return new DescriptionKey(effect.getDescriptionId());
    }

    public static @NotNull DescriptionKey getDescriptionKey(Enchantment enchantment) {
        return getEnchantmentDescriptionKey(enchantment);
    }

    public static DescriptionKey getEnchantmentDescriptionKey(Enchantment enchantment) {
        //? if >1.21 {
        return enchantment.description().getContents() instanceof TranslatableContents translatable ? new DescriptionKey(translatable.getKey()) : DescriptionKey.empty();
         //?} else
        /*return new DescriptionKey(enchantment.getDescriptionId());*/
    }

    /**
     * Find an entity's translation key
     */
    public static String getEntityTranslationKey(Entity entity) {
        //Allow for custom player descriptions
        if (entity instanceof Player) {
            //? if >1.21 {
            String playerKey = "entity.minecraft.player.%s".formatted(entity.getName().tryCollapseToString());
            //?} else
            /*String playerKey = "entity.minecraft.player." + entity.getName().getString();;*/
            //Check if a custom player description exists.
            if (hasTranslation(playerKey)) return playerKey;
            //If not, use the default one.
            else return "entity.minecraft.player";
        } else {
            return entity.getType().getDescriptionId();
        }
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

    /**
     * An internal method for wrapping this tooltip.
     * @param lines The lines for the final tooltip.
     * @param keys Any contents that make up this text object. Obtained through {@link Component#getSiblings()}.
     */
    private static void wrapTooltip(Component name, List<Component> lines, List<Component> keys) {
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
                    wrapTooltip(name, lines, originalText.getSiblings());
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
                wrapTooltip(name, lines, originalText.getSiblings());
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
            //? if >1.21.10 {
            /*var id = key.identifier();
            *///?} else {
            var id = key.location();
            //?}
            if (description.isEmpty()) {
                LOGGER.warn("[Item Descriptions] Couldn't get lore key for {}: {}!", registry.getAny().get(), id);
            } else if (keys.stream().noneMatch(I18n::exists)) {
                keys.remove(description.asLoreTranslation());
                if (value instanceof ItemStack stack) keys.remove(getModdedNameMatch(stack).asLoreTranslation()); // Ugly
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
        addMissingTranslations((Registry<EntityType<?>>) (Object) registryGetter.get(ResourceKey.createRegistryKey(ModHelpers.of("minecraft","entity_type"))).orElse(null), namespaces, Function.identity(), ModHelpers::getDescriptionKey, TagHelpers::findAllPotentialKeys);
        addMissingTranslations((Registry<Item>) (Object) registryGetter.get(ResourceKey.createRegistryKey(ModHelpers.of("minecraft", "item"))).orElse(null), namespaces, Item::getDefaultInstance, ModHelpers::getDescriptionKey, TagHelpers::findAllPotentialKeys);
        addMissingTranslations((Registry<Enchantment>) (Object) registryGetter.get(ResourceKey.createRegistryKey(ModHelpers.of("minecraft","enchantment"))).orElse(null), namespaces, Function.identity(), ModHelpers::getDescriptionKey, TagHelpers::findAllPotentialKeys);
        addMissingTranslations((Registry<Block>) (Object) registryGetter.get(ResourceKey.createRegistryKey(ModHelpers.of("minecraft","block"))).orElse(null), namespaces, Block::defaultBlockState, ModHelpers::getDescriptionKey, TagHelpers::findAllPotentialKeys); // Blocks will overwrite items
        addMissingTranslations((Registry<MobEffect>) (Object) registryGetter.get(ResourceKey.createRegistryKey(ModHelpers.of("minecraft","mob_effect"))).orElse(null), namespaces, Function.identity(), ModHelpers::getDescriptionKey, TagHelpers::findAllPotentialKeys);
        Map<String, String> combined = new TreeMap<>();
        namespaces.values().forEach(combined::putAll);
        namespaces.put(ModClient.MOD_ID_NEO, combined);
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
