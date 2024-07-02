package cc.cassian.item_descriptions;

import cc.cassian.item_descriptions.client.config.ModConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ModHelpers {
    public static boolean clothConfigInstalled() {
        return FabricLoader.getInstance().isModLoaded("cloth-config");
    }
    public static boolean tooltipFixInstalled() {
        return FabricLoader.getInstance().isModLoaded("tooltipfix");
    }
    public static boolean jadeInstalled() {
        return FabricLoader.getInstance().isModLoaded("jade");
    }

    public static Formatting getColor() {
        return Formatting.byCode(ModConfig.get().tooltipColor.charAt(0));
    }

    public static int getIndex(String translatedKey) {
        String subKey = translatedKey.substring(0, 25);
        int index;
        //Find the last space character in the substring, if not, default to the length of the substring.
        if (subKey.contains(" ")) {
            index = subKey.lastIndexOf(" ")+1;
        }
        else {
            index = 25;
        }
        return index;
    }

    public static boolean tooltipKeyPressed() {
        ModConfig config = ModConfig.get();
        if (config.displayAlways) {
            return true;
        }
        else {
            if (config.displayWhenControlIsHeld) {
                return checkKey(Screen.hasControlDown());
            }
            if (config.displayWhenShiftIsHeld) {
                return checkKey(Screen.hasShiftDown());
            }
            if (config.displayWhenAltIsHeld) {
                return checkKey(Screen.hasAltDown());
            }}
        return false;
    }

    public static boolean checkKey(boolean key) {
        boolean invert = ModConfig.get().invert;
        if (key) {
            return !invert;
        }
        else if (!key) {
            return invert;
        }
        else {
            return false;
        }
    }

    public static String findLoreKey(ItemStack stack) {
        if (stack.getComponents().contains(DataComponentTypes.CUSTOM_MODEL_DATA)) {
            return "lore.custommodeldata." + Objects.requireNonNull(stack.getComponents().get(DataComponentTypes.CUSTOM_MODEL_DATA)).value();
        }
        //Find the tooltip translation key for the provided item stack.
        String loreKey = getLoreKey(stack);
        return findLoreKey(loreKey);
    }

    public static String findLoreKey(Block stack) {
        //Find the tooltip translation key for the provided item stack.
        String loreKey = getLoreKey(stack);
        return findLoreKey(loreKey);
    }

    public static String findLoreKey(String loreKey) {
        //Check if the tooltip translation key exists. If so, use the provided tooltip.
        if (I18n.hasTranslation(loreKey)) {
            return loreKey;
        }
        //If the tooltip translation key does not exist, use one of the provided generic tooltips.
        else {
            return getGenericKey(loreKey);
        }
    }

    private static @NotNull String getGenericKey(String loreKey) {
        //Iterate through the provided generic keys.
        if (loreKey.contains("planks")) {
            return "lore.generic.planks";
        }
        else if (loreKey.contains("_sword")) {
            return "lore.generic.sword";
        }
        else if (loreKey.contains("_hoe")) {
            return "lore.generic.hoe";
        }
        else if (loreKey.contains("_shovel")) {
            return "lore.generic.shovel";
        }
        else if (loreKey.contains("_pickaxe")) {
            return "lore.generic.pickaxe";
        }
        else if (loreKey.contains("_axe")) {
            return "lore.generic.axe";
        }
        else if (loreKey.contains("stripped_")) {
            return "lore.generic.stripped_log";
        }
        else if (loreKey.contains("horse_armor")) {
            return "lore.generic.horse_armor";
        }
        else if (loreKey.contains("tipped_arrow")) {
            return "lore.minecraft.tipped_arrow";
        }
        else if (loreKey.contains("splash_potion")) {
            return "lore.minecraft.splash_potion";
        }
        else if (loreKey.contains("lingering_potion")) {
            return "lore.minecraft.lingering_potion";
        }
        else if (loreKey.contains("potion")) {
            return "lore.minecraft.potion";
        }
        else if (loreKey.contains("smithing_template")) {
            return "lore.generic.smithing_template";
        }
        else if (loreKey.contains("helmet")) {
            return "lore.generic.helmet";
        }
        else if (loreKey.contains("chestplate")) {
            return "lore.generic.chestplate";
        }
        else if (loreKey.contains("leggings")) {
            return "lore.generic.leggings";
        }
        else if (loreKey.contains("boots")) {
            return "lore.generic.boots";
        }
        else if (loreKey.contains("wool")) {
            return "lore.generic.wool";
        }
        else if (loreKey.contains("lamp")) {
            return "lore.generic.lamp";
        }
        else if (loreKey.contains("turf")) {
            return "lore.generic.turf";
        }
        else if (loreKey.contains("carpet")) {
            return "lore.generic.carpet";
        }
        else if (loreKey.contains("dead_")) {
            return "lore.generic.dead_coral";
        }
        else if (loreKey.contains("coral_block")) {
            return "lore.generic.coral_block";
        }
        else if (loreKey.contains("coral")) {
            return "lore.generic.coral";
        }
        else if (loreKey.contains("froglight")) {
            return "lore.generic.froglight";
        }
        else if (loreKey.contains("spawn_egg")) {
            return "lore.generic.spawn_egg";
        }
        else if (loreKey.contains("_stem")) {
            return "lore.generic.stem";
        }
        else if ((loreKey.contains("_wood")) || (loreKey.contains("_hyphae"))) {
            return "lore.generic.wood";
        }
        else if (loreKey.contains("_log")) {
            return "lore.generic.log";
        }
        else if (loreKey.contains("_hanging_sign")) {
            return "lore.generic.hanging_sign";
        }
        else if (loreKey.contains("_sign")) {
            return "lore.generic.sign";
        }
        else if (loreKey.contains("shulker_box")) {
            return "lore.generic.shulker_box";
        }
        else if (loreKey.contains("lantern")) {
            return "lore.generic.lantern";
        }
        else if (loreKey.contains("button")) {
            return "lore.generic.button";
        }
        else if ((loreKey.contains("_sapling")) || (loreKey.contains("_propagule"))) {
            return "lore.generic.sapling";
        }
        else if (loreKey.contains("_pressure_plate")) {
            return "lore.generic.pressure_plate";
        }
        else if (loreKey.contains("_stairs")) {
            return "lore.generic.stairs";
        }
        else if (loreKey.contains("wall_gate")) {
            return "lore.generic.wall_gate";
        }
        else if (loreKey.contains("wall")) {
            return "lore.generic.wall";
        }
        else if (loreKey.contains("fence_gate")) {
            return "lore.generic.fence_gate";
        }
        else if (loreKey.contains("fence")) {
            return "lore.generic.fence";
        }
        else if (loreKey.contains("_slab")) {
            return "lore.generic.slab";
        }
        else if (loreKey.contains("trapdoor")) {
            return "lore.generic.trapdoor";
        }
        else if (loreKey.contains("door")) {
            return "lore.generic.door";
        }
        else if (loreKey.contains("waxed")) {
            return "lore.generic.waxed";
        }
        else if (loreKey.contains("stained_glass")) {
            return "lore.generic.stained_glass";
        }
        else if (loreKey.contains("glass")) {
            return "lore.generic.glass";
        }
        else if (loreKey.contains("sherd")) {
            return "lore.generic.sherd";
        }
        else if (loreKey.contains("leaves")) {
            return "lore.generic.leaves";
        }
        else if (loreKey.contains("infested")) {
            return "lore.generic.infested";
        }
        else if (loreKey.contains("head") || (loreKey.contains("skull"))) {
            return "lore.generic.skull";
        }
        else if (loreKey.contains("cake")) {
            return "lore.generic.cake";
        }
        else if (loreKey.contains("candle")) {
            return "lore.generic.candle";
        }
        else if (loreKey.contains("dye")) {
            return "lore.generic.dye";
        }
        else if (loreKey.contains("music_disc")) {
            return "lore.generic.music_disc";
        }
        else if (loreKey.contains("banner_pattern")) {
            return "lore.generic.banner_pattern";
        }
        else if (loreKey.contains("banner")) {
            return "lore.generic.banner";
        }
        else if (loreKey.contains("concrete_powder")) {
            return "lore.generic.concrete_powder";
        }
        else if (loreKey.contains("concrete")) {
            return "lore.generic.concrete";
        }
        else if (loreKey.contains("glazed_terracotta")) {
            return "lore.generic.glazed_terracotta";
        }
        else if (loreKey.contains("terracotta")) {
            return "lore.generic.terracotta";
        }
        else if (loreKey.contains("bed")) {
            return "lore.generic.bed";
        }
        else if ((loreKey.contains("chest_boat")) || (loreKey.contains("chest_raft"))) {
            return "lore.generic.chest_boat";
        }
        else if ((loreKey.contains("boat")) || (loreKey.contains("_raft"))) {
            return "lore.generic.boat";
        }
        else if (loreKey.contains("bricks")) {
            return "lore.generic.bricks";
        }
        else if (loreKey.contains("chiseled")) {
            return "lore.generic.chiseled";
        }
        else if (loreKey.contains("smooth_")) {
            return "lore.generic.smooth";
        }
        else if (loreKey.contains("bars")) {
            return "lore.generic.bars";
        }
        else if (loreKey.contains("cut_")) {
            return "lore.generic.cut";
        }
        else if (loreKey.contains("crafting_table")) {
            return "lore.generic.crafting_table";
        }
        else if (loreKey.contains("torch_lever")) {
            return "lore.generic.torch_lever";
        }
        else if (loreKey.contains("torch")) {
            return "lore.generic.torch";
        }
        else if (loreKey.contains("ladder")) {
            return "lore.generic.ladder";
        }
        //If no key matches, return an empty string.
        else {
            return "";
        }
    }

    private static @NotNull String getLoreKey(ItemStack stack) {
        return getLoreTranslationKey(stack.getTranslationKey());
    }

    private static @NotNull String getLoreKey(Block stack) {
        return getLoreTranslationKey(stack.getTranslationKey());
    }

    private static @NotNull String getLoreTranslationKey(String translationKey) {
        String loreKey;
        //Find the translation key for blocks.
        if (translationKey.contains("block.")) {
            loreKey = translationKey.replaceFirst("block", "lore");
        }
        //Find the translation key for items.
        else if ((translationKey.contains("item."))) {
            loreKey = translationKey.replaceFirst("item", "lore");
        }
        //In case the translation key somehow does not contain a
        else {
            loreKey = translationKey;
        }
        return loreKey;
    }

    public static List<Text> createTooltip(String loreKey, boolean wrap) {
        //Setup list to store (potentially multi-line) tooltip.
        ArrayList<Text> lines = new ArrayList<>();
        if (tooltipKeyPressed()) {
            //Check if the key exists.
            if (!loreKey.isEmpty()) {
                //Translate the lore key.
                String translatedKey =  I18n.translate(loreKey);
                //Check if the translated key exists.
                if (!translatedKey.isEmpty()) {
                    //Check if custom wrapping should be used.
                    if (wrap) {
                        //Any tooltip longer than 25 characters should be shortened.
                        while (translatedKey.length() >= 25) {
                            //Find how much to shorten the tooltip by.
                            int index = getIndex(translatedKey);
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
        }
        return lines;
    }
}
