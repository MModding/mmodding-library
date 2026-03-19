package com.mmodding.library.datagen.api.family;

import com.mmodding.library.java.api.either.Either;
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

	public void process(Either<BlockStateModelGenerator, Consumer<RecipeJsonProvider>> either, BlockFamily family) {
		either.execute(generator -> {
			if (family.shouldGenerateModels()) {
				generator.registerCubeAllModelTexturePool(family.getBaseBlock()).family(family);
			}
		}, provider -> {
			if (family.shouldGenerateRecipes(FeatureFlags.FEATURE_MANAGER.getFeatureSet())) {
				RecipeProvider.generateFamily(provider, family);
			}
		});
	}
}
