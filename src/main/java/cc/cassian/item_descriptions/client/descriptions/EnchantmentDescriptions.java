package cc.cassian.item_descriptions.client.descriptions;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import cc.cassian.item_descriptions.client.helpers.ModStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnchantmentDescriptions {
    /**
     * Check if another Enchantment Descriptions is installed and our descriptions should be disabled.
     */
    public static boolean useInternalEnchantmentDescriptions() {
        if (ModClient.CONFIG.developerOptions.forceEnableEnchantmentDescriptions.value())
            return true;
        else return !(ModHelpers.isLoaded("idwtialsimmoedm") || ModHelpers.isLoaded("enchdesc"));
    }

    public static boolean createEnchantmentDescription(ItemStack stack, List<Component> lines) {
        boolean descriptionFound = false;
        if (ModClient.CONFIG.enchantmentDescriptions.enable.value() && showEnchantmentDescriptions()) {
            if (ModClient.CONFIG.enchantmentDescriptions.onlyShowOnBooks.value() && !stack.getItem().equals(Items.ENCHANTED_BOOK))
                return false;
            final var enchantments = new HashSet<>(getEnchantments(stack).keySet());
            if (enchantments.isEmpty()) return false;
            for (var enchantmentEntry : enchantments) {
                var enchantment = enchantmentEntry.value();
                for (int i = 0; i < lines.size(); i++) {
                    if (!lines.get(i).getContents().equals(enchantment.description().getContents())) continue;
                    ComponentContents description = lines.get(i).getContents();
                    if (description instanceof TranslatableContents translatableTextContent) {
                        var descriptionKey = new DescriptionKey(translatableTextContent.getKey());
                        lines.add(i + 1, descriptionKey.toText().setStyle(ModStyle.ENCHANTMENT_DESCRIPTIONS));
                    }
                }
            }
        }
        return descriptionFound;
    }

    /**
	 * Check for enchantments on an ItemStack. `getEnchantmentsForCrafting` is not used directly to allow for modded items with
	 * stored enchantments to be correctly displayed.
	 */
	private static ItemEnchantments getEnchantments(ItemStack stack) {
        if (stack.has(DataComponents.STORED_ENCHANTMENTS)) {
            return stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
        } else if (stack.has(DataComponents.ENCHANTMENTS)) {
            return stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        }
		return EnchantmentHelper.getEnchantmentsForCrafting(stack);
	}

    public static void fixEnchantmentDescription(ItemStack stack, List<Component> lines) {
        if (ModClient.CONFIG.enchantmentDescriptions.enable.value() && useInternalEnchantmentDescriptions()) {
            if (ModClient.CONFIG.enchantmentDescriptions.onlyShowOnBooks.value() && !stack.getItem().equals(Items.ENCHANTED_BOOK))
                return;

            final var enchantments = new HashSet<>(getEnchantments(stack).keySet());

            if (enchantments.isEmpty())
                return;

            for (int i = 0; i < lines.size(); ++i) {
                if (isEnchantmentDescription(lines.get(i), enchantments)) {
                    // Create the tooltip.
                    var newLines = ModHelpers.createTooltip(stack.getDisplayName(), lines.get(i), ModHelpers.useInternalWrapper());
                    if (newLines.isEmpty())
                        continue;
                    // To avoid warnings, we don't remove from lines and instead modify the initial line to the first line.
                    lines.set(i, newLines.getFirst());
                    // Add the remaining lines.
                    if (newLines.size() > 1) {
                        lines.addAll(i + 1, newLines.subList(1, newLines.size()));
                    }
                }
            }
        }
    }

    private static boolean isEnchantmentDescription(Component text, Set<Holder<Enchantment>> enchantments) {
        // If the Minecraft world is null, we cannot check if this is an enchantment key.
        if (Minecraft.getInstance().level == null)
            return false;
        return ModHelpers.checkTranslatableText(text, content -> {
            // Split the lang key of this translatable content.
            String[] split = content.getKey().split("\\.");
            // The split key array should always be at least 3 in length, so return false if that's not the case.
            if (split.length < 3)
                return false;
            String namespace = split[1];
            String path = split[2];
            // Check whether the translation exists, and if the key is either an enchantment.*.*.description/desc or lore.*.* key.
            if (ModHelpers.hasTranslation(content.getKey()) && (split.length == 4 && split[0].equals("enchantment") && (split[3].equals("description") || split[3].equals("desc")) || split.length == 3 && split[0].equals("lore"))) {
                // Whether the namespace and path maps to an enchantment on this item. If so, return true.
                return enchantments.stream().anyMatch(entry -> (entry).is(ModHelpers.of(namespace, path)));
            }
            return false;
        });
    }

    public static void addEnchantmentDescription(List<Component> components, Holder<Enchantment> enchant) {
        if (EnchantmentDescriptions.showEnchantmentDescriptions() && ModClient.CONFIG.enchantmentDescriptions.enchantingTable.value()) {
            Component name = Enchantment.getFullname(enchant, 1);
            if (name.getContents() instanceof TranslatableContents content) {
                List<Component> tooltip = ModHelpers.createTooltip(name, new DescriptionKey(content.getKey()).toText().setStyle(ModStyle.ENCHANTMENT_DESCRIPTIONS), true);
                components.addAll(tooltip);
            }
        }
    }

    /**
     * Check if enchantment descriptions should be shown based off configuration.
     */
    public static boolean showEnchantmentDescriptions() {
        return ModClient.CONFIG.enchantmentDescriptions.enable.value() && useInternalEnchantmentDescriptions() && (ModHelpers.tooltipKeyPressed() || ModClient.CONFIG.enchantmentDescriptions.displayAlways.value());
    }

    public static @NotNull DescriptionKey getDescriptionKey(Enchantment enchantment) {
        return getEnchantmentDescriptionKey(enchantment);
    }

    public static DescriptionKey getEnchantmentDescriptionKey(Enchantment enchantment) {
        return enchantment.description().getContents() instanceof TranslatableContents translatable ? new DescriptionKey(translatable.getKey()) : DescriptionKey.empty();
    }
}
