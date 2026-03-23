package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.family.BlockFamilyProcessor;
import com.mmodding.library.datagen.api.lang.DefaultLangProcessors;
import com.mmodding.library.datagen.api.lang.TranslationProcessor;
import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.datagen.api.provider.MModdingLanguageProvider;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;

import java.util.List;
import java.util.function.Consumer;

public class BlockFamilyTypeImpl implements DataContentType<BlockFamily, BlockFamilyProcessor> {

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess) {
		pack.addProvider((output, lookup) -> new AutomatedBlockFamilyTranslations(output, contentToProcess));
		pack.addProvider((output, lookup) -> new AutomatedBlockFamilyModels(output, contentToProcess));
		pack.addProvider((output, lookup) -> new AutomatedBlockFamilyRecipes(output, contentToProcess));
	}

	private static class AutomatedBlockFamilyTranslations extends MModdingLanguageProvider {

		private final BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess;

		public AutomatedBlockFamilyTranslations(FabricDataOutput output, BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess) {
			super(output);
			this.contentToProcess = contentToProcess;
		}

		@Override
		public void generateTranslations(TranslationBuilder translationBuilder) {
			TranslationProcessor<Block> classicProcessor = DefaultLangProcessors.getClassic(); // I don't think that it really needs to be customized here. Block Families are meant to naming conventions.
			this.contentToProcess.forEach((processor, families) -> {
				families.forEach(family -> {
					RegistryKey<Block> mainKey = Registries.BLOCK.getKey(family.getBaseBlock()).orElseThrow();
					translationBuilder.add(family.getBaseBlock(), classicProcessor.process(mainKey));
					family.getVariants().values().forEach(block -> {
						RegistryKey<Block> variantKey = Registries.BLOCK.getKey(block).orElseThrow();
						translationBuilder.add(block, classicProcessor.process(variantKey));
					});
				});
			});
		}

		@Override
		public String getName() {
			return "Automated Block Family " + super.getName();
		}
	}

	private static class AutomatedBlockFamilyModels extends FabricModelProvider {

		private final BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess;

		public AutomatedBlockFamilyModels(FabricDataOutput output, BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess) {
			super(output);
			this.contentToProcess = contentToProcess;
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator generator) {
			this.contentToProcess.forEach(
				(processor, families) -> families.forEach(
					family -> processor.process(generator, family)
				)
			);
		}

		@Override
		public void generateItemModels(ItemModelGenerator generator) {}

		@Override
		public String getName() {
			return "Automated Block Family " + super.getName();
		}
	}

	private static class AutomatedBlockFamilyRecipes extends FabricRecipeProvider {

		private final BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess;

		public AutomatedBlockFamilyRecipes(FabricDataOutput output, BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess) {
			super(output);
			this.contentToProcess = contentToProcess;
		}

		@Override
		public void generate(Consumer<RecipeJsonProvider> exporter) {
			this.contentToProcess.forEach(
				(processor, families) -> families.forEach(
					family -> processor.process(exporter, family)
				)
			);
		}

		@Override
		public String getName() {
			return "Automated Block Family " + super.getName();
		}
	}
}
