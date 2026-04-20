package com.mmodding.library.datagen.impl.management.handler.ctp;

import com.mmodding.library.datagen.api.management.handler.DataProcessHandler;
import com.mmodding.library.datagen.api.recipe.RecipeProcessor;
import com.mmodding.library.datagen.impl.recipe.RecipeHelperImpl;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApiStatus.Internal
public class RecipeHandler<T extends ItemLike> implements DataProcessHandler<T, RecipeProcessor<T>> {

	@Override
	@SuppressWarnings("unchecked")
	public Class<T> getType() {
		return (Class<T>) ItemLike.class;
	}

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<RecipeProcessor<T>, List<T>> contentToProcess) {
		DataProcessHandler.provider(pack, contentToProcess, AutomatedRecipeProvider::new);
	}

	private static class AutomatedRecipeProvider<T extends ItemLike> extends FabricRecipeProvider {


		private final BiList<RecipeProcessor<T>, List<T>> contentToProcess;

		public AutomatedRecipeProvider(BiList<RecipeProcessor<T>, List<T>> contentToProcess, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
			this.contentToProcess = contentToProcess;
		}

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
			return new RecipeProvider(registries, output) {

				@Override
				public void buildRecipes() {
					AutomatedRecipeProvider.this.contentToProcess.forEach((processor, craftables) -> {
						for (T craftable : craftables) {
							processor.process(new RecipeHelperImpl(this, this.output, craftable), craftable);
						}
					});
				}
			};
		}

		@Override
		public String getName() {
			return "Automated Recipes";
		}
	}
}
