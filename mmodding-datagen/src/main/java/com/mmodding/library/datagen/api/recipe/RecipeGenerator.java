package com.mmodding.library.datagen.api.recipe;

import net.minecraft.item.ItemConvertible;

public interface RecipeGenerator {

	/**
	 * Creates a {@link RecipeHelper} for a target.
	 * @param item the target
	 * @return the helper
	 */
	RecipeHelper forItem(ItemConvertible item);
}
