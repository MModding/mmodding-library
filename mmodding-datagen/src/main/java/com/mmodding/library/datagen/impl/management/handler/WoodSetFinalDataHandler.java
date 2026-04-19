package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.block.api.catalog.AdvancedLeavesBlock;
import com.mmodding.library.block.impl.wrapper.BlockRelativesImpl;
import com.mmodding.library.datagen.api.lang.DefaultLangProcessors;
import com.mmodding.library.datagen.api.management.handler.FinalDataHandler;
import com.mmodding.library.datagen.api.provider.MModdingLanguageProvider;
import com.mmodding.library.woodset.api.WoodSet;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WoodSetFinalDataHandler implements FinalDataHandler<WoodSet> {

	@Override
	public Class<WoodSet> getType() {
		return WoodSet.class;
	}

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, List<WoodSet> finalContents) {
		List<BlockFamily> extracted = finalContents.stream().map(this::extractFamily).toList();
		pack.addProvider((output, future) -> new AutomatedTranslations(output, future, finalContents));
		pack.addProvider((output, future) -> new AutomatedBlockFamilyTranslations(output, future, extracted));
		pack.addProvider((output, future) -> new AutomatedModels(output, finalContents));
		pack.addProvider((output, future) -> new AutomatedBlockFamilyModels(output, extracted));
		pack.addProvider((output, future) -> new AutomatedRecipes(output, future, finalContents));
		pack.addProvider((output, future) -> new AutomatedBlockFamilyRecipes(output, future, extracted));
		AutomatedBlockTags blockTags = pack.addProvider((output, future) -> new AutomatedBlockTags(output, future, finalContents));
		pack.addProvider((output, future) -> new AutomatedItemTags(output, future, blockTags, finalContents));
	}

	private BlockFamily extractFamily(WoodSet set) {
		return ((BlockRelativesImpl) set.getPlankRelatives()).initDataFamily();
	}

	private static class AutomatedTranslations extends MModdingLanguageProvider {

		private final List<WoodSet> sets;

		protected AutomatedTranslations(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> future, List<WoodSet> sets) {
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
			builder.add(block, DefaultLangProcessors.CHEST_BOAT.process(BuiltInRegistries.BLOCK.getKey(block)));
		}

		@Override
		public String getName() {
			return "Automated Wood Set " + super.getName();
		}
	}

	private static class AutomatedModels extends FabricModelProvider {

		private final List<WoodSet> sets;

		protected AutomatedModels(FabricPackOutput dataOutput, List<WoodSet> sets) {
			super(dataOutput);
			this.sets = sets;
		}

		@Override
		public void generateBlockStateModels(BlockModelGenerators generator) {
			for (WoodSet set : this.sets) {
				BlockModelGenerators.WoodProvider provider = generator.woodProvider(set.getLog()).wood(set.getWood());
				BlockModelGenerators.WoodProvider stripped = generator.woodProvider(set.getStrippedLog()).wood(set.getStrippedWood());
				switch (set.getLogDisplay()) {
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
				generator.createHangingSign(set.getStrippedLog(), set.getHangingSign(), set.getWallHangingSign());
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

	private static class AutomatedRecipes extends FabricRecipeProvider {

		private final List<WoodSet> sets;

		protected AutomatedRecipes(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> future, List<WoodSet> sets) {
			super(dataOutput, future);
			this.sets = sets;
		}

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
			return new RecipeProvider(registries, output) {

				@Override
				public void buildRecipes() {
					for (WoodSet set : AutomatedRecipes.this.sets) {
						this.woodFromLogs(set.getWood(), set.getLog());
						this.woodFromLogs(set.getStrippedWood(), set.getStrippedLog());
						this.planksFromLog(set.getPlankRelatives().getMain(), set.getLogsItemTag(), 4);
						this.hangingSign(set.getHangingSign(), set.getStrippedLog());
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

	private static class AutomatedBlockTags extends FabricTagsProvider.BlockTagsProvider {

		private final List<WoodSet> sets;
		private boolean hasBurnable = false;
		private boolean hasNonBurnable = false;

		protected AutomatedBlockTags(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> future, List<WoodSet> sets) {
			super(dataOutput, future);
			this.sets = sets;
		}

		@Override
		protected void addTags(HolderLookup.Provider registries) {
			for (WoodSet set : this.sets) {
				this.valueLookupBuilder(set.getLogsBlockTag()).add(set.getLog(), set.getWood(), set.getStrippedLog(), set.getStrippedWood());
				if (set.isBurnable()) {
					this.hasBurnable = true;
					this.valueLookupBuilder(BlockTags.LOGS_THAT_BURN).addTag(set.getLogsBlockTag());
				}
				else {
					this.hasNonBurnable = true;
					this.valueLookupBuilder(BlockTags.LOGS).addTag(set.getLogsBlockTag());
				}
				this.valueLookupBuilder(BlockTags.LEAVES).add(set.getLeaves());
				this.valueLookupBuilder(BlockTags.SAPLINGS).add(set.getSapling());
				this.valueLookupBuilder(BlockTags.FLOWER_POTS).add(set.getPottedSapling());
				this.valueLookupBuilder(BlockTags.PLANKS).add(set.getPlankRelatives().getMain());
				this.valueLookupBuilder(BlockTags.WOODEN_BUTTONS).add(set.getPlankRelatives().get(BlockFamily.Variant.BUTTON));
				this.valueLookupBuilder(BlockTags.WOODEN_DOORS).add(set.getPlankRelatives().get(BlockFamily.Variant.DOOR));
				this.valueLookupBuilder(BlockTags.WOODEN_FENCES).add(set.getPlankRelatives().get(BlockFamily.Variant.FENCE));
				this.valueLookupBuilder(BlockTags.FENCE_GATES).add(set.getPlankRelatives().get(BlockFamily.Variant.FENCE_GATE));
				this.valueLookupBuilder(BlockTags.SIGNS).add(set.getPlankRelatives().get(BlockFamily.Variant.SIGN));
				this.valueLookupBuilder(BlockTags.WOODEN_SLABS).add(set.getPlankRelatives().get(BlockFamily.Variant.SLAB));
				this.valueLookupBuilder(BlockTags.WOODEN_STAIRS).add(set.getPlankRelatives().get(BlockFamily.Variant.STAIRS));
				this.valueLookupBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(set.getPlankRelatives().get(BlockFamily.Variant.PRESSURE_PLATE));
				this.valueLookupBuilder(BlockTags.WOODEN_TRAPDOORS).add(set.getPlankRelatives().get(BlockFamily.Variant.TRAPDOOR));
				this.valueLookupBuilder(BlockTags.WALL_SIGNS).add(set.getPlankRelatives().get(BlockFamily.Variant.WALL_SIGN));
				this.valueLookupBuilder(BlockTags.CEILING_HANGING_SIGNS).add(set.getHangingSign());
				this.valueLookupBuilder(BlockTags.WALL_HANGING_SIGNS).add(set.getWallHangingSign());
				this.valueLookupBuilder(BlockTags.WOODEN_SHELVES).add(set.getShelf());
			}
		}

		@Override
		public String getName() {
			return "Automated Wood Set " + super.getName();
		}
	}

	private static class AutomatedItemTags extends FabricTagsProvider.ItemTagsProvider {

		private final List<WoodSet> sets;
		private final boolean hasBurnable;
		private final boolean hasNonBurnable;

		public AutomatedItemTags(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture, AutomatedBlockTags blockTagsProvider, List<WoodSet> sets) {
			super(output, registryLookupFuture, blockTagsProvider);
			this.sets = sets;
			this.hasBurnable = blockTagsProvider.hasBurnable;
			this.hasNonBurnable = blockTagsProvider.hasNonBurnable;
		}

		@Override
		protected void addTags(HolderLookup.Provider registries) {
			for (WoodSet set : this.sets) {
				this.copy(set.getLogsBlockTag(), set.getLogsItemTag());
				this.valueLookupBuilder(ItemTags.BOATS).add(set.getBoatItem());
				this.valueLookupBuilder(ItemTags.CHEST_BOATS).add(set.getChestBoatItem());
			}
			if (this.hasBurnable) {
				this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
			}
			if (this.hasNonBurnable) {
				this.copy(BlockTags.LOGS, ItemTags.LOGS);
			}
			this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
			this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
			this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
			this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
			this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
			this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
			this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
			this.copy(BlockTags.SIGNS, ItemTags.SIGNS);
			this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
			this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
			this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
			this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
			this.copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
			this.copy(BlockTags.WOODEN_SHELVES, ItemTags.WOODEN_SHELVES);
		}

		@Override
		public String getName() {
			return "Automated Wood Set " + super.getName();
		}
	}


	private static class AutomatedBlockFamilyTranslations extends BlockFamilyFinalDataHandler.AutomatedBlockFamilyTranslations {

		public AutomatedBlockFamilyTranslations(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future, List<BlockFamily> families) {
			super(output, future, families);
		}

		@Override
		public String getName() {
			return "Wood Set " + super.getName();
		}
	}

	private static class AutomatedBlockFamilyModels extends BlockFamilyFinalDataHandler.AutomatedBlockFamilyModels {

		public AutomatedBlockFamilyModels(FabricPackOutput output, List<BlockFamily> families) {
			super(output, families);
		}

		@Override
		public String getName() {
			return "Wood Set " + super.getName();
		}
	}

	private static class AutomatedBlockFamilyRecipes extends BlockFamilyFinalDataHandler.AutomatedBlockFamilyRecipes {

		public AutomatedBlockFamilyRecipes(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future, List<BlockFamily> families) {
			super(output, future, families);
		}

		@Override
		public String getName() {
			return "Wood Set " + super.getName();
		}
	}
}
