package com.mmodding.library.datagen.api.provider;

import com.mmodding.library.datagen.api.recipe.RecipeGenerator;
import com.mmodding.library.datagen.impl.recipe.RecipeHelperImpl;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

/**
 * Variant of {@link FabricRecipeProvider} because it shines better.
 */
public abstract class MModdingRecipeProvider extends FabricRecipeProvider {

	public MModdingRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
		super(output, future);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
		return new RecipeProvider(registries, output) {

			@Override
			public void buildRecipes() {
				MModdingRecipeProvider.this.createRecipes(item -> new RecipeHelperImpl(this, this.output, item));
			}
		};
	}

	public abstract void createRecipes(RecipeGenerator generator);
}
