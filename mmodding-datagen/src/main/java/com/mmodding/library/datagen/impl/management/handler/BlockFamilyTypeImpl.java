package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.family.BlockFamilyProcessor;
import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.java.api.either.Either;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.recipe.RecipeJsonProvider;

import java.util.List;
import java.util.function.Consumer;

public class BlockFamilyTypeImpl implements DataContentType<BlockFamily, BlockFamilyProcessor> {

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess) {
		pack.addProvider((output, lookup) -> new AutomatedBlockFamilyModels(output, contentToProcess));
		pack.addProvider((output, lookup) -> new AutomatedBlockFamilyRecipes(output, contentToProcess));
	}

	private static class AutomatedBlockFamilyModels extends FabricModelProvider {

		private final BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess;

		public AutomatedBlockFamilyModels(FabricDataOutput output, BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess) {
			super(output);
			this.contentToProcess = contentToProcess;
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
			this.contentToProcess.forEach((processor, families) -> {
				Either<BlockStateModelGenerator, Consumer<RecipeJsonProvider>> either = Either.ofFirst(blockStateModelGenerator);
				families.forEach(family -> processor.process(either, family));
			});
		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {}
	}

	private static class AutomatedBlockFamilyRecipes extends FabricRecipeProvider {

		private final BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess;

		public AutomatedBlockFamilyRecipes(FabricDataOutput output, BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess) {
			super(output);
			this.contentToProcess = contentToProcess;
		}

		@Override
		public void generate(Consumer<RecipeJsonProvider> exporter) {
			this.contentToProcess.forEach((processor, families) -> {
				Either<BlockStateModelGenerator, Consumer<RecipeJsonProvider>> either = Either.ofSecond(exporter);
				families.forEach(family -> processor.process(either, family));
			});
		}
	}
}
