package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
//? if >1.20 {
import net.minecraft.tags.TagKey;
//?} else {
/*import net.minecraft.tag.TagKey;
 *///?}
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class TagHelpers {

    private static boolean checkMatch(DescriptionKey[] currentKey, DescriptionKey newKey) {
        return newKey.hasTranslation() && (currentKey[0] == null || DescriptionKey.isMorePrecise(currentKey[0], newKey));
    }

    private static DescriptionKey checkGenericTagList(Object object) {
        // If object is an item, check for Item Tags
        if (object instanceof ItemStack itemStack) {
            final Item item = itemStack.getItem();
            //Temporary - Spawn Eggs do not yet have a tag.
            if (item instanceof SpawnEggItem spawnEggItem) {
                if (ModClient.CONFIG.spawnEggsShowEntity.value()) {
                    EntityType<?> entityType = spawnEggItem.getType(
                            //? if >1.21.8 {
                            /*itemStack
                            *///?} else if >1.21.1 {
                            ModClient.lookup, itemStack
                            //?} else if >1.20.1 {
                            /*itemStack
                            *///?} else {
                            /*itemStack.getTag()
                            *///?}
                    );
                    var key = ModHelpers.getDescriptionKey(entityType);
                    if (key.hasTranslation()) return key;
                }
                return new DescriptionKey("tag", "c", "spawn_egg");
            }
            final DescriptionKey[] returnedKey = new DescriptionKey[1];
            itemStack.getTags().forEach(itemTagKey -> {
                DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                if (checkMatch(returnedKey, loreKey)) {
                    returnedKey[0] = loreKey;
                }
            });
            // If untagged, check if it is a Block Item and if a Block Tag matches.
            if (returnedKey[0] == null) {
                if ((item instanceof BlockItem blockItem)) {
                    blockItem.getBlock().defaultBlockState().getTags().forEach(itemTagKey -> {
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
            state.getTags().forEach(itemTagKey -> {
                DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                if (checkMatch(returnedKey, loreKey)) {
                    returnedKey[0] = loreKey;
                }
            });
            return returnedKey[0];
        } else if (object instanceof Entity entity) {
            final DescriptionKey[] returnedKey = new DescriptionKey[1];
            entity.getType().builtInRegistryHolder().tags().forEach(itemTagKey -> {
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

    private static void addSafe(ArrayList<Component> tags, DescriptionKey newAdd) {
        Component newText;
        if (Screen.hasAltDown())
            newText = newAdd.toText();
        else {
            newText = Component.literal(newAdd.toString());
        }
        if (!tags.contains(newText))
            tags.add(newText);
    }

    /**
     * Check an object for potential keys. This includes all of its block/item/entity tags,
     * as well as its direct match and any implemented custom component data.
     */
    public static List<Component> findAllPotentialKeys(Object object) {
        ArrayList<Component> tags = new ArrayList<>(); // Create an ArrayList object
        // If object is an item, check for Item Tags
        if (Objects.requireNonNull(object) instanceof ItemStack itemStack) {
            addSafe(tags, findItemLoreKey(itemStack));
            addSafe(tags, getDescriptionKey(itemStack));
            addSafe(tags, getModdedNameMatch(itemStack));
            final Item item = itemStack.getItem();
            //Temporary - Spawn Eggs do not yet have a tag.
            if (item instanceof SpawnEggItem) {
                addSafe(tags, new DescriptionKey("tag", "c", "spawn_egg"));
            }
            itemStack.getTags().forEach(itemTagKey -> {
                DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                addSafe(tags, loreKey);
            });
            // If untagged, check if it is a Block Item and if a Block Tag matches.
            if ((item instanceof BlockItem blockItem)) {
                blockItem.getBlock().defaultBlockState().getTags().forEach(itemTagKey -> {
                    DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                    addSafe(tags, loreKey);
                });
            }
            //If object is a blockstate, check it for Block tags
        } else if (object instanceof BlockState state) {
            state.getTags().forEach(itemTagKey -> {
                DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                addSafe(tags, loreKey);
            });
        } else if (object instanceof Entity entity) {
            entity.getType().builtInRegistryHolder().tags().forEach(itemTagKey -> {
                DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                addSafe(tags, loreKey);
            });
        } else {
            tags.add(Component.empty());
        }
        return tags;
    }

    /**
     * Convert a TagKey into a translation key.
     */
    private static DescriptionKey tagKeyToGenericKey(TagKey<?> key) {
        return new DescriptionKey("tag", key.location());
    }

    public static DescriptionKey getGenericKey(Object object) {
        if (!ModClient.CONFIG.developerOptions.disableTagDescriptions.value()) {
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
