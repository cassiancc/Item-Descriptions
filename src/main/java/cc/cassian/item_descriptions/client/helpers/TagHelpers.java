package cc.cassian.item_descriptions.client.helpers;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.descriptions.*;
import net.minecraft.client.Minecraft;
//? if <1.21.9 {
/*import net.minecraft.client.gui.screens.Screen;
*///?}
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.tags.TagKey;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
                EntityType<?> entityType = SpawnEggItem.getType(stack);
                if (entityType != null) {
                    var key = EntityDescriptions.getDescriptionKey(entityType);
                    if (key.hasTranslation()) return key;
                }
            }
            return new DescriptionKey("tag", "c", "spawn_egg");
        }
        final DescriptionKey[] returnedKey = new DescriptionKey[1];
        getTags(stack).forEach(itemTagKey -> {
            DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
            if (checkMatch(returnedKey, loreKey)) {
                returnedKey[0] = loreKey;
            }
        });
        // If untagged, check if it is a Block Item and if a Block Tag matches.
        if (returnedKey[0] == null) {
            if ((item instanceof BlockItem blockItem)) {
                if (blockItem.getBlock() != null) {
                    getTags(blockItem.getBlock().defaultBlockState()).forEach(itemTagKey -> {
                        DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                        if (checkMatch(returnedKey, loreKey)) {
                            returnedKey[0] = loreKey;
                        }
                    });
                } else {
                    ModClient.LOGGER.warn("BlockItem for {} is null!", blockItem);
                }
            }
        }
        return Objects.requireNonNullElse(returnedKey[0], DescriptionKey.empty());
    }

    public static DescriptionKey checkGenericTagList(BlockState state) {
        final DescriptionKey[] returnedKey = new DescriptionKey[1];
        getTags(state).forEach(tagKey -> {
            DescriptionKey loreKey = tagKeyToGenericKey(tagKey);
            if (checkMatch(returnedKey, loreKey)) {
                returnedKey[0] = loreKey;
            }
        });
        return Objects.requireNonNullElse(returnedKey[0], DescriptionKey.empty());
    }

    public static DescriptionKey checkGenericTagList(Block block) {
        return checkGenericTagList(block.defaultBlockState());
    }

    public static DescriptionKey checkGenericTagList(Entity entity) {
        return checkGenericTagList(entity.getType());
    }

    public static DescriptionKey checkGenericTagList(EntityType<?> type) {
        final DescriptionKey[] returnedKey = new DescriptionKey[1];
        getTags(type).forEach(tagKey -> {
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
        Minecraft.getInstance()
        //?} else {
        /*Screen
        *///?}
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
        addSafe(tags, ItemDescriptions.findLoreKey(itemStack));
        addSafe(tags, ItemDescriptions.getDescriptionKey(itemStack));
        addSafe(tags, ItemDescriptions.getModdedNameMatch(itemStack));
        final Item item = itemStack.getItem();
        //Temporary - Spawn Eggs do not yet have a tag.
        if (item instanceof SpawnEggItem) {
            addSafe(tags, new DescriptionKey("tag", "c", "spawn_egg"));
        }
        getTags(itemStack).forEach(itemTagKey -> {
            DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
            addSafe(tags, loreKey);
        });
        // If untagged, check if it is a Block Item and if a Block Tag matches.
        if ((item instanceof BlockItem blockItem)) {
            if (blockItem.getBlock() != null) {
                getTags(blockItem.getBlock().defaultBlockState()).forEach(itemTagKey -> {
                    DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
                    addSafe(tags, loreKey);
                });
            } else {
                ModClient.LOGGER.warn("BlockItem for {} is null!", blockItem);
            }
        }
        return tags;
    }

    public static List<Component> findAllPotentialKeys(BlockState state) {
        ArrayList<Component> tags = new ArrayList<>(); // Create an ArrayList object
        addSafe(tags, BlockDescriptions.getDescriptionKey(state));
        addSafe(tags, BlockDescriptions.findLoreKey(state));
        getTags(state).forEach(itemTagKey -> {
            DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
            addSafe(tags, loreKey);
        });
        return tags;
    }

    public static List<Component> findAllPotentialKeys(Block block) {
        return findAllPotentialKeys(block.defaultBlockState());
    }

    public static List<Component> findAllPotentialKeys(Entity entity) {
        return findAllPotentialKeys(entity.getType());
    }

    public static List<Component> findAllPotentialKeys(EntityType<?> type) {
        ArrayList<Component> tags = new ArrayList<>(); // Create an ArrayList object
        addSafe(tags, EntityDescriptions.getDescriptionKey(type));
        addSafe(tags, EntityDescriptions.findLoreKey(type));
        getTags(type).forEach(itemTagKey -> {
            DescriptionKey loreKey = tagKeyToGenericKey(itemTagKey);
            addSafe(tags, loreKey);
        });
        return tags;
    }

    public static List<Component> findAllPotentialKeys(Enchantment enchantment) {
        ArrayList<Component> tags = new ArrayList<>(); // Create an ArrayList object
        addSafe(tags, EnchantmentDescriptions.getDescriptionKey(enchantment));
        return tags;
    }

    public static List<Component> findAllPotentialKeys(MobEffect mobEffect) {
        ArrayList<Component> tags = new ArrayList<>(); // Create an ArrayList object
        addSafe(tags, EffectDescriptions.getDescriptionKey(mobEffect));
        return tags;
    }

    /**
     * Convert a TagKey into a translation key.
     */
    private static DescriptionKey tagKeyToGenericKey(TagKey<?> key) {
        return new DescriptionKey("tag", key.location());
    }

    private static Stream<TagKey<Item>> getTags(ItemStack stack) {
        //? if >26 {
        return stack.tags();
        //?} else {
        /*return stack.getTags();
        *///?}
    }

    private static Stream<TagKey<Block>> getTags(BlockState state) {
        //? if >26 {
        return state.tags();
        //?} else {
        /*return state.getTags();
        *///?}
    }

    private static Stream<TagKey<EntityType<?>>> getTags(EntityType<?> type) {
        return type.builtInRegistryHolder().tags();
    }
}
