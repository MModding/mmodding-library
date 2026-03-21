package com.mmodding.library.datagen.api.provider;

import com.mmodding.library.datagen.api.recipe.RecipeGenerator;
import com.mmodding.library.datagen.impl.recipe.RecipeHelperImpl;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;

import java.util.function.Consumer;

/**
 * Variant of {@link FabricRecipeProvider} because it shines better.
 */
public abstract class MModdingRecipeProvider extends FabricRecipeProvider {

	public MModdingRecipeProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter) {
		this.generate((RecipeGenerator) item -> new RecipeHelperImpl(exporter, item));
	}

	public abstract void generate(RecipeGenerator generator);
}
