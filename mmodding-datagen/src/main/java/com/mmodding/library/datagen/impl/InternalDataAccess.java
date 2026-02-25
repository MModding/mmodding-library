package com.mmodding.library.datagen.impl;

import com.mmodding.library.datagen.api.loot.block.BlockLootContainer;
import com.mmodding.library.datagen.api.loot.block.BlockLootProcessor;
import com.mmodding.library.datagen.api.loot.entity.EntityLootContainer;
import com.mmodding.library.datagen.api.loot.entity.EntityLootProcessor;
import com.mmodding.library.datagen.api.recipe.RecipeContainer;
import com.mmodding.library.datagen.api.recipe.RecipeHelper;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;

@ApiStatus.Internal
public class InternalDataAccess {

	public InternalDataAccess() {
		throw new IllegalStateException("InternalDataAccess only contains static definitions");
	}

	static Consumer<RecipeHelper> recipeGenerator(RecipeContainer container) {
		return ((RecipeGeneratorAccess) container).recipeGenerator();
	}

	static BlockLootProcessor blockLootProcessor(BlockLootContainer container) {
		return ((BlockLootContainerAccess) container).blockLootProcessor();
	}

	@SuppressWarnings("unchecked")
	static <T extends Entity> EntityLootProcessor<T> entityLootProcessor(EntityLootContainer container) {
		return ((EntityLootContainerAccess<T>) container).entityLootProcessor();
	}

	public interface RecipeGeneratorAccess {

		Consumer<RecipeHelper> recipeGenerator();
	}

	public interface BlockLootContainerAccess {

		BlockLootProcessor blockLootProcessor();
	}

	public interface EntityLootContainerAccess<T extends Entity> {

		EntityLootProcessor<T> entityLootProcessor();
	}
}
