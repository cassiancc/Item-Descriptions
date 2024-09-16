package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.config.ModConfig;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
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

    private static String convertIdentifierToDescriptionKey(Identifier itemTagKey) {
        String convertedKey = itemTagKey.toString().replace(":", ".").replace("/", ".");
        return "tag."+convertedKey+".description";
    }

    private static String checkGenericTagList(Object object) {
        // If object is an item, check for Item Tags
        if ((object instanceof ItemStack)) {
            final ItemStack itemStack = (ItemStack) object;
            final Item item = itemStack.getItem();
//            //Temporary - Spawn Eggs do not yet have a tag.
            if (item instanceof SpawnEggItem) {
                return "tag.c.spawn_egg.description";
            }
            final String[] returnedKey = new String[1];
            ItemTags.getContainer().getTagsFor(item).stream().forEach(itemTagKey -> {
                String loreKey = convertIdentifierToDescriptionKey(itemTagKey);
                System.out.println(loreKey);
                if (hasTranslation(loreKey)) {
                returnedKey[0] = loreKey;
                }
            });
            ItemTags.getContainer().getTagsFor(item).stream().forEach(itemTagKey -> {
                String loreKey = convertIdentifierToDescriptionKey(itemTagKey);
                if (hasTranslation(loreKey)) {
                    returnedKey[0] = loreKey;
                }
            });
            // If untagged, check if it is a Block Item and if a Block Tag matches.
            if (returnedKey[0] == null) {
                if ((item instanceof BlockItem)) {
                    BlockItem blockItem = (BlockItem) item;
                    BlockTags.getContainer().getTagsFor(blockItem.getBlock()).stream().forEach(itemTagKey -> {
                        String loreKey = convertIdentifierToDescriptionKey(itemTagKey);
                        if (hasTranslation(loreKey)) {
                            returnedKey[0] = loreKey;
                        }
                    });
                }
            }
            return returnedKey[0];
        }
//        If object is a blockstate, check it for Block tags
        else if ((object instanceof BlockState)) {
            final BlockState state = (BlockState) object;
            final String[] returnedKey = new String[1];
            BlockTags.getContainer().getTagsFor(state.getBlock()).stream().forEach(itemTagKey -> {
                String loreKey = "tag."+itemTagKey.toString()+".description";
//                if (I18n.hasTranslation(loreKey)) {
                    returnedKey[0] = loreKey;
//                }
            });
            return returnedKey[0];
        }
        //If no tag key matches, return empty so a string match can be found.
        return "";
    }

    public static String getGenericKey(Object object) {
        String loreKey = getLoreTranslationKey(object);
        if (!ModConfig.get().developer_disableGenericTagDescriptions) {
            //Iterate through the provided generic tag list.
            String generic = checkGenericTagList(object);
            if (generic != null) {
                if (generic.isEmpty())
                    return getGenericLoreKey(loreKey);
                else return generic;
            }
            else return getGenericLoreKey(loreKey);
        }
        else return getGenericLoreKey(loreKey);
    }
}
