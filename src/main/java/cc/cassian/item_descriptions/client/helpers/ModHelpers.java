package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.config.ModConfig;
import cc.cassian.item_descriptions.client.helpers.compat.FastItemFramesHelpers;
import cc.cassian.item_descriptions.client.helpers.compat.GlowcaseHelpers;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
//? if >1.20.5 {
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import cc.cassian.item_descriptions.client.helpers.compat.PolymerHelpers;
//?} else {
/*import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
*///?}
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

import static cc.cassian.item_descriptions.client.ModClient.MOD_ID;
import static cc.cassian.item_descriptions.client.helpers.TagHelpers.*;
import static net.minecraft.client.resource.language.I18n.translate;

public class ModHelpers {

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
        return !isLoaded("tooltipfix");
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
    public static Style getStyle() {
        return getStyle(ModConfig.get().style_color);
    }

    /**
     * Used in Config to change the tooltip's formatting.
     */
    public static Style getStyle(String colour) {
        return Style.EMPTY.withColor(getColour(colour)).withItalic(ModConfig.get().style_italics).withBold(ModConfig.get().style_bold);
    }

    /**
     * Used in Config to change the tooltip's formatting.
     */
    public static Text getHintText() {
        var sb = new StringBuilder();
        var config = ModConfig.get();
        var shift = config.keybind_displayWhenShiftIsHeld;
        var ctrl = config.keybind_displayWhenControlIsHeld;
        var alt = config.keybind_displayWhenAltIsHeld;
        if (config.hint_showKeybind) {
            if (ctrl) {
                sb.append(I18n.translate("hint.item-descriptions.ctrl"));
                if (shift || alt) sb.append("/");
            }
            if (alt) {
                sb.append(I18n.translate("hint.item-descriptions.alt"));
                if (shift) sb.append("/");
            }
            if (shift) {
                sb.append(I18n.translate("hint.item-descriptions.shift"));
            }
            sb.append(": ");
        }
        if (config.keybind_invert)
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
            index = subKey.lastIndexOf(" ")+1;
        }
        else index = maxLength;
        return index;
    }

    /**
     * Check if a keybind is pressed and a tooltip should be displayed.
     */
    public static boolean tooltipKeyPressed() {
        if (ModConfig.get().keybind_displayWhenControlIsHeld && Screen.hasControlDown()) return checkKey(Screen.hasControlDown());
        else if (ModConfig.get().keybind_displayWhenShiftIsHeld && Screen.hasShiftDown()) return checkKey(Screen.hasShiftDown());
        else if (ModConfig.get().keybind_displayWhenAltIsHeld && Screen.hasAltDown()) return checkKey(Screen.hasAltDown());
        else return false;
    }

    /**
     * Check if a keybind is pressed. Contains the handling for if the key is inverted.
     */
    @SuppressWarnings({"DuplicateCondition", "ConstantValue"})
    public static boolean checkKey(boolean key) {
        boolean invert = ModConfig.get().keybind_invert;
        //If key is pressed, display the tooltip unless inverted.
        if (key) return !invert;
        //If key is not pressed, don't display the tooltip unless inverted.
        else if (!key) return invert;
        else return false;
    }

    /**
     * Create an item's lore key based off data from its Item Stack.
     */
    public static String findItemLoreKey(ItemStack stack) {
        //Ensure items with Custom Model Data get a custom key instead of a vanilla one.
        //? if >1.20.5 {
            if (PolymerHelpers.getServerIdentifier(stack) != null) {
                return "lore."+convertToLoreKey(PolymerHelpers.getServerIdentifier(stack).toTranslationKey());
            } else 
            if (hasComponent(stack, DataComponentTypes.CUSTOM_MODEL_DATA)) {
                var data = Objects.requireNonNull(stack.getComponents().get(DataComponentTypes.CUSTOM_MODEL_DATA));
                //? if <1.21.4 {
                 /*var dataValue = data.value();
                *///?} else {
                var dataValue = data.getString(0);
                //?}
                String modelKey = getLoreKey(stack) + ".custommodeldata." + dataValue;
                if (hasTranslation(modelKey)) {
                    return modelKey;
                }
            }
            else if (stack.isOf(Items.PAINTING) && hasComponent(stack, DataComponentTypes.ENTITY_DATA)) {
                var data = Objects.requireNonNull(stack.getComponents().get(DataComponentTypes.ENTITY_DATA));
                var variant = toTranslationKey(data.copyNbt().getString("variant"));
                var paintingKey = "lore.minecraft.painting."+variant;
                if (hasTranslation(paintingKey) || ModConfig.get().developer_showAllPotentialKeys) return paintingKey;
            }
            //Ensure player heads with Profile components get a custom key instead of a vanilla one.
            else if (hasComponent(stack, DataComponentTypes.PROFILE)) {
                String profileKey = getProfile(stack);
                if (hasTranslation(profileKey)) {
                    return profileKey;
                }
            }
        //?} else {
        /*NbtCompound s = stack.getNbt();
            if (s != null) {
                if (s.contains("CUSTOM_MODEL_DATA", NbtElement.NUMBER_TYPE)) {
                    return getLoreKey(stack) + ".custommodeldata." + Objects.requireNonNull(s.get("CUSTOM_MODEL_DATA"));
                }
                else if (s.contains("SkullOwner", NbtElement.STRING_TYPE)) {
                    String profileKey = getProfile(stack);
                    if (hasTranslation(profileKey)) {
                        return profileKey;
                    }
                }
            }
        *///?}
        var name = getModdedNameMatch(stack);
        if (hasTranslation(name)) {
            return name;
        }
        //Find the tooltip translation key for the provided item stack.
        return checkLoreKey(getLoreKey(stack));
    }

    public static String getModdedNameMatch(ItemStack stack) {
        return getLoreTranslationKey(stack)+"."+toTranslationKey(stack.getItem().getName(stack).getString());
    }

    public static String toTranslationKey(String string) {
        return string.toLowerCase().replaceAll("\"", "").replaceAll(" ", "_").replaceAll("[/:]", ".");
    }

    public static boolean hasTranslation(String key) {
        if (ModConfig.get().developer_showUntranslated) return true;
        return I18n.hasTranslation(key);
    }

    /**
     * Create a block's lore key based off data from WAILA-based Block Accessors like Jade/WTHIT/HYWLA.
     */
    public static String createBlockDescription(Block block, World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        //Convert block translation key to lore translation key.
        String loreKey = findBlockLoreKey(block);
        //? if >1.20.5 {
        if (isLoaded("polymer-bundled"))
            if (PolymerHelpers.isPolymerBlock(pos)) {
                loreKey = PolymerHelpers.findPolymerBlockIdentifier(pos);
            }
        //?}
        //Custom handling of Player Heads so custom profiles give custom descriptions.
        if (blockEntity instanceof SkullBlockEntity) {
            String profileKey = getProfile(blockEntity, loreKey);
            //Only show custom descriptions if a translation is present.
            if (hasTranslation(profileKey)) {
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
        if (!hasTranslation(loreKey)) {
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
        if (entity instanceof ItemFrameEntity itemFrameEntity && !itemFrameEntity.getHeldItemStack().isEmpty()) {
            return createTooltip(findItemLoreKey(itemFrameEntity.getHeldItemStack()));
        }
        //? if >1.21 {
        else if (entity instanceof PaintingEntity painting && painting.getVariant().hasKeyAndValue()) {
            var loreKey = "lore.minecraft.painting."+toTranslationKey(painting.getVariant().getIdAsString());
            if (hasTranslation(loreKey))
                return createTooltip(loreKey);
        }
        //?}
        return createTooltip(findEntityLoreKey(entity));
    }

    /**
     * Check if an Item Stack has a particular component.
     */
    //? if >1.20.5 {
    public static boolean hasComponent(ItemStack stack, ComponentType<?> type) {
        return stack.getComponents().contains(type);
    }
    //?}

    /**
     * Find a profile name in a Player Head Item Stack.
     */
    public static String getProfile(ItemStack stack) {
        //? if >1.20.5 {
        var optionalProfileName = Objects.requireNonNull(Objects.requireNonNull(stack.getComponents().get(DataComponentTypes.PROFILE)).name());
        //?} else {
        /*var optionalProfileName = Objects.requireNonNull(stack.getNbt().get("CUSTOM_MODEL_DATA")).toString();
         *///?}
        if (!optionalProfileName.isEmpty()) {
            String profileKey = getLoreKey(stack) + ".profile." + getProfileName(optionalProfileName);
            if (hasTranslation(profileKey)) {
                return profileKey;
            }
        }
        return "";
    }

    /**
     * Find a profile name in a Player Head block.
     */
    public static String getProfile(BlockEntity blockEntity, String loreKey) {
        Optional<String> optionalProfileName;
        try {
            //? if >1.20.5 {
            optionalProfileName = Objects.requireNonNull(((SkullBlockEntity) blockEntity).getOwner()).name();
            //?} else
            /*optionalProfileName = Optional.of(Objects.requireNonNull(((SkullBlockEntity) blockEntity).getOwner()).getName());*/
        }
        catch (NullPointerException nullPointerException) {
            return loreKey;
        }
        String profileKey = loreKey + ".profile." + getProfileName(optionalProfileName);
        if (hasTranslation(profileKey)) {
            return profileKey;
        }
        else return loreKey;
    }

    /**
     * Check if block descriptions should be shown based off configuration.
     */
    public static boolean showBlockDescriptions() {
        return ModConfig.get().blockDescriptions && (tooltipKeyPressed() || ModConfig.get().displayBlockDescriptionsAlways);
    }

    /**
     * Check if item descriptions should be shown based off configuration.
     */
    public static boolean showItemDescriptions() {
        return ModConfig.get().itemDescriptions && (tooltipKeyPressed() || ModConfig.get().displayAlways);
    }
    /**
     * Check if entity descriptions should be shown based off configuration.
     */
    public static boolean showEntityDescriptions() {
        return ModConfig.get().entityDescriptions && (tooltipKeyPressed() || ModConfig.get().displayEntityDescriptionsAlways);
    }

    /**
     * Find a profile name
     */
    public static String getProfileName(Optional<String> optionalProfileName) {
        String profileName;
        if (optionalProfileName.isPresent()) {
            profileName = optionalProfileName.get();
            return profileName;
        }
        else {
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
    public static String findBlockLoreKey(Block block) {
        return checkLoreKey(getLoreKey(block));
    }

    /**
     * Shorthand to check an entity's lore key.
     */
    public static String findEntityLoreKey(Entity entity) {
        return checkLoreKey(getLoreKey(entity));
    }

    /**
     * Check if a lore key exists or if a generic tooltip should be used.
     */
    public static String checkLoreKey(String loreKey) {
        //Check if the tooltip translation key exists. If so, use the provided tooltip.
        if (hasTranslation(loreKey)) return loreKey;
        //If the tooltip translation key does not exist, use one of the provided generic tooltips.
        else return getDeprecatedStringMatch(loreKey);
    }

    /**
     * Check if a tag exists, or if a generic one should be used.
     */
    private static @NotNull String getLoreKey(Object object) {
        @NotNull String key = getLoreTranslationKey(object);
        if (hasTranslation(key)) {
            return key;
        }
        else {
            return getGenericKey(object);
        }
    }

    /**
     * Convert block/item/entity translation keys to lore translation keys.
     */
    public static @NotNull String convertToLoreKey(String translationKey) {
        String loreKey;
        //Find the translation key for blocks.
        if (translationKey.contains("block.")) loreKey = translationKey.replaceFirst("block", "lore");
        //Find the translation key for items.
        else if ((translationKey.contains("item."))) loreKey = translationKey.replaceFirst("item", "lore");
        //Find the translation key for entities.
        else if ((translationKey.contains("entity."))) {
            //Entity descriptions use a different format as to avoiding colliding with items of the same name.
            String oldKey = translationKey.replaceFirst("entity", "lore");
            String newKey = translationKey + ".description";
            //Tropical fish have 20 different variants and their description should be the same.
            if (newKey.contains("tropical_fish")) {
                newKey = "entity.minecraft.tropical_fish";
            }
            //In case an entity tooltip is misconfigured, try checking for an "old style" key.
            if (hasTranslation(newKey)) return newKey;
            else if (hasTranslation(oldKey)) return oldKey;
            else return newKey;
        }
        //In case the translation key somehow does not contain a block/item/entity.
        else loreKey = translationKey;
        return loreKey;
    }

    /**
     * Convert block/item/entity translation keys to lore translation keys.
     */
    public static @NotNull String getLoreTranslationKey(Object object) {
        if (object instanceof ItemStack stack) {
            return convertToLoreKey(stack.getItem().getTranslationKey());
        } else if (object instanceof Block block) {
            return convertToLoreKey(block.getTranslationKey());
        } else if (object instanceof Entity entity) {
            return convertToLoreKey(getEntityTranslationKey(entity));
        }
        return "";
    }

    /**
     * Find an entity's translation key
     */
    public static String getEntityTranslationKey(Entity entity) {
        //Allow for custom player descriptions
        if (entity.isPlayer()) {
            //? if >1.21 {
            String playerKey = "entity.minecraft.player.%s".formatted(entity.getName().getLiteralString());;
            //?} else
            /*String playerKey = "entity.minecraft.player." + entity.getName().getString();;*/
            //Check if a custom player description exists.
            if (hasTranslation(playerKey)) return playerKey;
            //If not, use the default one.
            else return "entity.minecraft.player";
        }
        else {
            return entity.getType().getTranslationKey();
        }
    }

    /**
     * Create a custom multi-line tooltip.
     *
     * @param loreKey The translation key that will be translated and wrapped.
     */
    public static List<Text> createTooltip(String loreKey) {
        return createTooltip(loreKey, true);
    }

    /**
     * Create a custom, potentially multi-line tooltip.
     * @param loreKey The translation key that will be translated.
     * @param wrap Whether to use the built-in wrapper.
     */
    public static List<Text> createTooltip(String loreKey, boolean wrap) {
        //Setup list to store (potentially multi-line) tooltip.
        ArrayList<Text> lines = new ArrayList<>();
        int maxLength = ModConfig.get().style_length;
        //Check if the key exists.
        if (!loreKey.isBlank()) {
            //Translate the lore key.
            String translatedKey = translate(loreKey);
            //Check if the translated key exists.
            if (hasTranslation(loreKey)) {
                TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
                //Check if custom wrapping should be used.
                if (textRenderer != null && wrap && (maxLength != 0)) {
                    //Any tooltip longer than XX pixels should be shortened.
                    while (textRenderer.getWidth(Text.of(translatedKey)) >= maxLength && translatedKey.contains(" ")) {
                        int lineLength = translatedKey.length();
                        // Find where to end this line, starting from the remaining string.
                        while (translatedKey.substring(0, lineLength).contains(" ") && textRenderer.getWidth(Text.of(translatedKey.substring(0, lineLength))) >= maxLength) {
                            lineLength = translatedKey.substring(0, lineLength).lastIndexOf(' ');
                        }
                        // Add the line.
                        lines.add(Text.literal(translatedKey.substring(0, lineLength)).setStyle(getStyle()));
                        // Remove the line substring from the start of the remaining string. Repeat.
                        translatedKey = translatedKey.substring(lineLength + 1);
                    }
                }
                //Add the final tooltip.
                if (!translatedKey.isBlank()) lines.add(Text.literal(translatedKey).setStyle(getStyle()));
            }
        }
        return lines;
    }

    /**
     * Automatically generate translation keys for config options.
     */
    public static Text fieldName(Field field) {
        return Text.translatable("config.%s.config.%s".formatted(MOD_ID, field.getName()));
    }
    
    /**
     * Automatically generate translation keys for config tooltips. Relies on custom tooltip wrapping.
     */
    public static Text[] fieldTooltip(Field field) {
        String tooltipKey = "config.%s.config.%s.tooltip".formatted(MOD_ID, field.getName());
        return createTooltip(tooltipKey).toArray(new Text[0]);
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
}
