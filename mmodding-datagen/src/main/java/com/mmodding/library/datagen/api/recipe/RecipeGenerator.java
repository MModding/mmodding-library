package com.mmodding.library.datagen.api.recipe;

import net.minecraft.world.level.ItemLike;

public interface RecipeGenerator {

	/**
	 * Creates a {@link RecipeHelper} for a target.
	 * @param item the target
	 * @return the helper
	 */
	RecipeHelper forItem(ItemLike item);
}
