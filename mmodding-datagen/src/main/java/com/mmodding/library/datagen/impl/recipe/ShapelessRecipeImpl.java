package com.mmodding.library.datagen.impl.recipe;

import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ShapelessRecipeImpl implements RecipeHelper.ShapelessRecipe {

	final ShapelessRecipeJsonBuilder factory;

	public ShapelessRecipeImpl(ItemConvertible item, int count, RecipeCategory category) {
		this.factory = new ShapelessRecipeJsonBuilder(category, item, count).criterion(RecipeProvider.hasItem(item), RecipeProvider.conditionsFromItem(item));
	}

	@Override
	public void with(ItemConvertible... items) {
		for (ItemConvertible item : items) {
			this.factory.input(item);
		}
	}

	@Override
	public void with(Ingredient... ingredients) {
		for (Ingredient ingredient : ingredients) {
			this.factory.input(ingredient);
		}
	}
}
