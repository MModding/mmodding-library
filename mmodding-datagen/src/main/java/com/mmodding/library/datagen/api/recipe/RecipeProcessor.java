package com.mmodding.library.datagen.api.recipe;

import net.minecraft.world.level.ItemLike;

@FunctionalInterface
public interface RecipeProcessor<T extends ItemLike> {

	void process(RecipeHelper helper, T element);
}
