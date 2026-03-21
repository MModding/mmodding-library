package com.mmodding.library.datagen.api.family;

import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.resource.featuretoggle.FeatureFlags;

import java.util.function.Consumer;

/**
 * We deal with those in only one way.
 */
public final class BlockFamilyProcessor {

	public void process(BlockStateModelGenerator generator, BlockFamily family) {
		if (family.shouldGenerateModels()) {
			generator.registerCubeAllModelTexturePool(family.getBaseBlock()).family(family);
		}
	}

	public void process(Consumer<RecipeJsonProvider> exporter, BlockFamily family) {
		if (family.shouldGenerateRecipes(FeatureFlags.FEATURE_MANAGER.getFeatureSet())) {
			RecipeProvider.generateFamily(exporter, family);
		}
	}
}
