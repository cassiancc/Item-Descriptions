package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.effect.MobEffect;
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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class TagHelpers {

    private static boolean checkMatch(DescriptionKey[] currentKey, DescriptionKey newKey) {
        return newKey.hasTranslation() && (currentKey[0] == null || DescriptionKey.isMorePrecise(currentKey[0], newKey));
    }

    public static DescriptionKey checkGenericTagList(ItemStack stack) {
        if (ModClient.CONFIG.developerOptions.disableTagDescriptions.value()) return DescriptionKey.empty();
        final Item item = stack.getItem();
        //Temporary - Spawn Eggs do not yet have a tag.
        if (item instanceof SpawnEggItem spawnEggItem) {
            if (ModClient.CONFIG.spawnEggsShowEntity.value()) {
                EntityType<?> entityType = spawnEggItem.getType(
                        //? if >1.21.8 {
                        /*stack
                         *///?} else if >1.21.1 {
                        Minecraft.getInstance().level.registryAccess(), stack
                        //?} else if >1.20.1 {
                        /*stack
                         *///?} else {
                        /*stack.getTag()
                         *///?}
                );
                var key = ModHelpers.getDescriptionKey(entityType);
                if (key.hasTranslation()) return key;
            }
            return new DescriptionKey("tag", "c", "spawn_egg");
        }
        final DescriptionKey[] returnedKey = new DescriptionKey[1];
        stack.getTags().forEach(itemTagKey -> {
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
        return Objects.requireNonNullElse(returnedKey[0], DescriptionKey.empty());
    }

    public static DescriptionKey checkGenericTagList(BlockState state) {
        return checkGenericTagList(state.getBlock());
    }

    public static DescriptionKey checkGenericTagList(Block block) {
        final DescriptionKey[] returnedKey = new DescriptionKey[1];
        block.builtInRegistryHolder().tags().forEach(tagKey -> {
            DescriptionKey loreKey = tagKeyToGenericKey(tagKey);
            if (checkMatch(returnedKey, loreKey)) {
                returnedKey[0] = loreKey;
            }
        });
        return Objects.requireNonNullElse(returnedKey[0], DescriptionKey.empty());
    }

    public static DescriptionKey checkGenericTagList(Entity entity) {
        return checkGenericTagList(entity.getType());
    }

    public static DescriptionKey checkGenericTagList(EntityType<?> type) {
        final DescriptionKey[] returnedKey = new DescriptionKey[1];
        type.builtInRegistryHolder().tags().forEach(tagKey -> {
            DescriptionKey loreKey = tagKeyToGenericKey(tagKey);
            if (checkMatch(returnedKey, loreKey)) {
                returnedKey[0] = loreKey;
            }
        });
        return Objects.requireNonNullElse(returnedKey[0], DescriptionKey.empty());
    }

    private static void addSafe(ArrayList<Component> tags, DescriptionKey newAdd) {
        Component newText;
        if (
        //? if >1.21.8 {
        /*Minecraft.getInstance()
        *///?} else {
        Screen
        //?}
        .hasAltDown())
            newText = newAdd.toText();
        else {
            newText = Component.literal(newAdd.toString());
        }
        if (!tags.contains(newText))
            tags.add(newText);
    }

    public static List<Component> findAllPotentialKeys(ItemStack itemStack) {
        ArrayList<Component> tags = new ArrayList<>(); // Create an ArrayList object
        addSafe(tags, ModHelpers.findLoreKey(itemStack));
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
        return tags;
    }

    public static List<Component> findAllPotentialKeys(BlockState state) {
        return findAllPotentialKeys(state.getBlock());
    }

    public static List<Component> findAllPotentialKeys(Block block) {
        ArrayList<Component> tags = new ArrayList<>(); // Create an ArrayList object
        addSafe(tags, getDescriptionKey(block));
        addSafe(tags, findLoreKey(block));
        block.builtInRegistryHolder().tags().forEach(itemTagKey -> {
            DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
            addSafe(tags, loreKey);
        });
        return tags;
    }

    public static List<Component> findAllPotentialKeys(Entity entity) {
        return findAllPotentialKeys(entity.getType());
    }

    public static List<Component> findAllPotentialKeys(EntityType<?> type) {
        ArrayList<Component> tags = new ArrayList<>(); // Create an ArrayList object
        addSafe(tags, getDescriptionKey(type));
        addSafe(tags, findLoreKey(type));
        type.builtInRegistryHolder().tags().forEach(itemTagKey -> {
            DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
            addSafe(tags, loreKey);
        });
        return tags;
    }

    public static List<Component> findAllPotentialKeys(Enchantment enchantment) {
        ArrayList<Component> tags = new ArrayList<>(); // Create an ArrayList object
        addSafe(tags, getDescriptionKey(enchantment));
        return tags;
    }

    public static List<Component> findAllPotentialKeys(MobEffect mobEffect) {
        ArrayList<Component> tags = new ArrayList<>(); // Create an ArrayList object
        addSafe(tags, getDescriptionKey(mobEffect));
        return tags;
    }

    /**
     * Convert a TagKey into a translation key.
     */
    private static DescriptionKey tagKeyToGenericKey(TagKey<?> key) {
        return new DescriptionKey("tag", key.location());
    }
}
