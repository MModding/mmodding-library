package com.mmodding.library.datagen.impl.recipe;

import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeCategory;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ShapelessRecipeImpl implements RecipeHelper.ShapelessRecipe {

	final ShapelessRecipeJsonFactory factory;

	public ShapelessRecipeImpl(ItemConvertible item, int count, RecipeCategory category) {
		this.factory = new ShapelessRecipeJsonFactory(category, item, count).criterion(RecipesProvider.hasItem(item), RecipesProvider.conditionsFromItem(item));
	}

	@Override
	public void with(ItemConvertible... items) {
		for (ItemConvertible item : items) {
			this.factory.ingredient(item);
		}
	}

	@Override
	public void with(Ingredient... ingredients) {
		for (Ingredient ingredient : ingredients) {
			this.factory.ingredient(ingredient);
		}
	}
}
