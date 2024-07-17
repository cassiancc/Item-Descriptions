package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.config.ModConfig;
import org.jetbrains.annotations.NotNull;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class GenericKeys {
    public static @NotNull String getGenericLoreKey(String loreKey) {
        if (!ModConfig.get().developer_disableGenericStringDescriptions) {
            //Iterate through the provided generic keys.
            if (loreKey.contains("planks")) return "lore.generic.planks";
            else if (loreKey.contains("_sword")) return "lore.generic.sword";
            else if (loreKey.contains("_hoe")) return "lore.generic.hoe";
            else if (loreKey.contains("_shovel")) return "lore.generic.shovel";
            else if (loreKey.contains("_pickaxe")) return "lore.generic.pickaxe";
            else if (loreKey.contains("_axe")) return "lore.generic.axe";
            else if (loreKey.contains("stripped_")) return "lore.generic.stripped_log";
            else if (loreKey.contains("horse_armor")) return "lore.generic.horse_armor";
            else if (loreKey.contains("tipped_arrow")) return "lore.minecraft.tipped_arrow";
            else if (loreKey.contains("splash_potion")) return "lore.minecraft.splash_potion";
            else if (loreKey.contains("lingering_potion")) return "lore.minecraft.lingering_potion";
            else if (loreKey.contains("potion")) return "lore.minecraft.potion";
            else if (loreKey.contains("smithing_template")) return "lore.generic.smithing_template";
            else if (loreKey.contains("helmet")) return "lore.generic.helmet";
            else if (loreKey.contains("chestplate")) return "lore.generic.chestplate";
            else if (loreKey.contains("leggings")) return "lore.generic.leggings";
            else if (loreKey.contains("boots")) return "lore.generic.boots";
            else if (loreKey.contains("wool")) return "lore.generic.wool";
            else if (loreKey.contains("lamp")) return "lore.generic.lamp";
            else if (loreKey.contains("turf")) return "lore.generic.turf";
            else if (loreKey.contains("carpet")) return "lore.generic.carpet";
            else if (loreKey.contains("dead_")) return "lore.generic.dead_coral";
            else if (loreKey.contains("coral_block")) return "lore.generic.coral_block";
            else if (loreKey.contains("coral")) return "lore.generic.coral";
            else if (loreKey.contains("froglight")) return "lore.generic.froglight";
            else if (loreKey.contains("spawn_egg"))  return "lore.generic.spawn_egg";
            else if (loreKey.contains("_stem"))  return "lore.generic.stem";
            else if ((loreKey.contains("_wood")) || (loreKey.contains("_hyphae"))) return "lore.generic.wood";
            else if (loreKey.contains("_log")) return "lore.generic.log";
            else if (loreKey.contains("_hanging_sign")) return "lore.generic.hanging_sign";
            else if (loreKey.contains("_sign")) return "lore.generic.sign";
            else if (loreKey.contains("shulker_box")) return "lore.generic.shulker_box";
            else if (loreKey.contains("lantern")) return "lore.generic.lantern";
            else if (loreKey.contains("button")) return "lore.generic.button";
            else if ((loreKey.contains("_sapling")) || (loreKey.contains("_propagule"))) return "lore.generic.sapling";
            else if (loreKey.contains("_pressure_plate")) return "lore.generic.pressure_plate";
            else if (loreKey.contains("_stairs")) return "lore.generic.stairs";
            else if (loreKey.contains("wall_gate")) return "lore.generic.wall_gate";
            else if (loreKey.contains("wall")) return "lore.generic.wall";
            else if (loreKey.contains("fence_gate")) return "lore.generic.fence_gate";
            else if (loreKey.contains("fence")) return "lore.generic.fence";
            else if (loreKey.contains("_slab")) return "lore.generic.slab";
            else if (loreKey.contains("trapdoor")) return "lore.generic.trapdoor";
            else if (loreKey.contains("door")) return "lore.generic.door";
            else if (loreKey.contains("waxed")) return "lore.generic.waxed";
            else if (loreKey.contains("stained_glass")) return "lore.generic.stained_glass";
            else if (loreKey.contains("glass")) return "lore.generic.glass";
            else if (loreKey.contains("sherd")) return "lore.generic.sherd";
            else if (loreKey.contains("leaves")) return "lore.generic.leaves";
            else if (loreKey.contains("infested")) return "lore.generic.infested";
            else if (loreKey.contains("banner_pattern")) return "lore.generic.banner_pattern";
            else if (loreKey.contains("head") || (loreKey.contains("skull"))) return "lore.generic.skull";
            else if (loreKey.contains("cake")) return "lore.generic.cake";
            else if (loreKey.contains("candle")) return "lore.generic.candle";
            else if (loreKey.contains("dye")) return "lore.generic.dye";
            else if (loreKey.contains("music_disc")) return "lore.generic.music_disc";
            else if (loreKey.contains("banner")) return "lore.generic.banner";
            else if (loreKey.contains("concrete_powder")) return "lore.generic.concrete_powder";
            else if (loreKey.contains("concrete")) return "lore.generic.concrete";
            else if (loreKey.contains("glazed_terracotta")) return "lore.generic.glazed_terracotta";
            else if (loreKey.contains("terracotta")) return "lore.generic.terracotta";
            else if (loreKey.contains("bed")) return "lore.generic.bed";
            else if ((loreKey.contains("chest_boat")) || (loreKey.contains("chest_raft"))) return "lore.generic.chest_boat";
            else if ((loreKey.contains("boat")) || (loreKey.contains("_raft"))) return "lore.generic.boat";
            else if (loreKey.contains("bricks")) return "lore.generic.bricks";
            else if (loreKey.contains("chiseled")) return "lore.generic.chiseled";
            else if (loreKey.contains("smooth_")) return "lore.generic.smooth";
            else if (loreKey.contains("bars")) return "lore.generic.bars";
            else if (loreKey.contains("cut_")) return "lore.generic.cut";
            else if (loreKey.contains("crafting_table")) return "lore.generic.crafting_table";
            else if (loreKey.contains("torch_lever")) return "lore.generic.torch_lever";
            else if (loreKey.contains("torch")) return "lore.generic.torch";
            else if (loreKey.contains("ladder")) return "lore.generic.ladder";
            //If no key matches, return an empty string.
            else return "";
        }
        else return loreKey;

    }

    private static String checkGenericTagList(Object stack) {
        if (checkVanillaTag(stack, "planks")) return "lore.generic.planks";
        else if (checkVanillaTag(stack, "wool")) return "lore.generic.wool";
        else if (checkVanillaTag(stack, "wool_carpets")) return "lore.generic.carpet";
        else if ((checkVanillaTag(stack, "warped_stems")) || (checkVanillaTag(stack, "crimson_stems")))  return "lore.generic.stem";
        else if (checkVanillaTag(stack, "logs")) return "lore.generic.log";
        else if (checkVanillaTag(stack, "hanging_signs")) return "lore.generic.hanging_sign";
        else if (checkVanillaTag(stack, "signs")) return "lore.generic.sign";
        else if (checkVanillaTag(stack, "buttons")) return "lore.generic.button";
        else if (checkVanillaTag(stack, "saplings")) return "lore.generic.sapling";
        else if (checkVanillaTag(stack, "pressure_plates") || checkVanillaTag(stack, "wooden_pressure_plates")) return "lore.generic.pressure_plate";
        else if (checkVanillaTag(stack, "stairs")) return "lore.generic.stairs";
        else if (checkVanillaTag(stack, "walls")) return "lore.generic.wall";
        else if (checkVanillaTag(stack, "fence_gates")) return "lore.generic.fence_gate";
        else if (checkVanillaTag(stack, "fences")) return "lore.generic.fence";
        else if (checkVanillaTag(stack, "slabs")) return "lore.generic.slab";
        else if (checkVanillaTag(stack, "trapdoors")) return "lore.generic.trapdoor";
        else if (checkVanillaTag(stack, "doors")) return "lore.generic.door";
        else if (checkVanillaTag(stack, "leaves")) return "lore.generic.leaves";
        else if (checkVanillaTag(stack, "skulls")) return "lore.generic.skull";
        else if (checkVanillaTag(stack, "candles")) return "lore.generic.candle";
        else if (checkVanillaTag(stack, "banners")) return "lore.generic.banner";
        else if (checkVanillaTag(stack, "terracotta")) return "lore.generic.terracotta";
        else if (checkVanillaTag(stack, "beds")) return "lore.generic.bed";
        else if (checkVanillaTag(stack, "swords")) return "lore.generic.sword";
        else if (checkVanillaTag(stack, "hoes")) return "lore.generic.hoe";
        else if (checkVanillaTag(stack, "shovels")) return "lore.generic.shovel";
        else if (checkVanillaTag(stack, "pickaxes")) return "lore.generic.pickaxe";
        else if (checkVanillaTag(stack, "axes")) return "lore.generic.axe";
        else if (checkVanillaTag(stack, "trim_templates")) return "lore.generic.smithing_template";
        else if (checkVanillaTag(stack, "head_armor")) return "lore.generic.helmet";
        else if (checkVanillaTag(stack, "chest_armor")) return "lore.generic.chestplate";
        else if (checkVanillaTag(stack, "leg_armor")) return "lore.generic.leggings";
        else if (checkVanillaTag(stack, "foot_armor")) return "lore.generic.boots";
        else if (checkVanillaTag(stack, "decorated_pot_sherds")) return "lore.generic.sherd";
        else if (checkVanillaTag(stack, "chest_boats")) return "lore.generic.chest_boat";
        else if (checkVanillaTag(stack, "boats")) return "lore.generic.boat";
        else if (checkCommonTag(stack, "shulker_boxes")) return "lore.generic.shulker_box";
        else if (checkCommonTag(stack, "dyes")) return "lore.generic.dye";
        else if (checkCommonTag(stack, "concrete")) return "lore.generic.concrete";
        else if (checkCommonTag(stack, "concrete_powder")) return "lore.generic.concrete_powder";
        else if (checkCommonTag(stack, "glazed_terracotta")) return "lore.generic.glazed_terracotta";
        else if (checkCommonTag(stack, "glass_blocks")) return "lore.generic.glass";
        else if (checkCommonTag(stack, "glass_panes")) return "lore.minecraft.glass_pane";
        else if (checkCommonTag(stack, "cobblestones")) return "lore.minecraft.cobblestone";
        else if (checkCommonTag(stack, "bookshelves")) return "lore.minecraft.bookshelf";
        else if (checkCommonTag(stack, "player_workstations/crafting_tables")) return "lore.generic.crafting_table";
        else if (checkCommonTag(stack, "chests")) return "lore.minecraft.chest";
        else if (checkCommonTag(stack, "barrels")) return "lore.minecraft.barrel";
        else if (checkCommonTag(stack, "chains")) return "lore.minecraft.chain";
        else if (checkCommonTag(stack, "ropes")) return "lore.generic.rope";
        else if (checkCommonTag(stack, "creeper_drop_music_disc")) return "lore.generic.music_disc_creeper";
        else if (checkCommonTag(stack, "music_disc")) return "lore.generic.music_disc_common";
        else if (checkCommonTag(stack, "villager_job_sites")) return "lore.generic.villager_workstations";
        else if (checkCommonTag(stack, "storage_blocks")) return "lore.generic.storage_blocks";
        else if (checkCommonTag(stack, "raw_materials")) return "lore.generic.raw_materials";
        else if (checkCommonTag(stack, "ores")) return "lore.generic.ores";
        else if (checkCommonTag(stack, "potions")) return "lore.minecraft.potion";

            //If no tag key matches, return empty so a string match can be found.
        else return "";
    }

    public static String getGenericKey(Object object) {
        String loreKey = getLoreTranslationKey(object);
        if (!ModConfig.get().developer_disableGenericTagDescriptions) {
            //Iterate through the provided generic tag list.
            String generic = checkGenericTagList(object);
            if (generic.isEmpty()) {
                return getGenericLoreKey(loreKey);
            }
            else {
                return generic;
            }
        }
        else return getGenericLoreKey(loreKey);
    }
}
