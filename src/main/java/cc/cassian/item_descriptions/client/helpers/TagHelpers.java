package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.DescriptionKey;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class TagHelpers {

    private static boolean checkMatch(DescriptionKey[] currentKey, DescriptionKey newKey) {
        return newKey.hasTranslation() && (currentKey[0] == null || DescriptionKey.isMorePrecise(currentKey[0], newKey));
    }

    public static @NotNull DescriptionKey checkGenericTagList(ItemStack itemStack) {
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
        return Objects.requireNonNullElse(returnedKey[0], DescriptionKey.empty());
    }

    public static @NotNull DescriptionKey checkGenericTagList(BlockState state) {
        final DescriptionKey[] returnedKey = new DescriptionKey[1];
        state.streamTags().forEach(itemTagKey -> {
            DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
            if (checkMatch(returnedKey, loreKey)) {
                returnedKey[0] = loreKey;
            }
        });
        return returnedKey[0];
    }

    public static @NotNull DescriptionKey checkGenericTagList(EntityType<?> entityType) {
        final DescriptionKey[] returnedKey = new DescriptionKey[1];
        entityType.getRegistryEntry().streamTags().forEach(itemTagKey -> {
            DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
            if (checkMatch(returnedKey, loreKey)) {
                returnedKey[0] = loreKey;
            }
        });
        return returnedKey[0];
        //If no tag key matches, return empty so a string match can be found.
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
            addSafe(tags, getDescriptionKey(itemStack));
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
            addSafe(tags, getDescriptionKey(state));
        } else if (object instanceof EntityType<?> type) {
            type.getRegistryEntry().streamTags().forEach(itemTagKey -> {
                DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                addSafe(tags, loreKey);
            });
            addSafe(tags, getDescriptionKey(type));
        } else if (object instanceof Entity entity) {
            entity.getType().getRegistryEntry().streamTags().forEach(itemTagKey -> {
                DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                addSafe(tags, loreKey);
            });
            addSafe(tags, getDescriptionKey(entity));
        } else if (object instanceof StatusEffect effect) {
            addSafe(tags, new DescriptionKey(effect.getTranslationKey()));
        } else if (object instanceof Enchantment enchantment) {
            addSafe(tags, getEnchantmentDescriptionKey(enchantment));
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
}
