package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.management.DataContentType;
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
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipeTypeImpl<T extends ItemLike> implements DataContentType<T, RecipeProcessor<T>> {

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<RecipeProcessor<T>, List<T>> contentToProcess) {
		pack.addProvider((output, future) -> new AutomatedRecipeProvider<>(contentToProcess, future, output));
	}

	private static class AutomatedRecipeProvider<T extends ItemLike> extends FabricRecipeProvider {

		private final BiList<RecipeProcessor<T>, List<T>> contentToProcess;

		public AutomatedRecipeProvider(BiList<RecipeProcessor<T>, List<T>> contentToProcess, CompletableFuture<HolderLookup.Provider> future, FabricPackOutput output) {
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
