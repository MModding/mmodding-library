package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.datagen.api.recipe.RecipeProcessor;
import com.mmodding.library.datagen.impl.recipe.RecipeHelperImpl;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.ItemConvertible;

import java.util.List;
import java.util.function.Consumer;

public class RecipeTypeImpl<T extends ItemConvertible> implements DataContentType<T, RecipeProcessor<T>> {

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<RecipeProcessor<T>, List<T>> contentToProcess) {
		pack.addProvider((output, future) -> new AutomatedRecipeProvider<>(contentToProcess, output));
	}

	private static class AutomatedRecipeProvider<T extends ItemConvertible> extends FabricRecipeProvider {

		private final BiList<RecipeProcessor<T>, List<T>> contentToProcess;

		public AutomatedRecipeProvider(BiList<RecipeProcessor<T>, List<T>> contentToProcess, FabricDataOutput output) {
			super(output);
			this.contentToProcess = contentToProcess;
		}

		@Override
		public void generate(Consumer<RecipeJsonProvider> exporter) {
			this.contentToProcess.forEach((processor, craftables) -> {
				for (T craftable : craftables) {
					processor.process(new RecipeHelperImpl(craftable), craftable);
				}
			});
		}
	}
}
