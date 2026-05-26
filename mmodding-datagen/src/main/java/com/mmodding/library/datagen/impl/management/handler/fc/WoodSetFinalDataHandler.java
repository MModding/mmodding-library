package com.mmodding.library.datagen.impl.management.handler.fc;

import com.mmodding.library.block.api.catalog.AdvancedLeavesBlock;
import com.mmodding.library.block.api.wrapper.BlockRelatives;
import com.mmodding.library.block.impl.wrapper.BlockRelativesImpl;
import com.mmodding.library.datagen.api.lang.DefaultLangProcessors;
import com.mmodding.library.datagen.api.management.handler.FinalDataHandler;
import com.mmodding.library.datagen.api.provider.BuiltinRegistryTagsProvider;
import com.mmodding.library.datagen.api.provider.MModdingLanguageProvider;
import com.mmodding.library.woodset.api.WoodSet;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WoodSetFinalDataHandler implements FinalDataHandler<WoodSet> {

	@Override
	public Class<WoodSet> getType() {
		return WoodSet.class;
	}

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, List<WoodSet> finalContent) {
		List<BlockRelatives> plankRelatives = finalContent.stream().map(WoodSet::getPlankRelatives).toList();
		FinalDataHandler.with(pack, finalContent, AutomatedTranslations::new);
		FinalDataHandler.with(pack, plankRelatives, AutomatedPlankRelativesTranslations::new);
		FinalDataHandler.with(pack, finalContent, AutomatedModels::new);
		FinalDataHandler.with(pack, plankRelatives, AutomatedPlankRelativesModels::new);
		FinalDataHandler.with(pack, finalContent, AutomatedBlockLootTables::new);
		FinalDataHandler.with(pack, plankRelatives, AutomatedPlankRelativesBlockLootTables::new);
		FinalDataHandler.with(pack, finalContent, AutomatedRecipes::new);
		FinalDataHandler.with(pack, plankRelatives, AutomatedPlankRelativesRecipes::new);
		AutomatedBlockTags blockTags = FinalDataHandler.with(pack, finalContent, AutomatedBlockTags::new);
		pack.addProvider((output, future) -> new AutomatedItemTags(finalContent, output, future, blockTags));
		FinalDataHandler.with(pack, finalContent, AutomatedEntityTypeTags::new);
	}

	private BlockFamily extractFamily(WoodSet set) {
		return ((BlockRelativesImpl) set.getPlankRelatives()).initDataFamily();
	}

	private static class AutomatedTranslations extends MModdingLanguageProvider {

		private final List<WoodSet> sets;

		protected AutomatedTranslations(List<WoodSet> sets, FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> future) {
			super(dataOutput, future);
			this.sets = sets;
		}

		@Override
		public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder builder) {
			for (WoodSet set : this.sets) {
				this.block(builder, set.getLog());
				this.block(builder, set.getWood());
				this.block(builder, set.getStrippedLog());
				this.block(builder, set.getStrippedWood());
				this.block(builder, set.getLeaves());
				this.block(builder, set.getSapling());
				Identifier hangingSignItemId = BuiltInRegistries.ITEM.getKey(set.getHangingSign().asItem());
				builder.add(set.getHangingSign().asItem(), DefaultLangProcessors.CLASSIC.process(hangingSignItemId));
				this.block(builder, set.getShelf());
				Identifier boatId = BuiltInRegistries.ENTITY_TYPE.getKey(set.getBoatEntityType());
				builder.add(set.getBoatEntityType(), DefaultLangProcessors.CLASSIC.process(boatId));
				builder.add(set.getBoatItem(), DefaultLangProcessors.CLASSIC.process(boatId));
				Identifier chestBoatId = BuiltInRegistries.ENTITY_TYPE.getKey(set.getChestBoatEntityType());
				builder.add(set.getChestBoatEntityType(), DefaultLangProcessors.CHEST_BOAT.process(chestBoatId));
				builder.add(set.getChestBoatItem(), DefaultLangProcessors.CHEST_BOAT.process(chestBoatId));
			}
		}

		private void block(TranslationBuilder builder, Block block) {
			builder.add(block, DefaultLangProcessors.CLASSIC.process(BuiltInRegistries.BLOCK.getKey(block)));
		}

		@Override
		public String getName() {
			return "Automated Wood Set " + super.getName();
		}
	}

	private static class AutomatedModels extends FabricModelProvider {

		private final List<WoodSet> sets;

		protected AutomatedModels(List<WoodSet> sets, FabricPackOutput dataOutput) {
			super(dataOutput);
			this.sets = sets;
		}

		@Override
		public void generateBlockStateModels(BlockModelGenerators generator) {
			for (WoodSet set : this.sets) {
				BlockModelGenerators.WoodProvider provider = generator.woodProvider(set.getLog()).wood(set.getWood());
				BlockModelGenerators.WoodProvider stripped = generator.woodProvider(set.getStrippedLog()).wood(set.getStrippedWood());
				switch (set.getSettings().getLogDisplay()) {
					case NORMAL -> { provider.log(set.getLog()); stripped.log(set.getStrippedLog()); }
					case WITH_HORIZONTAL -> { provider.logWithHorizontal(set.getLog()); stripped.logWithHorizontal(set.getStrippedLog()); }
					case UV_LOCKED -> { provider.logUVLocked(set.getLog()); stripped.logUVLocked(set.getStrippedLog()); }
				}
				AdvancedLeavesBlock leaves = (AdvancedLeavesBlock) set.getLeaves();
				if (leaves.getItemTintColor() != null) {
					generator.createTintedLeaves(leaves, TexturedModel.LEAVES, leaves.getItemTintColor().toDecimal());
				}
				else {
					generator.createTrivialBlock(leaves, TexturedModel.LEAVES);
				}
				generator.createPlantWithDefaultItem(set.getSapling(), set.getPottedSapling(), BlockModelGenerators.PlantType.NOT_TINTED);
				generator.family(set.getPlankRelatives().getMain()).hangingSign(set.getHangingSign());
				generator.createShelf(set.getShelf(), set.getStrippedLog());
			}
		}

		@Override
		public void generateItemModels(ItemModelGenerators generator) {
			for (WoodSet set : this.sets) {
				generator.generateFlatItem(set.getBoatItem(), ModelTemplates.FLAT_ITEM);
				generator.generateFlatItem(set.getChestBoatItem(), ModelTemplates.FLAT_ITEM);
			}
		}

		@Override
		public String getName() {
			return "Automated Wood Set " + super.getName();
		}
	}

	private static class AutomatedBlockLootTables extends FabricBlockLootSubProvider {

		private final List<WoodSet> sets;

		protected AutomatedBlockLootTables(List<WoodSet> sets, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
			this.sets = sets;
		}

		@Override
		public void generate() {
			for (WoodSet set : this.sets) {
				this.dropSelf(set.getLog());
				this.dropSelf(set.getWood());
				this.dropSelf(set.getStrippedLog());
				this.dropSelf(set.getStrippedWood());
				this.add(set.getLeaves(), block -> this.createLeavesDrops(block, set.getSapling(), NORMAL_LEAVES_SAPLING_CHANCES));
				this.dropSelf(set.getSapling());
				this.dropPottedContents(set.getPottedSapling());
				this.dropSelf(set.getHangingSign());
				this.dropSelf(set.getShelf());
			}
		}

		@Override
		public String getName() {
			return "Automated Wood Set " + super.getName();
		}
	}

	private static class AutomatedRecipes extends FabricRecipeProvider {

		private final List<WoodSet> sets;

		protected AutomatedRecipes(List<WoodSet> sets, FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> future) {
			super(dataOutput, future);
			this.sets = sets;
		}

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
			return new RecipeProvider(registries, output) {

				private void hangingSign(final ItemLike result, final ItemLike ingredient, final ItemLike chain) {
					this.shaped(RecipeCategory.DECORATIONS, result, 6)
						.group("hanging_sign")
						.define('#', ingredient)
						.define('X', chain)
						.pattern("X X")
						.pattern("###")
						.pattern("###")
						.unlockedBy("has_stripped_logs", this.has(ingredient))
						.save(this.output);
				}

				@Override
				public void buildRecipes() {
					for (WoodSet set : AutomatedRecipes.this.sets) {
						this.woodFromLogs(set.getWood(), set.getLog());
						this.woodFromLogs(set.getStrippedWood(), set.getStrippedLog());
						this.planksFromLog(set.getPlankRelatives().getMain(), set.getLogsItemTag(), 4);
						this.hangingSign(set.getHangingSign(), set.getStrippedLog(), set.getSettings().getHangingSignChain());
						this.shelf(set.getShelf(), set.getStrippedLog());
						this.woodenBoat(set.getBoatItem(), set.getPlankRelatives().getMain());
						this.chestBoat(set.getChestBoatItem(), set.getPlankRelatives().getMain());
					}
				}
			};
		}

		@Override
		public String getName() {
			return "Automated Wood Set Recipes";
		}
	}

	private static class AutomatedBlockTags extends BuiltinRegistryTagsProvider.BlockTagsProvider {

		private final List<WoodSet> sets;
		private boolean hasBurnable = false;
		private boolean hasNonBurnable = false;

		protected AutomatedBlockTags(List<WoodSet> sets, FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> future) {
			super(dataOutput, future);
			this.sets = sets;
		}

		@Override
		protected void addTags(HolderLookup.Provider registries) {
			for (WoodSet set : this.sets) {
				this.valueBuilder(set.getLogsBlockTag()).add(set.getLog(), set.getWood(), set.getStrippedLog(), set.getStrippedWood());
				if (set.getSettings().isBurnable()) {
					this.hasBurnable = true;
					this.valueBuilder(BlockItemTags.LOGS_THAT_BURN.block()).addTag(set.getLogsBlockTag());
				}
				else {
					this.hasNonBurnable = true;
					this.valueBuilder(BlockTags.LOGS).addTag(set.getLogsBlockTag());
				}
				this.valueBuilder(BlockTags.LEAVES).add(set.getLeaves());
				this.valueBuilder(BlockItemTags.SAPLINGS.block()).add(set.getSapling());
				this.valueBuilder(BlockTags.FLOWER_POTS).add(set.getPottedSapling());
				this.valueBuilder(BlockTags.PLANKS).add(set.getPlankRelatives().getMain());
				this.valueBuilder(BlockTags.WOODEN_BUTTONS).add(set.getPlankRelatives().get(BlockFamily.Variant.BUTTON));
				this.valueBuilder(BlockTags.WOODEN_DOORS).add(set.getPlankRelatives().get(BlockFamily.Variant.DOOR));
				this.valueBuilder(BlockTags.WOODEN_FENCES).add(set.getPlankRelatives().get(BlockFamily.Variant.FENCE));
				this.valueBuilder(BlockTags.FENCE_GATES).add(set.getPlankRelatives().get(BlockFamily.Variant.FENCE_GATE));
				this.valueBuilder(BlockTags.SIGNS).add(set.getPlankRelatives().get(BlockFamily.Variant.SIGN));
				this.valueBuilder(BlockTags.WOODEN_SLABS).add(set.getPlankRelatives().get(BlockFamily.Variant.SLAB));
				this.valueBuilder(BlockTags.WOODEN_STAIRS).add(set.getPlankRelatives().get(BlockFamily.Variant.STAIRS));
				this.valueBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(set.getPlankRelatives().get(BlockFamily.Variant.PRESSURE_PLATE));
				this.valueBuilder(BlockTags.WOODEN_TRAPDOORS).add(set.getPlankRelatives().get(BlockFamily.Variant.TRAPDOOR));
				this.valueBuilder(BlockTags.WALL_SIGNS).add(set.getPlankRelatives().get(BlockFamily.Variant.WALL_SIGN));
				this.valueBuilder(BlockTags.CEILING_HANGING_SIGNS).add(set.getHangingSign());
				this.valueBuilder(BlockTags.WALL_HANGING_SIGNS).add(set.getWallHangingSign());
				this.valueBuilder(BlockTags.WOODEN_SHELVES).add(set.getShelf());
			}
		}

		@Override
		public String getName() {
			return "Automated Wood Set " + super.getName();
		}
	}

	private static class AutomatedItemTags extends BuiltinRegistryTagsProvider.ItemTagsProvider {

		private final List<WoodSet> sets;
		private final boolean hasBurnable;
		private final boolean hasNonBurnable;

		public AutomatedItemTags(List<WoodSet> sets, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future, AutomatedBlockTags blockTagsProvider) {
			super(output, future, blockTagsProvider);
			this.sets = sets;
			this.hasBurnable = blockTagsProvider.hasBurnable;
			this.hasNonBurnable = blockTagsProvider.hasNonBurnable;
		}

		@Override
		protected void addTags(HolderLookup.Provider registries) {
			for (WoodSet set : this.sets) {
				this.copy(set.getLogsBlockTag(), set.getLogsItemTag());
				this.valueBuilder(ItemTags.BOATS).add(set.getBoatItem());
				this.valueBuilder(ItemTags.CHEST_BOATS).add(set.getChestBoatItem());
			}
			if (this.hasBurnable) {
				this.copy(BlockItemTags.LOGS_THAT_BURN);
			}
			if (this.hasNonBurnable) {
				this.copy(BlockItemTags.LOGS);
			}
			this.copy(BlockItemTags.LEAVES);
			this.copy(BlockItemTags.SAPLINGS);
			this.copy(BlockItemTags.PLANKS);
			this.copy(BlockItemTags.WOODEN_BUTTONS);
			this.copy(BlockItemTags.WOODEN_DOORS);
			this.copy(BlockItemTags.WOODEN_FENCES);
			this.copy(BlockItemTags.FENCE_GATES);
			this.copy(BlockItemTags.SIGNS);
			this.copy(BlockItemTags.WOODEN_SLABS);
			this.copy(BlockItemTags.WOODEN_STAIRS);
			this.copy(BlockItemTags.WOODEN_PRESSURE_PLATES);
			this.copy(BlockItemTags.WOODEN_TRAPDOORS);
			this.copy(BlockItemTags.HANGING_SIGNS);
			this.copy(BlockItemTags.WOODEN_SHELVES);
		}

		@Override
		public String getName() {
			return "Automated Wood Set " + super.getName();
		}
	}

	private static class AutomatedEntityTypeTags extends BuiltinRegistryTagsProvider.EntityTypeTagsProvider {

		private final List<WoodSet> sets;

		protected AutomatedEntityTypeTags(List<WoodSet> sets, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
			this.sets = sets;
		}

		@Override
		protected void addTags(HolderLookup.Provider registries) {
			for (WoodSet set : this.sets) {
				this.valueBuilder(EntityTypeTags.BOAT).add(set.getBoatEntityType());
			}
		}

		@Override
		public String getName() {
			return "Automated Wood Set " + super.getName();
		}
	}

	private static class AutomatedPlankRelativesTranslations extends BlockRelativesFinalDataHandler.AutomatedBlockRelativesTranslations {

		public AutomatedPlankRelativesTranslations(List<BlockRelatives> relatives, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(relatives, output, future);
		}

		@Override
		public String getName() {
			return "Wood Set " + super.getName();
		}
	}

	private static class AutomatedPlankRelativesModels extends BlockRelativesFinalDataHandler.AutomatedBlockRelativesModels {

		public AutomatedPlankRelativesModels(List<BlockRelatives> relatives, FabricPackOutput output) {
			super(relatives, output);
		}

		@Override
		public String getName() {
			return "Wood Set " + super.getName();
		}
	}

	private static class AutomatedPlankRelativesBlockLootTables extends BlockRelativesFinalDataHandler.AutomatedBlockRelativesBlockLootTables {

		public AutomatedPlankRelativesBlockLootTables(List<BlockRelatives> relatives, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(relatives, output, future);
		}

		@Override
		public String getName() {
			return "Wood Set " + super.getName();
		}
	}

	private static class AutomatedPlankRelativesRecipes extends BlockRelativesFinalDataHandler.AutomatedBlockRelativesRecipes {

		public AutomatedPlankRelativesRecipes(List<BlockRelatives> relatives, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(relatives, output, future);
		}

		@Override
		public String getName() {
			return "Wood Set " + super.getName();
		}
	}
}
