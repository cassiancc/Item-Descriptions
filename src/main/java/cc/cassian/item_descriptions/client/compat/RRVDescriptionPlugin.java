package cc.cassian.item_descriptions.client.compat;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.descriptions.EffectDescriptions;
import cc.cassian.item_descriptions.client.descriptions.EnchantmentDescriptions;
import cc.cassian.rrv.api.CommonTags;
import cc.cassian.rrv.api.ReliableRecipeViewerClientPlugin;
import cc.cassian.rrv.api.recipe.ItemView;
import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.client.recipe.ClientRecipeCache;
import cc.cassian.rrv.common.builtin.info.InfoClientRecipe;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.ArrayList;
import java.util.List;

import static cc.cassian.item_descriptions.client.descriptions.ItemDescriptions.findLoreKey;

public class RRVDescriptionPlugin implements ReliableRecipeViewerClientPlugin {
	@Override
	public void onIntegrationInitialize() {
		ItemView.addClientRecipeProvider(recipeList -> {
			if (ModClient.CONFIG.addToRecipeViewers.value()) {
				List<ItemStack> results = new ArrayList<>();

				BuiltInRegistries.ITEM.forEach(item -> {
					results.add(new ItemStack(item));
					results.addAll(ClientRecipeCache.INSTANCE.getStackSensitives(item).stream().map(ItemView.StackSensitive::stack).toList());
				});

				results.forEach(stack -> {
					//Create and add tooltip.
					DescriptionKey descriptionKey = findLoreKey(stack);
					if (descriptionKey.hasTranslation() && !descriptionKey.hasEmptyTranslation()) {
						MutableComponent tooltip = descriptionKey.toText();
						Identifier itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
						recipeList.add(new InfoClientRecipe(Identifier.fromNamespaceAndPath(ModClient.MOD_ID, itemId.getNamespace() + "/" + itemId.getPath()), SlotContent.of(stack), tooltip));
					}
				});
			}

			if (ModClient.CONFIG.enchantmentDescriptions.addToRecipeViewers.value()) {
				Registry<Enchantment> enchantmentRegistry = Minecraft.getInstance().level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
				enchantmentRegistry.entrySet().forEach((entry) -> {
					var key = entry.getKey();
					var enchantment = entry.getValue();
					for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++) {
						var enchantmentHolder = enchantmentRegistry.wrapAsHolder(enchantment);
						if (!enchantmentHolder.is(CommonTags.EXCLUDED_ENCHANTMENTS) && !ItemView.getExcludedEnchantments().contains(key)) {
							ItemStack enchantedBook = EnchantmentHelper.createBook(new EnchantmentInstance(enchantmentHolder, i));
							var itemId = enchantmentHolder.unwrapKey().orElseThrow().identifier();
							recipeList.add(new InfoClientRecipe(Identifier.fromNamespaceAndPath(ModClient.MOD_ID, itemId.getNamespace() + "/" + itemId.getPath()  + "_" + i), SlotContent.of(enchantedBook), EnchantmentDescriptions.getDescriptionKey(enchantment).toText()));
						}
					}
				});
			}

			if (ModClient.CONFIG.effectDescriptions.addToRecipeViewers.value()) {
				Registry<Potion> potionRegistry = Minecraft.getInstance().level.registryAccess().lookupOrThrow(Registries.POTION);
				potionRegistry.forEach(potion -> {
					var potionHolder = potionRegistry.wrapAsHolder(potion);

					if (!ItemView.isExcludedPotion(potionHolder)) {
						for (MobEffectInstance effect : potion.getEffects()) {
							addPotionRecipe(PotionContents.createItemStack(Items.POTION, potionHolder), effect, recipeList);
							addPotionRecipe(PotionContents.createItemStack(Items.SPLASH_POTION, potionHolder), effect, recipeList);
							addPotionRecipe(PotionContents.createItemStack(Items.LINGERING_POTION, potionHolder), effect, recipeList);
							ItemStack tipped = new ItemStack(Items.TIPPED_ARROW);
							tipped.set(DataComponents.POTION_CONTENTS, new PotionContents(potionHolder));
							addPotionRecipe(tipped, effect, recipeList);
						}
					}
				});
			}
		});
	}

	private void addPotionRecipe(ItemStack itemStack, MobEffectInstance effect, List<ReliableClientRecipe> recipeList) {
		var effectId = BuiltInRegistries.MOB_EFFECT.getKey(effect.getEffect().value());
		var itemId = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
		recipeList.add(new InfoClientRecipe(Identifier.fromNamespaceAndPath(ModClient.MOD_ID, effectId.getNamespace() + "/" + effectId.getPath()  + "_" + itemId.getPath()), SlotContent.of(itemStack), EffectDescriptions.getDescriptionKey(effect).toText()));
	}
}
