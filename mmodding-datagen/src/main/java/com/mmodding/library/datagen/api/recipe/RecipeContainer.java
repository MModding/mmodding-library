package com.mmodding.library.datagen.api.recipe;

import net.minecraft.item.ItemConvertible;

import java.util.function.Consumer;

public interface RecipeContainer {

	default <T extends ItemConvertible> T recipe(Consumer<RecipeHelper> helper) {
		throw new IllegalStateException();
	}
}
