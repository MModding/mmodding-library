package com.mmodding.library.datagen.impl.recipe;

import com.mmodding.library.datagen.api.recipe.RecipeGenerator;
import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.ItemLike;

public class RecipeGeneratorImpl implements RecipeGenerator {

	private final RecipeProvider provider;
	private final RecipeOutput output;

	public RecipeGeneratorImpl(RecipeProvider provider, RecipeOutput output) {
		this.provider = provider;
		this.output = output;
	}

	@Override
	public RecipeHelper forItem(ItemLike item) {
		return new RecipeHelperImpl(this.provider, this.output, item);
	}
}
