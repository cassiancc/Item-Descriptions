package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.config.ModConfig;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
//? if >1.20 {
import net.minecraft.registry.tag.TagKey;
//?} else {
/*import net.minecraft.tag.TagKey;
 *///?}
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class TagHelpers {
    @Deprecated
    public static DescriptionKey getDeprecatedStringMatch(String loreKey) {
        return null;

    }

    private static boolean checkMatch(DescriptionKey[] currentKey, DescriptionKey newKey) {
        return newKey.hasTranslation() && (currentKey[0] == null || DescriptionKey.isMorePrecise(currentKey[0], newKey));
    }

    private static DescriptionKey checkGenericTagList(Object object) {
        // If object is an item, check for Item Tags
        if (object instanceof ItemStack itemStack) {
            final Item item = itemStack.getItem();
            //Temporary - Spawn Eggs do not yet have a tag.
            if (item instanceof SpawnEggItem) {
                return new DescriptionKey("tag", "c", "spawn_egg");
            }
            final DescriptionKey[] returnedKey = new DescriptionKey[1];
            itemStack.streamTags().forEach(itemTagKey -> {
                DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                if (checkMatch(returnedKey, loreKey)) {
                    returnedKey[0] = loreKey;
                }
            });
            // If untagged, check if it is a Block Item and if a Block Tag matches.
            if (returnedKey[0] == null) {
                if ((item instanceof BlockItem blockItem)) {
                    blockItem.getBlock().getDefaultState().streamTags().forEach(itemTagKey -> {
                        DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                        if (checkMatch(returnedKey, loreKey)) {
                            returnedKey[0] = loreKey;
                        }
                    });
                }
            }
            return returnedKey[0];

            //If object is a blockstate, check it for Block tags
        } else if (object instanceof BlockState state) {
            final DescriptionKey[] returnedKey = new DescriptionKey[1];
            state.streamTags().forEach(itemTagKey -> {
                DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                if (checkMatch(returnedKey, loreKey)) {
                    returnedKey[0] = loreKey;
                }
            });
            return returnedKey[0];
        } else if (object instanceof Entity entity) {
            final DescriptionKey[] returnedKey = new DescriptionKey[1];
            entity.getType().getRegistryEntry().streamTags().forEach(itemTagKey -> {
                DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                if (checkMatch(returnedKey, loreKey)) {
                    returnedKey[0] = loreKey;
                }
            });
            return returnedKey[0];
            //If no tag key matches, return empty so a string match can be found.
        }
        return DescriptionKey.empty();
    }

    private static void addSafe(ArrayList<Text> tags, DescriptionKey newAdd) {
        Text newText;
        if (Screen.hasAltDown())
            newText = newAdd.toText();
        else {
            newText = Text.literal(newAdd.toString());
        }
        if (!tags.contains(newText))
            tags.add(newText);
    }

    /**
     * Check an object for potential keys. This includes all of its block/item/entity tags,
     * as well as its direct match and any implemented custom component data.
     */
    public static List<Text> findAllPotentialKeys(Object object) {
        ArrayList<Text> tags = new ArrayList<>(); // Create an ArrayList object
        // If object is an item, check for Item Tags
        if (Objects.requireNonNull(object) instanceof ItemStack itemStack) {
            addSafe(tags, findItemLoreKey(itemStack));
            addSafe(tags, getLoreTranslationKey(itemStack));
            addSafe(tags, getModdedNameMatch(itemStack));
            final Item item = itemStack.getItem();
            //Temporary - Spawn Eggs do not yet have a tag.
            if (item instanceof SpawnEggItem) {
                addSafe(tags, new DescriptionKey("tag", "c", "spawn_egg"));
            }
            itemStack.streamTags().forEach(itemTagKey -> {
                DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                addSafe(tags, loreKey);
            });
            // If untagged, check if it is a Block Item and if a Block Tag matches.
            if ((item instanceof BlockItem blockItem)) {
                blockItem.getBlock().getDefaultState().streamTags().forEach(itemTagKey -> {
                    DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                    addSafe(tags, loreKey);
                });
            }
            //If object is a blockstate, check it for Block tags
        } else if (object instanceof BlockState state) {
            state.streamTags().forEach(itemTagKey -> {
                DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                addSafe(tags, loreKey);
            });
        } else if (object instanceof Entity entity) {
            entity.getType().getRegistryEntry().streamTags().forEach(itemTagKey -> {
                DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                addSafe(tags, loreKey);
            });
        } else {
            tags.add(Text.empty());
        }
        return tags;
    }

    /**
     * Convert a TagKey into a translation key.
     */
    private static DescriptionKey tagKeyToGenericKey(TagKey<?> key) {
        return new DescriptionKey("tag", key.id());
    }

    public static DescriptionKey getGenericKey(Object object) {
        if (!ModConfig.get().developer_disableGenericTagDescriptions) {
            //Iterate through the provided generic tag list.
            DescriptionKey generic = checkGenericTagList(object);
            if (generic != null) {
                if (generic.isEmpty()) return DescriptionKey.empty();
                else return generic;
            }
        }
        return DescriptionKey.empty();
    }
}
