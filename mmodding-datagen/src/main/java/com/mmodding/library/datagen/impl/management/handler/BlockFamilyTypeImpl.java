package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.family.BlockFamilyProcessor;
import com.mmodding.library.datagen.api.lang.DefaultLangProcessors;
import com.mmodding.library.datagen.api.lang.TranslationProcessor;
import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.datagen.api.provider.MModdingLanguageProvider;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BlockFamilyTypeImpl implements DataContentType<BlockFamily, BlockFamilyProcessor> {

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess) {
		pack.addProvider((output, future) -> new AutomatedBlockFamilyTranslations(output, future, contentToProcess));
		pack.addProvider((output, future) -> new AutomatedBlockFamilyModels(output, contentToProcess));
		pack.addProvider((output, future) -> new AutomatedBlockFamilyRecipes(output, future, contentToProcess));
		AutomatedBlockFamilyBlockTags blockTags = pack.addProvider((output, future) -> new AutomatedBlockFamilyBlockTags(output, future, contentToProcess));
		pack.addProvider((output, future) -> new AutomatedBlockFamilyItemTags(output, future, blockTags));
	}

	private static class AutomatedBlockFamilyTranslations extends MModdingLanguageProvider {

		private final BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess;

		public AutomatedBlockFamilyTranslations(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future, BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess) {
			super(output, future);
			this.contentToProcess = contentToProcess;
		}

		@Override
		public void generateTranslations(HolderLookup.Provider lookup, TranslationBuilder translationBuilder) {
			TranslationProcessor<Block> classicProcessor = DefaultLangProcessors.getClassic(); // I don't think that it really needs to be customized here. Block Families are meant to naming conventions.
			this.contentToProcess.forEach((processor, families) -> {
				families.forEach(family -> {
					ResourceKey<Block> mainKey = BuiltInRegistries.BLOCK.getResourceKey(family.getBaseBlock()).orElseThrow();
					translationBuilder.add(family.getBaseBlock(), classicProcessor.process(mainKey));
					family.getVariants().values().forEach(block -> {
						ResourceKey<Block> variantKey = BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
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

		public AutomatedBlockFamilyModels(FabricPackOutput output, BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess) {
			super(output);
			this.contentToProcess = contentToProcess;
		}

		@Override
		public void generateBlockStateModels(BlockModelGenerators generator) {
			this.contentToProcess.forEach(
				(processor, families) -> families.forEach(
					family -> processor.process(generator, family)
				)
			);
		}

		@Override
		public void generateItemModels(ItemModelGenerators generator) {}

		@Override
		public String getName() {
			return "Automated Block Family " + super.getName();
		}
	}

	private static class AutomatedBlockFamilyRecipes extends FabricRecipeProvider {

		private final BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess;

		public AutomatedBlockFamilyRecipes(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future, BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess) {
			super(output, future);
			this.contentToProcess = contentToProcess;
		}

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
			return new RecipeProvider(registries, output) {

				@Override
				public void buildRecipes() {
					AutomatedBlockFamilyRecipes.this.contentToProcess.forEach(
						(ignored, families) -> families.forEach(
							family -> this.generateRecipes(family, FeatureFlags.DEFAULT_FLAGS)
						)
					);
				}
			};
		}

		@Override
		public String getName() {
			return "Automated Block Family Recipes";
		}
	}

	private static class AutomatedBlockFamilyBlockTags extends FabricTagsProvider.BlockTagsProvider {

		private final BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess;

		public AutomatedBlockFamilyBlockTags(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future, BiList<BlockFamilyProcessor, List<BlockFamily>> contentToProcess) {
			super(output, future);
			this.contentToProcess = contentToProcess;
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.contentToProcess.forEach((processor, families) -> families.forEach(
				family -> processor.process(this::valueLookupBuilder, family)
			));
		}

		@Override
		public String getName() {
			return "Automated Block Family " + super.getName();
		}
	}

	private static class AutomatedBlockFamilyItemTags extends FabricTagsProvider.ItemTagsProvider {

		public AutomatedBlockFamilyItemTags(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable BlockTagsProvider blockTagProvider) {
			super(output, completableFuture, blockTagProvider);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
			this.copy(BlockTags.DOORS, ItemTags.DOORS);
			this.copy(BlockTags.FENCES, ItemTags.FENCES);
			this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
			this.copy(BlockTags.SIGNS, ItemTags.SIGNS);
			this.copy(BlockTags.SLABS, ItemTags.SLABS);
			this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
			// no pressure plates item tag?
			this.copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
			this.copy(BlockTags.WALLS, ItemTags.WALLS);
		}

		@Override
		public String getName() {
			return "Automated Block Family " + super.getName();
		}
	}
}
