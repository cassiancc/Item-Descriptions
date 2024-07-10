package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.config.ModConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
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

    public static String getGenericBlockKey(Block block) {
        @NotNull final String loreKey = getLoreTranslationKey(block);
        final BlockState stack = block.getDefaultState();
        if (!ModConfig.get().developer_disableGenericTagDescriptions) {
            //Iterate through the provided generic keys.
            if (stack.isIn(BlockTags.PLANKS)) return "lore.generic.planks";
            else if (stack.isIn(BlockTags.WOOL)) return "lore.generic.wool";
            else if ((stack.isIn(BlockTags.WOOL_CARPETS))) return "lore.generic.carpet";
            else if ((stack.isIn(BlockTags.WARPED_STEMS)) || (stack.isIn(BlockTags.CRIMSON_STEMS)))  return "lore.generic.stem";
            else if (stack.isIn(BlockTags.LOGS)) return "lore.generic.log";
            else if (stack.isIn(BlockTags.ALL_HANGING_SIGNS)) return "lore.generic.hanging_sign";
            else if (stack.isIn(BlockTags.SIGNS)) return "lore.generic.sign";
            else if (stack.isIn(BlockTags.BUTTONS)) return "lore.generic.button";
            else if (stack.isIn(BlockTags.SAPLINGS)) return "lore.generic.sapling";
            else if (stack.isIn(BlockTags.WOODEN_PRESSURE_PLATES)) return "lore.generic.pressure_plate";
            else if (stack.isIn(BlockTags.STAIRS)) return "lore.generic.stairs";
            else if (stack.isIn(BlockTags.WALLS)) return "lore.generic.wall";
            else if (stack.isIn(BlockTags.FENCE_GATES)) return "lore.generic.fence_gate";
            else if (stack.isIn(BlockTags.FENCES)) return "lore.generic.fence";
            else if (stack.isIn(BlockTags.SLABS)) return "lore.generic.slab";
            else if (stack.isIn(BlockTags.TRAPDOORS)) return "lore.generic.trapdoor";
            else if (stack.isIn(BlockTags.DOORS)) return "lore.generic.door";
            else if (stack.isIn(BlockTags.LEAVES)) return "lore.generic.leaves";
            else if (checkCommonTag(stack, "skulls")) return "lore.generic.skull";
            else if (stack.isIn(BlockTags.CANDLES)) return "lore.generic.candle";
            else if (stack.isIn(BlockTags.BANNERS)) return "lore.generic.banner";
            else if (stack.isIn(BlockTags.TERRACOTTA)) return "lore.generic.terracotta";
            else if (stack.isIn(BlockTags.BEDS)) return "lore.generic.bed";
            else if (checkCommonTag(stack, "shulker_boxes")) return "lore.generic.shulker_box";
            else if (checkCommonTag(stack, "dyes")) return "lore.generic.dye";
            else if (checkCommonTag(stack, "concrete")) return "lore.generic.concrete";
            else if (checkCommonTag(stack, "concrete_powder")) return "lore.generic.concrete_powder";
            else if (checkCommonTag(stack, "glazed_terracotta")) return "lore.generic.glazed_terracotta";
            else if (checkCommonTag(stack, "glass_blocks")) return "lore.generic.glass";
            else if (checkCommonTag(stack, "glass_panes")) return "lore.minecraft.glass_pane";
            else if (checkCommonTag(stack, "cobblestones")) return "lore.minecraft.cobblestone";
            //If no tag key matches, check if a string match can be found.
            else return getGenericLoreKey(loreKey);
        }
        else return getGenericLoreKey(loreKey);
    }

    public static String getGenericItemKey(ItemStack stack) {
        @NotNull final String loreKey = getLoreTranslationKey(stack);
        if (!ModConfig.get().developer_disableGenericTagDescriptions) {
            //Iterate through the provided generic keys.
            if (stack.isIn(ItemTags.PLANKS)) return "lore.generic.planks";
            else if (stack.isIn(ItemTags.SWORDS)) return "lore.generic.sword";
            else if (stack.isIn(ItemTags.HOES)) return "lore.generic.hoe";
            else if (stack.isIn(ItemTags.SHOVELS)) return "lore.generic.shovel";
            else if (stack.isIn(ItemTags.PICKAXES)) return "lore.generic.pickaxe";
            else if (stack.isIn(ItemTags.AXES)) return "lore.generic.axe";
            else if ((stack.isIn(ItemTags.TRIM_TEMPLATES))) return "lore.generic.smithing_template";
            else if (stack.isIn(ItemTags.HEAD_ARMOR)) return "lore.generic.helmet";
            else if (stack.isIn(ItemTags.CHEST_ARMOR)) return "lore.generic.chestplate";
            else if (stack.isIn(ItemTags.LEG_ARMOR)) return "lore.generic.leggings";
            else if (stack.isIn(ItemTags.FOOT_ARMOR)) return "lore.generic.boots";
            else if (stack.isIn(ItemTags.WOOL)) return "lore.generic.wool";
            else if ((stack.isIn(ItemTags.WOOL_CARPETS))) return "lore.generic.carpet";
            else if ((stack.isIn(ItemTags.WARPED_STEMS)) || (stack.isIn(ItemTags.CRIMSON_STEMS)))  return "lore.generic.stem";
            else if (stack.isIn(ItemTags.LOGS)) return "lore.generic.log";
            else if (stack.isIn(ItemTags.HANGING_SIGNS)) return "lore.generic.hanging_sign";
            else if (stack.isIn(ItemTags.SIGNS)) return "lore.generic.sign";
            else if (stack.isIn(ItemTags.BUTTONS)) return "lore.generic.button";
            else if (stack.isIn(ItemTags.SAPLINGS)) return "lore.generic.sapling";
            else if (stack.isIn(ItemTags.WOODEN_PRESSURE_PLATES)) return "lore.generic.pressure_plate";
            else if (stack.isIn(ItemTags.STAIRS)) return "lore.generic.stairs";
            else if (stack.isIn(ItemTags.WALLS)) return "lore.generic.wall";
            else if (stack.isIn(ItemTags.FENCE_GATES)) return "lore.generic.fence_gate";
            else if (stack.isIn(ItemTags.FENCES)) return "lore.generic.fence";
            else if (stack.isIn(ItemTags.SLABS)) return "lore.generic.slab";
            else if (stack.isIn(ItemTags.TRAPDOORS)) return "lore.generic.trapdoor";
            else if (stack.isIn(ItemTags.DOORS)) return "lore.generic.door";
            else if (stack.isIn(ItemTags.DECORATED_POT_SHERDS)) return "lore.generic.sherd";
            else if (stack.isIn(ItemTags.LEAVES)) return "lore.generic.leaves";
            else if (stack.isIn(ItemTags.SKULLS)) return "lore.generic.skull";
            else if (stack.isIn(ItemTags.CANDLES)) return "lore.generic.candle";
            else if (stack.isIn(ItemTags.CREEPER_DROP_MUSIC_DISCS)) return "lore.generic.music_disc";
            else if (stack.isIn(ItemTags.BANNERS)) return "lore.generic.banner";
            else if (stack.isIn(ItemTags.TERRACOTTA)) return "lore.generic.terracotta";
            else if (stack.isIn(ItemTags.BEDS)) return "lore.generic.bed";
            else if (stack.isIn(ItemTags.CHEST_BOATS)) return "lore.generic.chest_boat";
            else if (stack.isIn(ItemTags.BOATS)) return "lore.generic.boat";
            else if (checkCommonTag(stack, "shulker_boxes")) return "lore.generic.shulker_box";
            else if (checkCommonTag(stack, "dyes")) return "lore.generic.dye";
            else if (checkCommonTag(stack, "concrete")) return "lore.generic.concrete";
            else if (checkCommonTag(stack, "concrete_powder")) return "lore.generic.concrete_powder";
            else if (checkCommonTag(stack, "glazed_terracotta")) return "lore.generic.glazed_terracotta";
            else if (checkCommonTag(stack, "glass_blocks")) return "lore.generic.glass";
            else if (checkCommonTag(stack, "glass_panes")) return "lore.minecraft.glass_pane";
            else if (checkCommonTag(stack, "cobblestones")) return "lore.minecraft.cobblestone";
            //If no tag key matches, check if a string match can be found.
            else return getGenericLoreKey(loreKey);
        }
        else return getGenericLoreKey(loreKey);
    }
}
