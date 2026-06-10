package cc.cassian.item_descriptions.client.compat;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.descriptions.EffectDescriptions;
import cc.cassian.item_descriptions.client.descriptions.EnchantmentDescriptions;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.Collections;
import java.util.function.Consumer;

import static cc.cassian.item_descriptions.client.descriptions.ItemDescriptions.findLoreKey;

@EmiEntrypoint
public class EmiDescriptionPlugin implements EmiPlugin {

	@Override
	public void register(EmiRegistry emiRegistry) {
		emiRegistry.addDeferredRecipes(recipeList -> {
			if (ModClient.CONFIG.addToRecipeViewers.value()) {

				BuiltInRegistries.ITEM.forEach(stack -> {
					//Create and add tooltip.
					DescriptionKey descriptionKey = findLoreKey(stack.getDefaultInstance());
					if (descriptionKey.hasTranslation() && !descriptionKey.hasEmptyTranslation()) {
						MutableComponent tooltip = descriptionKey.toText();
						ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack);
						recipeList.accept(new EmiInfoRecipe(Collections.singletonList(EmiStack.of(stack)), Collections.singletonList(tooltip), ResourceLocation.fromNamespaceAndPath(ModClient.MOD_ID, "/"+ itemId.getNamespace() + "/" + itemId.getPath())));
					}
				});
			}

			if (ModClient.CONFIG.enchantmentDescriptions.addToRecipeViewers.value()) {
				Registry<Enchantment> enchantmentRegistry = Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
				enchantmentRegistry.entrySet().forEach((entry) -> {
					var enchantmentId = entry.getKey().location();
					var enchantment = entry.getValue();
					DescriptionKey descriptionKey = EnchantmentDescriptions.getDescriptionKey(enchantment);
					if (descriptionKey.hasTranslation() && !descriptionKey.hasEmptyTranslation()) {
						for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++) {
							var enchantmentHolder = enchantmentRegistry.wrapAsHolder(enchantment);
							ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
							ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
							mutable.set(enchantmentHolder, i);
							enchantedBook.set(DataComponents.STORED_ENCHANTMENTS, mutable.toImmutable());


							recipeList.accept(new EmiInfoRecipe(Collections.singletonList(EmiStack.of(enchantedBook)), Collections.singletonList(descriptionKey.toText()), ResourceLocation.fromNamespaceAndPath(ModClient.MOD_ID,"/"+ enchantmentId.getNamespace() + "/" + enchantmentId.getPath()  + "_" + i)));
						}
					}
				});
			}

			if (ModClient.CONFIG.effectDescriptions.addToRecipeViewers.value()) {
				Registry<Potion> potionRegistry = Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.POTION);
				potionRegistry.forEach(potion -> {
					var potionHolder = potionRegistry.wrapAsHolder(potion);
					for (MobEffectInstance effect : potion.getEffects()) {
						var key = EffectDescriptions.getDescriptionKey(effect);
						if (key.hasTranslation() && !key.hasEmptyTranslation()) {
							var text = key.toText();
							potionHolder.unwrapKey().map(ResourceKey::location).ifPresent(potionId->{
								addPotionRecipe(PotionContents.createItemStack(Items.POTION, potionHolder), effect, recipeList, potionId, text);
								addPotionRecipe(PotionContents.createItemStack(Items.SPLASH_POTION, potionHolder), effect, recipeList, potionId, text);
								addPotionRecipe(PotionContents.createItemStack(Items.LINGERING_POTION, potionHolder), effect, recipeList, potionId, text);
								ItemStack tipped = new ItemStack(Items.TIPPED_ARROW);
								tipped.set(DataComponents.POTION_CONTENTS, new PotionContents(potionHolder));
								addPotionRecipe(tipped, effect, recipeList, potionId, text);
							});
						}
					}
				});
			}
		});
	}

	private void addPotionRecipe(ItemStack itemStack, MobEffectInstance effect, Consumer<EmiRecipe> recipeList, ResourceLocation potionId, MutableComponent text) {
		var effectId = BuiltInRegistries.MOB_EFFECT.getKey(effect.getEffect().value());
		var itemId = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
		recipeList.accept(new EmiInfoRecipe(Collections.singletonList(EmiStack.of(itemStack)), Collections.singletonList(text), ResourceLocation.fromNamespaceAndPath(ModClient.MOD_ID, "/"+effectId.getNamespace() + "/" + effectId.getPath() + "_" + potionId.getPath() + "_" + itemId.getPath())));
	}
}
