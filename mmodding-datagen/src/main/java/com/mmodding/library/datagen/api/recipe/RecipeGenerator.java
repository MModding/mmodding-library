package com.mmodding.library.datagen.api.recipe;

import net.minecraft.world.level.ItemLike;

public interface RecipeGenerator {

	/**
	 * Creates a {@link RecipeHelper} for a target.
	 * @param item the target
	 * @return the helper
	 */
	RecipeHelper forItem(ItemLike item);

	/**
	 * Creates a {@link RecipeHelper} for a target, and specifies a suffix.
	 * @param item the target
	 * @param suffix the suffix
	 * @return the helper
	 */
	RecipeHelper forItem(ItemLike item, String suffix);
}
