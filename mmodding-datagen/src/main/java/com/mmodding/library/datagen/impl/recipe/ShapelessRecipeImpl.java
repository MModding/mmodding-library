package com.mmodding.library.datagen.impl.recipe;

import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ShapelessRecipeImpl implements RecipeHelper.ShapelessRecipe {

	private final RecipeProvider provider;
	final ShapelessRecipeBuilder factory;

	public ShapelessRecipeImpl(RecipeProvider provider, ItemLike item, int count, RecipeCategory category, ItemLike... unlockers) {
		this.provider = provider;
		this.factory = this.provider.shapeless(category, item, count);
		for (ItemLike unlocker : unlockers) {
			this.factory.unlockedBy(RecipeProvider.getHasName(unlocker), this.provider.has(unlocker));
		}
	}

	@Override
	public RecipeHelper.ShapelessRecipe with(ItemLike... items) {
		for (ItemLike item : items) {
			this.factory.unlockedBy(RecipeProvider.getHasName(item), this.provider.has(item)).requires(item);
		}
		return this;
	}

	@Override
	public RecipeHelper.ShapelessRecipe with(Ingredient... ingredients) {
		for (Ingredient ingredient : ingredients) {
			this.factory.requires(ingredient);
		}
		return this;
	}
}
