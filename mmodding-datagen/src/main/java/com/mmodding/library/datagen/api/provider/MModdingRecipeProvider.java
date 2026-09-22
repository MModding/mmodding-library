package com.mmodding.library.datagen.api.provider;

import com.mmodding.library.datagen.api.recipe.RecipeGenerator;
import com.mmodding.library.datagen.impl.recipe.RecipeGeneratorImpl;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;

import java.util.concurrent.CompletableFuture;

/**
 * Variant of {@link FabricRecipeProvider} because it shines better.
 */
public abstract class MModdingRecipeProvider extends FabricRecipeProvider {

	public MModdingRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
		super(output, future);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
		return new RecipeProvider(recipes, advancements) {

			@Override
			public void buildRecipes() {
				MModdingRecipeProvider.this.createRecipes(new RecipeGeneratorImpl(this, this.output, registries));
			}
		};
	}

	public abstract void createRecipes(RecipeGenerator generator);
}
