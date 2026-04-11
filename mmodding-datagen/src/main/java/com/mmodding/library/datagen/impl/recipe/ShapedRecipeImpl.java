package com.mmodding.library.datagen.impl.recipe;

import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ShapedRecipeImpl implements RecipeHelper.ShapedRecipe {

	final ShapedRecipeBuilder factory;

	public ShapedRecipeImpl(ItemLike item, int count, RecipeCategory category) {
		this.factory = new ShapedRecipeBuilder(category, item, count).unlockedBy(RecipeProvider.getHasName(item), RecipeProvider.has(item));
	}

	@Override
	public void key(char key, Ingredient ingredient) {
		this.factory.define(key, ingredient);
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
