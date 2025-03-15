package com.mmodding.library.datagen.impl.recipe;

import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ShapedRecipeImpl implements RecipeHelper.ShapedRecipe {

	final ShapedRecipeJsonBuilder factory;

	public ShapedRecipeImpl(ItemConvertible item, int count, RecipeCategory category) {
		this.factory = new ShapedRecipeJsonBuilder(category, item, count).criterion(RecipeProvider.hasItem(item), RecipeProvider.conditionsFromItem(item));
	}

	@Override
	public void key(char key, Ingredient ingredient) {
		this.factory.input(key, ingredient);
	}

	@Override
	public void pattern(String firstLine, String secondLine, String thirdLine) {
		this.factory.pattern(firstLine).pattern(secondLine).pattern(thirdLine);
	}

	@Override
	public void pattern(String firstLine, String secondLine) {
		this.factory.pattern(firstLine).pattern(secondLine);
	}
}
