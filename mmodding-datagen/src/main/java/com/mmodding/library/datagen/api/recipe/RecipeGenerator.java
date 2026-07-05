package com.mmodding.library.datagen.api.recipe;

import com.mmodding.library.core.api.registry.RegistryLooker;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public interface RecipeGenerator extends RegistryLooker {

	/**
	 * Creates a {@link RecipeHelper} for a target.
	 * @param item the target
	 * @return the helper
	 */
	RecipeHelper forItem(ItemLike item);

	/**
	 * Gets the {@link HolderGetter<Item>} (item lookup) of this recipe generator.
	 * @return the item lookup
	 */
	HolderGetter<Item> getItemLookup();
}
