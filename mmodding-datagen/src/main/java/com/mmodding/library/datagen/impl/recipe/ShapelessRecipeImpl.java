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

	final ShapelessRecipeBuilder factory;

	public ShapelessRecipeImpl(ItemLike item, int count, RecipeCategory category) {
		this.factory = new ShapelessRecipeBuilder(category, item, count).unlockedBy(RecipeProvider.getHasName(item), RecipeProvider.has(item));
	}

	@Override
	public void with(ItemLike... items) {
		for (ItemLike item : items) {
			this.factory.requires(item);
		}
	}

	@Override
	public void with(Ingredient... ingredients) {
		for (Ingredient ingredient : ingredients) {
			this.factory.requires(ingredient);
		}
	}
}
