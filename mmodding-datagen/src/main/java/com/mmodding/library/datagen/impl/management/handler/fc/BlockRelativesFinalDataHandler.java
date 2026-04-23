package com.mmodding.library.datagen.impl.management.handler.fc;

import com.mmodding.library.block.api.wrapper.BlockRelatives;
import com.mmodding.library.block.impl.wrapper.BlockRelativesImpl;
import com.mmodding.library.datagen.api.lang.DefaultLangProcessors;
import com.mmodding.library.datagen.api.management.handler.FinalDataHandler;
import com.mmodding.library.datagen.api.provider.MModdingLanguageProvider;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
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
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@ApiStatus.Internal
public class BlockRelativesFinalDataHandler implements FinalDataHandler<BlockRelatives> {

	@Override
	public Class<BlockRelatives> getType() {
		return BlockRelatives.class;
	}

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, List<BlockRelatives> finalContent) {
		FinalDataHandler.with(pack, finalContent, AutomatedBlockRelativesTranslations::new);
		FinalDataHandler.with(pack, finalContent, AutomatedBlockRelativesModels::new);
		FinalDataHandler.with(pack, finalContent, AutomatedBlockRelativesBlockLootTables::new);
		FinalDataHandler.with(pack, finalContent, AutomatedBlockRelativesRecipes::new);
		AutomatedBlockFamilyBlockTags blockTags = FinalDataHandler.with(pack, finalContent, AutomatedBlockFamilyBlockTags::new);
		pack.addProvider((output, future) -> new AutomatedBlockFamilyItemTags(finalContent, output, future, blockTags));
	}

	static class AutomatedBlockRelativesTranslations extends MModdingLanguageProvider {

		private final List<BlockRelatives> relatives;

		public AutomatedBlockRelativesTranslations(List<BlockRelatives> relatives, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
			this.relatives = relatives;
		}

		@Override
		public void generateTranslations(HolderLookup.Provider lookup, TranslationBuilder translationBuilder) {
			this.relatives.forEach(current -> {
				current.getEntries().stream().filter(block -> !(block instanceof SignBlock || block instanceof WallSignBlock)).toList().forEach(block -> {
					Identifier blockId = BuiltInRegistries.BLOCK.getKey(block);
					translationBuilder.add(block, DefaultLangProcessors.CLASSIC.process(blockId));
				});
				if (current.getVariants().contains(BlockFamily.Variant.SIGN)) {
					Item item = current.get(BlockFamily.Variant.SIGN).asItem();
					Identifier identifier = BuiltInRegistries.ITEM.getKey(item);
					translationBuilder.add(item, DefaultLangProcessors.CLASSIC.process(identifier));
				}
			});
		}

		@Override
		public String getName() {
			return "Automated Block Relatives " + super.getName();
		}
	}

	static class AutomatedBlockRelativesModels extends FabricModelProvider {

		private final List<BlockRelatives> relatives;

		public AutomatedBlockRelativesModels(List<BlockRelatives> relatives, FabricPackOutput output) {
			super(output);
			this.relatives = relatives;
		}

		@Override
		public void generateBlockStateModels(BlockModelGenerators generator) {
			this.relatives.forEach(relatives -> {
				BlockFamily family = ((BlockRelativesImpl) relatives).initDataFamily();
				if (family.shouldGenerateModel()) {
					generator.family(family.getBaseBlock()).generateFor(family);
				}
			});
		}

		@Override
		public void generateItemModels(ItemModelGenerators generator) {}

		@Override
		public String getName() {
			return "Automated Block Relatives " + super.getName();
		}
	}

	static class AutomatedBlockRelativesBlockLootTables extends FabricBlockLootSubProvider {

		private final List<BlockRelatives> relatives;

		public AutomatedBlockRelativesBlockLootTables(List<BlockRelatives> relatives, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
			this.relatives = relatives;
		}

		@Override
		public void generate() {
			for (BlockRelatives current : this.relatives) {
				this.dropSelf(current.getMain());
				for (BlockFamily.Variant variant : current.getVariants()) {
					if (!variant.equals(BlockFamily.Variant.DOOR) && !variant.equals(BlockFamily.Variant.WALL_SIGN)) {
						this.dropSelf(current.get(variant));
					}
					else if (variant.equals(BlockFamily.Variant.DOOR)) {
						this.add(current.get(variant), this::createDoorTable);
					}
				}
			}
		}

		@Override
		public String getName() {
			return "Automated Block Relatives " + super.getName();
		}
	}

	static class AutomatedBlockRelativesRecipes extends FabricRecipeProvider {

		private final List<BlockRelatives> relatives;

		public AutomatedBlockRelativesRecipes(List<BlockRelatives> relatives, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
			this.relatives = relatives;
		}

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
			return new RecipeProvider(registries, output) {

				@Override
				public void buildRecipes() {
					AutomatedBlockRelativesRecipes.this.relatives.forEach(current -> {
						BlockFamily family = ((BlockRelativesImpl) current).initDataFamily();
						this.generateRecipes(family, FeatureFlags.DEFAULT_FLAGS);
					});
				}
			};
		}

		@Override
		public String getName() {
			return "Automated Block Relatives Recipes";
		}
	}

	static class AutomatedBlockFamilyBlockTags extends FabricTagsProvider.BlockTagsProvider {

		private final List<BlockRelatives> relatives;
		private final Set<TagKey<Block>> memory;

		public AutomatedBlockFamilyBlockTags(List<BlockRelatives> relatives, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
			this.relatives = relatives;
			this.memory = new HashSet<>();
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.relatives.forEach(current -> {
				this.valueLookupBuilder(current.getBlockTagKey()).add(current.getMain());
				for (BlockFamily.Variant variant : current.getVariants()) {
					Block block = current.get(variant);
					this.valueLookupBuilder(current.getBlockTagKey()).add(block);
					switch (variant) {
						case BUTTON -> this.buildAndMemorize(BlockTags.BUTTONS).add(block);
						case DOOR -> this.buildAndMemorize(BlockTags.DOORS).add(block);
						case FENCE -> this.buildAndMemorize(BlockTags.FENCES).add(block);
						case FENCE_GATE -> this.buildAndMemorize(BlockTags.FENCE_GATES).add(block);
						case SIGN -> this.buildAndMemorize(BlockTags.SIGNS).add(block);
						case SLAB -> this.buildAndMemorize(BlockTags.SLABS).add(block);
						case STAIRS -> this.buildAndMemorize(BlockTags.STAIRS).add(block);
						case PRESSURE_PLATE -> this.buildAndMemorize(BlockTags.PRESSURE_PLATES).add(block);
						case TRAPDOOR -> this.buildAndMemorize(BlockTags.TRAPDOORS).add(block);
						case WALL -> this.buildAndMemorize(BlockTags.WALLS).add(block);
						case WALL_SIGN -> this.buildAndMemorize(BlockTags.WALL_SIGNS).add(block);
					}
				}
			});
		}

		private TagAppender<Block, Block> buildAndMemorize(TagKey<Block> tag) {
			this.memory.add(tag);
			return this.valueLookupBuilder(tag);
		}

		@Override
		public String getName() {
			return "Automated Block Relatives " + super.getName();
		}
	}

	static class AutomatedBlockFamilyItemTags extends FabricTagsProvider.ItemTagsProvider {

		private final List<BlockRelatives> relatives;
		private final Set<TagKey<Block>> memory;

		public AutomatedBlockFamilyItemTags(List<BlockRelatives> relatives, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, AutomatedBlockFamilyBlockTags blockTagProvider) {
			super(output, completableFuture, blockTagProvider);
			this.relatives = relatives;
			this.memory = blockTagProvider.memory;
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.relatives.forEach(current -> this.copy(current.getBlockTagKey(), current.getItemTagKey()));
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

		private void copyIfMemorized(TagKey<Block> blockTag, TagKey<Item> itemTag) {
			if (this.memory.contains(blockTag)) this.copy(blockTag, itemTag);
		}

		@Override
		public String getName() {
			return "Automated Block Relatives " + super.getName();
		}
	}
}
