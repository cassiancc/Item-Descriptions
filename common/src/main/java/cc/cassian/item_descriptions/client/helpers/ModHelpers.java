package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.config.ModConfig;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

import static cc.cassian.item_descriptions.client.ModClient.MOD_ID;
import static cc.cassian.item_descriptions.client.helpers.GenericKeys.*;

public class ModHelpers {
    //Shorthand for config.
    public static final ModConfig config = ModConfig.get();

    //Check if Cloth Config is installed and its configuration can be used.
    @ExpectPlatform
    public static boolean clothConfigInstalled() {
        throw new AssertionError();
    }

    //Check if ToolTipFix is installed and its wrapper should be used.
    @ExpectPlatform
    public static boolean tooltipFixInstalled() {
        throw new AssertionError();
    }

    //Used in Config to change the tooltip's colour based off a Minecraft Colour Code.
    public static Formatting getColor() {
        return Formatting.byCode(config.tooltipColor.charAt(0));
    }

    //Handles detection of when a line break should be added in a tooltip.
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

    //Check if a keybind is pressed and a tooltip should be displayed.
    public static boolean tooltipKeyPressed() {
        if (config.keybind_displayWhenControlIsHeld && Screen.hasControlDown()) return checkKey(Screen.hasControlDown());
        else if (config.keybind_displayWhenShiftIsHeld && Screen.hasShiftDown()) return checkKey(Screen.hasShiftDown());
        else if (config.keybind_displayWhenAltIsHeld && Screen.hasAltDown()) return checkKey(Screen.hasAltDown());
        else return false;
    }

    //Check if a keybind is pressed. Contains the handling for if the key is inverted.
    @SuppressWarnings({"DuplicateCondition", "ConstantValue"})
    public static boolean checkKey(boolean key) {
        boolean invert = config.keybind_invert;
        //If key is pressed, display the tooltip unless inverted.
        if (key) return !invert;
        //If key is not pressed, don't display the tooltip unless inverted.
        else if (!key) return invert;
        else return false;
    }

    //Create an item's lore key based off data from its Item Stack.
    public static String findItemLoreKey(ItemStack stack) {
        //Ensure items with Custom Model Data get a custom key instead of a vanilla one.
        if (hasComponent(stack, DataComponentTypes.CUSTOM_MODEL_DATA)) {
            String modelKey = getLoreKey(stack) + ".custommodeldata." + Objects.requireNonNull(stack.getComponents().get(DataComponentTypes.CUSTOM_MODEL_DATA)).value();
            if (hasTranslation(modelKey)) {
                return modelKey;
            }
        }
        //Ensure player heads with Profile components get a custom key instead of a vanilla one.
        else if (hasComponent(stack, DataComponentTypes.PROFILE)) {
            String profileKey = getProfile(stack);
            if (hasTranslation(profileKey)) {
                return profileKey;
            }
        }
        //Find the tooltip translation key for the provided item stack.
        return checkLoreKey(getLoreKey(stack));
    }

    //Create a block's lore key based off data from WAILA-based Block Accessors like Jade/WTHIT/HYWLA.
    public static String getBlockAccessorLoreKey(Block block, World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        //Convert block translation key to lore translation key.
        String loreKey = findBlockLoreKey(block);
        //Custom handling of Player Heads so custom profiles give custom descriptions.
        if (blockEntity instanceof SkullBlockEntity) {
            String profileKey = getProfile(blockEntity, loreKey);
            //Only show custom descriptions if a translation is present.
            if (hasTranslation(profileKey)) {
                return profileKey;
            }
        }
        //Check if translation exists. If not, see if an item exists for it - e.g. seeds.
        if (!hasTranslation(loreKey)) {
            return findItemLoreKey(block.getPickStack(world, pos, state));
        }
        return loreKey;
    }

    //Check if an Item Stack has a particular component.
    public static boolean hasComponent(ItemStack stack, ComponentType<?> type) {
        return stack.getComponents().contains(type);
    }

    //Find a profile name in a Player Head Item Stack.
    public static String getProfile(ItemStack stack) {
        Optional<String> optionalProfileName = Objects.requireNonNull(Objects.requireNonNull(stack.getComponents().get(DataComponentTypes.PROFILE)).name());
        if (optionalProfileName.isPresent()) {
            String profileKey = getLoreKey(stack) + ".profile." + getProfileName(optionalProfileName);
            if (hasTranslation(profileKey)) {
                return profileKey;
            }
        }
        return "";
    }

    //Find a profile name in a Player Head block.
    public static String getProfile(BlockEntity blockEntity, String loreKey) {
        Optional<String> optionalProfileName;
        try {
             optionalProfileName = Objects.requireNonNull(((SkullBlockEntity) blockEntity).getOwner()).name();

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

    //Check if block descriptions should be shown based off configuration.
    public static boolean showBlockDescriptions() {
        return config.blockDescriptions && (tooltipKeyPressed() || config.displayBlockDescriptionsAlways);
    }

    //Check if item descriptions should be shown based off configuration.
    public static boolean showItemDescriptions() {
        return config.itemDescriptions && (tooltipKeyPressed() || config.displayAlways);
    }
    //Check if entity descriptions should be shown based off configuration.
    public static boolean showEntityDescriptions() {
        return config.entityDescriptions && (tooltipKeyPressed() || config.displayEntityDescriptionsAlways);
    }

    //Find a profile name
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

    //Shorthand to check a block's lore key.
    public static String findBlockLoreKey(Block block) {
        return checkLoreKey(getLoreKey(block));
    }

    //Shorthand to check an entity's lore key.
    public static String findEntityLoreKey(Entity entity) {
        return checkLoreKey(getLoreKey(entity));
    }

    //Check if a lore key exists or if a generic tooltip should be used.
    public static String checkLoreKey(String loreKey) {
        //This function handles whether a generic tooltip should be used, or if a tooltip exists.
        if (!config.developer_dontTranslate) {
            //Check if the tooltip translation key exists. If so, use the provided tooltip.
            if (hasTranslation(loreKey)) return loreKey;
            //If the tooltip translation key does not exist, use one of the provided generic tooltips.
            else return getGenericLoreKey(loreKey);
        }
        else return loreKey;
    }

    //Check if a tag exists, or if a generic one should be used.
    private static @NotNull String getLoreKey(Object object) {
        @NotNull String key = getLoreTranslationKey(object);
        if (hasTranslation(key)) {
            return key;
        }
        else {
            return getGenericKey(object);
        }
    }

    //Check if a block or item is in a common tag - helper for checkNamespacedTag.
    public static boolean checkCommonTag(Object object, String tag) {
        return checkNamespacedTag("c", object, tag);
    }

    //Check if a block or item is in a vanilla tag - helper for checkNamespacedTag.
    public static boolean checkVanillaTag(Object object, String tag) {
        return checkNamespacedTag("minecraft", object, tag);
    }

    //Check if a block or item is in a particular tag.
    public static boolean checkNamespacedTag(String namespace, Object object, String tag) {
        if (object instanceof ItemStack stack) {
            return stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(namespace, tag)));
        }
        else if (object instanceof BlockState state) {
            return state.isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of(namespace, tag)));
        }
        else {
            return false;
        }
    }

    //Convert block/item/entity translation keys to lore translation keys.
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

    //Convert block/item/entity translation keys to lore translation keys.
    public static @NotNull String getLoreTranslationKey(Object object) {
        return switch (object) {
            case ItemStack stack -> convertToLoreKey(stack.getTranslationKey());
            case Block block -> convertToLoreKey(block.getTranslationKey());
            case Entity entity -> convertToLoreKey(getEntityTranslationKey(entity));
            case null, default -> "";
        };
    }

    //Find an entity's translation key through substrings.
    public static String getEntityTranslationKey(Entity entity) {
        if (entity.isPlayer()) {
            return "entity.minecraft.player";
        }
        else {
            try {
                String translatedString = String.valueOf(entity.getName());
                translatedString = translatedString.substring(translatedString.indexOf("{"));
                translatedString = translatedString.substring(translatedString.indexOf("'")+1);
                translatedString = translatedString.substring(0, translatedString.indexOf("'"));
                return translatedString;
            }
            catch (StringIndexOutOfBoundsException ignored) {
                return "entity.minecraft.unknown";
            }
        }

    }

    //Translate key with I18n. Can be disabled with developer options.
    public static String translate(String key) {
        if (!config.developer_dontTranslate) return I18n.translate(key);
        else return key;
    }

    //Check for translation with I18n. Can be disabled with developer options.
    public static boolean hasTranslation(String key) {
        if (!config.developer_showUntranslated) return I18n.hasTranslation(key);
        else return true;
    }

    //Create a custom, potentially multi-line tooltip.
    public static List<Text> createTooltip(String loreKey, boolean wrap) {
        //Setup list to store (potentially multi-line) tooltip.
        ArrayList<Text> lines = new ArrayList<>();
        int maxLength = config.maxTooltipLength;
        //Check if the key exists.
        if (!loreKey.isEmpty()) {
            //Translate the lore key.
            String translatedKey = translate(loreKey);
            //Check if the translated key exists.
            if (hasTranslation(loreKey)) {
                //Check if custom wrapping should be used.
                if (wrap) {
                    //Any tooltip longer than 25 characters should be shortened.
                    while (translatedKey.length() >= maxLength) {
                        //Find how much to shorten the tooltip by.
                        int index = getIndex(translatedKey, maxLength);
                        //Add a shortened tooltip.
                        lines.add(Text.literal(translatedKey.substring(0, index)).formatted(getColor()));
                        //Remove the shortened tooltip substring from the tooltip. Repeat.
                        translatedKey = translatedKey.substring(index);
                    }
                }
                //Add the final tooltip.
                lines.add(Text.literal(translatedKey).formatted(getColor()));
            }
        }
        return lines;
    }

    //Automatically generate translation keys for config options.
    public static Text fieldName(Field field) {
        return Text.translatable("config."+MOD_ID+".config." + field.getName());
    }
    
    //Automatically generate translation keys for config tooltips. Relies on custom tooltip wrapping.
    public static Text[] fieldTooltip(Field field) {
        String tooltipKey = "config."+MOD_ID+".config." + field.getName() + ".tooltip";
        return createTooltip(tooltipKey, true).toArray(new Text[0]);
    }

    //Get the current value of a config field.
    @SuppressWarnings("unchecked")
    public static <T> T fieldGet(Object instance, Field field) {
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //Set a config field.
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
