package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.lang.DefaultLangProcessors;
import com.mmodding.library.datagen.api.lang.TranslationProcessor;
import com.mmodding.library.datagen.api.management.handler.FinalDataHandler;
import com.mmodding.library.datagen.api.provider.MModdingLanguageProvider;
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
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@ApiStatus.Internal
public class BlockFamilyFinalDataHandler implements FinalDataHandler<BlockFamily> {

	@Override
	public Class<BlockFamily> getType() {
		return BlockFamily.class;
	}

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, List<BlockFamily> finalContents) {
		pack.addProvider((output, future) -> new AutomatedBlockFamilyTranslations(output, future, finalContents));
		pack.addProvider((output, future) -> new AutomatedBlockFamilyModels(output, finalContents));
		pack.addProvider((output, future) -> new AutomatedBlockFamilyRecipes(output, future, finalContents));
		AutomatedBlockFamilyBlockTags blockTags = pack.addProvider((output, future) -> new AutomatedBlockFamilyBlockTags(output, future, finalContents));
		pack.addProvider((output, future) -> new AutomatedBlockFamilyItemTags(output, future, blockTags));
	}

	private static class AutomatedBlockFamilyTranslations extends MModdingLanguageProvider {

		private final List<BlockFamily> families;

		public AutomatedBlockFamilyTranslations(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future, List<BlockFamily> families) {
			super(output, future);
			this.families = families;
		}

		@Override
		public void generateTranslations(HolderLookup.Provider lookup, TranslationBuilder translationBuilder) {
			TranslationProcessor<Block> classicProcessor = DefaultLangProcessors.getClassic();
			this.families.forEach(family -> {
				ResourceKey<Block> mainKey = BuiltInRegistries.BLOCK.getResourceKey(family.getBaseBlock()).orElseThrow();
				translationBuilder.add(family.getBaseBlock(), classicProcessor.process(mainKey));
				family.getVariants().values().forEach(block -> {
					ResourceKey<Block> variantKey = BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
					translationBuilder.add(block, classicProcessor.process(variantKey));
				});
			});
		}

		@Override
		public String getName() {
			return "Automated Block Family " + super.getName();
		}
	}

	private static class AutomatedBlockFamilyModels extends FabricModelProvider {

		private final List<BlockFamily> families;

		public AutomatedBlockFamilyModels(FabricPackOutput output, List<BlockFamily> families) {
			super(output);
			this.families = families;
		}

		@Override
		public void generateBlockStateModels(BlockModelGenerators generator) {
			this.families.forEach(family -> {
				if (family.shouldGenerateModel()) {
					generator.family(family.getBaseBlock()).generateFor(family);
				}
			});
		}

		@Override
		public void generateItemModels(ItemModelGenerators generator) {}

		@Override
		public String getName() {
			return "Automated Block Family " + super.getName();
		}
	}

	private static class AutomatedBlockFamilyRecipes extends FabricRecipeProvider {

		private final List<BlockFamily> families;

		public AutomatedBlockFamilyRecipes(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future, List<BlockFamily> families) {
			super(output, future);
			this.families = families;
		}

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
			return new RecipeProvider(registries, output) {

				@Override
				public void buildRecipes() {
					AutomatedBlockFamilyRecipes.this.families.forEach(family -> this.generateRecipes(family, FeatureFlags.DEFAULT_FLAGS));
				}
			};
		}

		@Override
		public String getName() {
			return "Automated Block Family Recipes";
		}
	}

	private static class AutomatedBlockFamilyBlockTags extends FabricTagsProvider.BlockTagsProvider {

		private final List<BlockFamily> families;
		private final Set<TagKey<Block>> memory;

		public AutomatedBlockFamilyBlockTags(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future, List<BlockFamily> families) {
			super(output, future);
			this.families = families;
			this.memory = new HashSet<>();
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.families.forEach(family -> {
				for (Map.Entry<BlockFamily.Variant, Block> entry : family.getVariants().entrySet()) {
					switch (entry.getKey()) {
						case BUTTON -> this.buildAndMemorize(BlockTags.BUTTONS).add(entry.getValue());
						case DOOR -> this.buildAndMemorize(BlockTags.DOORS).add(entry.getValue());
						case FENCE -> this.buildAndMemorize(BlockTags.FENCES).add(entry.getValue());
						case FENCE_GATE -> this.buildAndMemorize(BlockTags.FENCE_GATES).add(entry.getValue());
						case SIGN -> this.buildAndMemorize(BlockTags.SIGNS).add(entry.getValue());
						case SLAB -> this.buildAndMemorize(BlockTags.SLABS).add(entry.getValue());
						case STAIRS -> this.buildAndMemorize(BlockTags.STAIRS).add(entry.getValue());
						case PRESSURE_PLATE -> this.buildAndMemorize(BlockTags.PRESSURE_PLATES).add(entry.getValue());
						case TRAPDOOR -> this.buildAndMemorize(BlockTags.TRAPDOORS).add(entry.getValue());
						case WALL -> this.buildAndMemorize(BlockTags.WALLS).add(entry.getValue());
						case WALL_SIGN -> this.buildAndMemorize(BlockTags.WALL_SIGNS).add(entry.getValue());
					}
				}
			});
		}

		protected TagAppender<Block, Block> buildAndMemorize(TagKey<Block> tag) {
			this.memory.add(tag);
			return this.valueLookupBuilder(tag);
		}

		@Override
		public String getName() {
			return "Automated Block Family " + super.getName();
		}
	}

	private static class AutomatedBlockFamilyItemTags extends FabricTagsProvider.ItemTagsProvider {

		private final Set<TagKey<Block>> memory;

		public AutomatedBlockFamilyItemTags(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, AutomatedBlockFamilyBlockTags blockTagProvider) {
			super(output, completableFuture, blockTagProvider);
			this.memory = blockTagProvider.memory;
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.copyIfMemorized(BlockTags.BUTTONS, ItemTags.BUTTONS);
			this.copyIfMemorized(BlockTags.DOORS, ItemTags.DOORS);
			this.copyIfMemorized(BlockTags.FENCES, ItemTags.FENCES);
			this.copyIfMemorized(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
			this.copyIfMemorized(BlockTags.SIGNS, ItemTags.SIGNS);
			this.copyIfMemorized(BlockTags.SLABS, ItemTags.SLABS);
			this.copyIfMemorized(BlockTags.STAIRS, ItemTags.STAIRS);
			// no pressure plates item tag?
			this.copyIfMemorized(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
			this.copyIfMemorized(BlockTags.WALLS, ItemTags.WALLS);
		}

		protected void copyIfMemorized(TagKey<Block> blockTag, TagKey<Item> itemTag) {
			if (this.memory.contains(blockTag)) this.copy(blockTag, itemTag);
		}

		@Override
		public String getName() {
			return "Automated Block Family " + super.getName();
		}
	}
}
