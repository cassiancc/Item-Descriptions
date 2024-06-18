package cc.cassian.lore;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class ModHelpers {
    public static String findLoreKey(ItemStack stack) {
        String translationKey = stack.getTranslationKey();
        String loreKey;
        if (translationKey.contains("block.")) {
            loreKey = translationKey.replaceFirst("block", "lore");
        }
        else if ((translationKey.contains("item."))) {
            loreKey = translationKey.replaceFirst("item", "lore");
        }
        else {
            loreKey = translationKey;
        }
        if (I18n.hasTranslation(loreKey)) {
           return loreKey;
        }
        else {
            if (loreKey.contains("planks")) {
                return "lore.generic.planks";
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
            else if (loreKey.contains("wood")) {
                return "lore.generic.wood";
            }
            else if (loreKey.contains("log")) {
                return "lore.generic.log";
            }
            else if (loreKey.contains("sign")) {
                return "lore.generic.sign";
            }
            else if (loreKey.contains("shulker")) {
                return "lore.generic.shulker_box";
            }
            else if (loreKey.contains("lantern")) {
                return "lore.generic.lantern";
            }
            else if (loreKey.contains("button")) {
                return "lore.generic.button";
            }
            else if ((loreKey.contains("sapling")) || (loreKey.contains("propagule"))) {
                return "lore.generic.sapling";
            }
            else if (loreKey.contains("pressure_plate")) {
                return "lore.generic.pressure_plate";
            }
            else if (loreKey.contains("stair")) {
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
                return "lore.generic.wall";
            }
            else if (loreKey.contains("slab")) {
                return "lore.generic.slab";
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
            else if (loreKey.contains("trapdoor")) {
                return "lore.generic.trapdoor";
            }
            else if (loreKey.contains("door")) {
                return "lore.generic.door";
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
            else {
                return "";
            }
        }
    }
}
