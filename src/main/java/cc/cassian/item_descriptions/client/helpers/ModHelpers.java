package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.config.ModConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static cc.cassian.item_descriptions.client.helpers.GenericKeys.*;

@Environment(EnvType.CLIENT)
public class ModHelpers {
    public static boolean clothConfigInstalled() {
        return FabricLoader.getInstance().isModLoaded("cloth-config");
    }
    public static boolean tooltipFixInstalled() {
        return FabricLoader.getInstance().isModLoaded("tooltipfix");
    }

    public static Formatting getColor() {
        return Formatting.byCode(ModConfig.get().tooltipColor.charAt(0));
    }

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

    public static boolean tooltipKeyPressed() {
        ModConfig config = ModConfig.get();
        if (config.keybind_displayWhenControlIsHeld && Screen.hasControlDown()) return checkKey(Screen.hasControlDown());
        else if (config.keybind_displayWhenShiftIsHeld && Screen.hasShiftDown()) return checkKey(Screen.hasShiftDown());
        else if (config.keybind_displayWhenAltIsHeld && Screen.hasAltDown()) return checkKey(Screen.hasAltDown());
        else return false;
    }

    @SuppressWarnings({"DuplicateCondition", "ConstantValue"})
    public static boolean checkKey(boolean key) {
        boolean invert = ModConfig.get().keybind_invert;
        //If key is pressed, display the tooltip unless inverted.
        if (key) return !invert;
        //If key is not pressed, don't display the tooltip unless inverted.
        else if (!key) return invert;
        else return false;
    }

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

    public static String getBlockAccessorLoreKey(Block block, World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        //Convert block translation key to lore translation key.
        String loreKey = findBlockLoreKey(block);
        //Check if translation exists. If not, see if an item exists for it - e.g. seeds.
        if (blockEntity instanceof SkullBlockEntity) {
            String profileKey = getProfile(blockEntity, loreKey);
            if (hasTranslation(profileKey)) {
                return profileKey;
            }
        }
        if (!hasTranslation(loreKey)) {
            return findItemLoreKey(block.getPickStack(world, pos, state));
        }
        return loreKey;
    }

    public static boolean hasComponent(ItemStack stack, ComponentType<?> type) {
        return stack.getComponents().contains(type);
    }

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

    public static boolean showBlockDescriptions() {
        return ModConfig.get().blockDescriptions && (tooltipKeyPressed() || ModConfig.get().displayBlockDescriptionsAlways);
    }

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

    public static String findBlockLoreKey(Block block) {
        return checkLoreKey(getLoreKey(block));
    }

    public static String checkLoreKey(String loreKey) {
        //This function handles whether a generic tooltip should be used, or if a tooltip exists.
        if (!ModConfig.get().developer_dontTranslate) {
            //Check if the tooltip translation key exists. If so, use the provided tooltip.
            if (hasTranslation(loreKey)) return loreKey;
            //If the tooltip translation key does not exist, use one of the provided generic tooltips.
            else return getGenericLoreKey(loreKey);
        }
        else return loreKey;
    }

    private static @NotNull String getLoreKey(Object object) {
        @NotNull String key = getLoreTranslationKey(object);
        if (hasTranslation(key)) {
            return key;
        }
        else {
            return getGenericKey(object);
        }
    }

    public static boolean checkCommonTag(Object object, String tag) {
        return checkNamespacedTag("c", object, tag);
    }

    public static boolean checkVanillaTag(Object object, String tag) {
        return checkNamespacedTag("minecraft", object, tag);
    }

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

    public static @NotNull String convertToLoreKey(String translationKey) {
        String loreKey;
        //Find the translation key for blocks.
        if (translationKey.contains("block.")) loreKey = translationKey.replaceFirst("block", "lore");
        //Find the translation key for items.
        else if ((translationKey.contains("item."))) loreKey = translationKey.replaceFirst("item", "lore");
        //In case the translation key somehow does not contain a block/item.
        else loreKey = translationKey;
        return loreKey;
    }

    public static @NotNull String getLoreTranslationKey(Object object) {
        if (object instanceof ItemStack stack) return convertToLoreKey(stack.getTranslationKey());
        else if (object instanceof Block block) return convertToLoreKey(block.getTranslationKey());
        else return "";
    }

    public static String translate(String key) {
        if (!ModConfig.get().developer_dontTranslate) return I18n.translate(key);
        else return key;
    }

    public static boolean hasTranslation(String key) {
        if (!ModConfig.get().developer_showUntranslated) return I18n.hasTranslation(key);
        else return true;
    }

    public static List<Text> createTooltip(String loreKey, boolean wrap) {
        //Setup list to store (potentially multi-line) tooltip.
        ArrayList<Text> lines = new ArrayList<>();
        int maxLength = ModConfig.get().maxTooltipLength;
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
}
