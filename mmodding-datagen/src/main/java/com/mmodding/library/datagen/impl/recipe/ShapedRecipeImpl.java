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

	public ShapedRecipeImpl(RecipeProvider provider, ItemLike item, int count, RecipeCategory category) {
		this.factory = provider.shaped(category, item, count).unlockedBy(RecipeProvider.getHasName(item), provider.has(item));
	}

	@Override
	public RecipeHelper.ShapedRecipe key(char key, Ingredient ingredient) {
		this.factory.define(key, ingredient);
		return this;
	}

	@Override
	public RecipeHelper.ShapedRecipe pattern(String firstLine, String secondLine, String thirdLine) {
		this.factory.pattern(firstLine).pattern(secondLine).pattern(thirdLine);
		return this;
	}

	@Override
	public RecipeHelper.ShapedRecipe pattern(String firstLine, String secondLine) {
		this.factory.pattern(firstLine).pattern(secondLine);
		return this;
	}
}
