package cc.cassian.item_descriptions.client.descriptions;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.Platform;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import cc.cassian.item_descriptions.client.helpers.ModLists;
import cc.cassian.item_descriptions.client.helpers.ModStyle;
import cc.cassian.item_descriptions.client.helpers.TagHelpers;
import cc.cassian.item_descriptions.client.helpers.compat.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class ItemDescriptions {
    public static LinkedHashMap<Integer, DescriptionKey> cachedDescriptions = new LinkedHashMap<>();

    /**
     * Create an item's lore key based off data from its Item Stack.
     */
    public static DescriptionKey findLoreKey(ItemStack stack) {
        if (cachedDescriptions.containsKey(stack.hashCode())) {
            return cachedDescriptions.get(stack.hashCode());
        }
        // Disable Item Descriptions on Enchanted Books
        if (ModClient.CONFIG.enchantmentDescriptions.onlyEnchantmentDescriptionsOnBooks.value() && stack.is(Items.ENCHANTED_BOOK)) {
            cachedDescriptions.put(stack.hashCode(), DescriptionKey.empty());
            return DescriptionKey.empty();
        }
        //Ensure items from Polymer get the correct key instead of a vanilla one.
        //? if fabric && <26 {
        if (PolymerHelpers.getServerIdentifier(stack) != null) {
            DescriptionKey descriptionKey = new DescriptionKey(PolymerHelpers.getServerIdentifier(stack));
            cachedDescriptions.put(stack.hashCode(), descriptionKey);
            return descriptionKey;
        }
        //?}
        //Ensure items with Custom Models get a custom key instead of a vanilla one.
        //? if >1.21.2 {
        if (hasComponent(stack, DataComponents.ITEM_MODEL)) {
            Identifier data = Objects.requireNonNull(stack.getComponents().get(DataComponents.ITEM_MODEL));
            DescriptionKey modelKey = new DescriptionKey(data);
            if (modelKey.hasTranslation()) {
                cachedDescriptions.put(stack.hashCode(), modelKey);
                return modelKey;
            }
        } else
            //?}
            //Ensure items with Custom Model Data get a custom key instead of a vanilla one.
            if (hasComponent(stack, DataComponents.CUSTOM_MODEL_DATA)) {
                var data = Objects.requireNonNull(stack.getComponents().get(DataComponents.CUSTOM_MODEL_DATA));
                //? if <1.21.4 {
                /*var dataValue = data.value();
                 *///?} else {
                var dataValue = data.getString(0);
                //?}
                DescriptionKey key = getDescriptionKey(stack);
                DescriptionKey modelKey = key.hasTranslation() ? key : TagHelpers.checkGenericTagList(stack);
                modelKey.setSuffix(".custommodeldata." + dataValue);
                if (modelKey.hasTranslation()) {
                    cachedDescriptions.put(stack.hashCode(), modelKey);
                    return modelKey;
                }
            }
            //Ensure Paintings get a custom key instead of a vanilla one.
            else if (stack.is(Items.PAINTING) && hasComponent(stack, DataComponents.ENTITY_DATA)) {
                var data = Objects.requireNonNull(stack.getComponents().get(DataComponents.ENTITY_DATA));
                //? if >1.21.8 {
                var variant = ModHelpers.toTranslationKey(data.copyTagWithoutId().getStringOr("variant", ""));
                //?} else if >=1.21.5 {
                /*var variant = ModHelpers.toTranslationKey(data.copyTag().getString("variant").orElse(""));
                 *///?} else {
                /*var variant = ModHelpers.toTranslationKey(data.copyTag().getString("variant"));
                 *///?}
                var paintingKey = new DescriptionKey("lore", "minecraft", "painting", variant);
                if (paintingKey.hasTranslation() || ModClient.CONFIG.developerOptions.showAllPotentialKeys.value()) {
                    cachedDescriptions.put(stack.hashCode(), paintingKey);
					return paintingKey;
				}
            }
            //Ensure player heads with Profile components get a custom key instead of a vanilla one.
            else if (hasComponent(stack, DataComponents.PROFILE)) {
                DescriptionKey profileKey = getProfile(stack);
                if (profileKey.hasTranslation()) {
                    cachedDescriptions.put(stack.hashCode(), profileKey);
                    return profileKey;
                }
            }
        DescriptionKey name = getModdedNameMatch(stack);
        if (name.hasTranslation()) {
            cachedDescriptions.put(stack.hashCode(), name);
            return name;
        }
        //Find the tooltip translation key for the provided item stack.
        DescriptionKey key = getDescriptionKey(stack);
		if (key.hasTranslation()) {
            DescriptionKey descriptionKey = DescriptionKey.checkLoreKey(key);
            cachedDescriptions.put(stack.hashCode(), descriptionKey);
            return descriptionKey;
		}
        DescriptionKey genericTagKey = DescriptionKey.checkLoreKey(TagHelpers.checkGenericTagList(stack));
        cachedDescriptions.put(stack.hashCode(), genericTagKey);
        return genericTagKey;
	}

    public static DescriptionKey getModdedNameMatch(ItemStack stack) {
        var key = getDescriptionKey(stack);
        key.setSuffix(ModHelpers.toTranslationKey(stack.getItem().getName(stack).getString()));
        return key;
    }

    public static boolean createItemDescription(ItemStack stack, List<Component> lines) {
        if (ModClient.CONFIG.itemDescriptions.value() && showItemDescriptions()) {
            //Create and add tooltip.
            List<Component> tooltip;
            DescriptionKey descriptionKey = findLoreKey(stack);
            if (ModClient.CONFIG.developerOptions.showAllPotentialKeys.value()) {
                tooltip = TagHelpers.findAllPotentialKeys(stack);
            } else if (descriptionKey.hasEmptyTranslation()) {
                return false;
            } else if (descriptionKey.hasTranslation()) {
                tooltip = List.of(descriptionKey.toText());
            } else return false;
            if (showItemDescriptions()) {
                tooltip = tooltip.stream().map(text -> (Component) text.copy().setStyle(ModStyle.ITEM_DESCRIPTIONS)).toList();
                lines.addAll(1, tooltip);
            } else return descriptionKey.hasTranslation();
        }
        return false;
    }

    public static void fixItemDescription(ItemStack stack, List<Component> lines) {
        if (ModClient.CONFIG.itemDescriptions.value() && showItemDescriptions()) {
            //Find and wrap tooltip. Will be disabled if TooltipFix is installed.
            List<Component> tooltip;
            DescriptionKey descriptionKey = findLoreKey(stack);
            if (ModClient.CONFIG.developerOptions.showAllPotentialKeys.value()) {
                tooltip = TagHelpers.findAllPotentialKeys(stack);
            } else {
                tooltip = List.of(descriptionKey.toText());
            }
            for (int i = 0; i < lines.size(); ++i) {
                // Required for lambda comparison.
                int finalI = i;
                // Check if any of the tooltips' content matches the current line's content.
                if (tooltip.stream().anyMatch(text -> text.getContents().equals(lines.get(finalI).getContents()))) {
                    var newLines = ModHelpers.createTooltip(stack.getDisplayName(), lines.get(i), ModHelpers.useInternalWrapper());
                    if (newLines.isEmpty()) {
                        return;
                    }
                    lines.set(i, newLines.getFirst());
                    if (newLines.size() > 1) {
                        lines.addAll(i + 1, newLines.subList(1, newLines.size()));
                    }
                }
            }

        }
    }

    /**
     * Check if an Item Stack has a particular component.
     */
    public static boolean hasComponent(ItemStack stack, DataComponentType<?> type) {
        return stack.getComponents().has(type);
    }

    /**
     * Find a profile name in a Player Head Item Stack.
     */
    public static DescriptionKey getProfile(ItemStack stack) {
        var optionalProfileName = Objects.requireNonNull(Objects.requireNonNull(stack.getComponents().get(DataComponents.PROFILE)).name()).orElse("");
        if (!optionalProfileName.isEmpty()) {
            DescriptionKey key = getDescriptionKey(stack);
            DescriptionKey profileKey = key.hasTranslation() ? key : TagHelpers.checkGenericTagList(stack);
            profileKey.setSuffix("profile." + optionalProfileName);
            if (profileKey.hasTranslation()) {
                return profileKey;
            }
        }
        return DescriptionKey.empty();
    }

    /**
     * Check if item descriptions should be shown based off configuration.
     */
    public static boolean showItemDescriptions() {
        return ModClient.CONFIG.itemDescriptions.value() && (ModHelpers.tooltipKeyPressed() || ModClient.CONFIG.displayAlways.value());
    }

    public static void createDescriptionsFromItemStack(ItemStack stack, Item.TooltipContext context, TooltipFlag type, List<Component> lines) {
        if (ModClient.CONFIG.developerOptions.hideOtherTooltips.value() || ModLists.hidden_items.contains(stack.getItem())) {
            var first = lines.getFirst();
            lines.clear();
            lines.add(first);
        }
        boolean enchant = EnchantmentDescriptions.createEnchantmentDescription(stack, lines);
        boolean effect = EffectDescriptions.checkForEffectDescription(stack);
        if (ModClient.CONFIG.effectDescriptions.onlyShowEffectDescriptions.value() && effect) return;
        boolean item = createItemDescription(stack, lines);
        if (ModClient.CONFIG.hint.enabled.value() && (item || enchant)) {
            ModHelpers.addHint(lines);
        }
        if ((ModHelpers.tooltipKeyPressed() || ModClient.CONFIG.displayAlways.value()) && ModClient.CONFIG.showModName.value()) {
            addModName(stack, lines);
        }
    }

    private static void addModName(ItemStack stack, List<Component> lines) {
        String namespace = Platform.INSTANCE.getModName(stack);
        MutableComponent text = Component.literal(namespace);
        lines.add(text.setStyle(ModStyle.MOD_NAME));
    }

    public static String getModName(ItemStack stack) {
        return Platform.INSTANCE.getModName(stack);
    }

    public static void fixItemStackDescriptionTooltip(ItemStack stack, Item.TooltipContext context, TooltipFlag type, List<Component> lines) {
        if (!ModHelpers.useInternalWrapper())
            return;
        EnchantmentDescriptions.fixEnchantmentDescription(stack, lines);
        if (ModHelpers.useInternalWrapper())
            fixItemDescription(stack, lines);
    }

    public static @NotNull DescriptionKey getDescriptionKey(ItemStack stack) {
        return getDescriptionKey(stack.getItem());
    }

    public static @NotNull DescriptionKey getDescriptionKey(Item item) {
        return DescriptionKey.ofTranslationKey(item.getDescriptionId());
    }
}
