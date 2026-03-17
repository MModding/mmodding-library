package com.mmodding.library.datagen.api.recipe;

import net.minecraft.item.ItemConvertible;

public interface RecipeProcessor<T extends ItemConvertible> {

	void process(RecipeHelper helper, T element);
}
