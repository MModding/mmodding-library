package com.mmodding.library.datagen.api.family;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.featuretoggle.FeatureFlags;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * We deal with those in only one way.
 */
public final class BlockFamilyProcessor {

	public void process(BlockStateModelGenerator generator, BlockFamily family) {
		if (family.shouldGenerateModels()) {
			generator.registerCubeAllModelTexturePool(family.getBaseBlock()).family(family);
		}
	}

	public void process(Consumer<RecipeJsonProvider> exporter, BlockFamily family) {
		if (family.shouldGenerateRecipes(FeatureFlags.FEATURE_MANAGER.getFeatureSet())) {
			RecipeProvider.generateFamily(exporter, family);
		}
	}

	public void process(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> builderProvider, BlockFamily family) {
		for (Map.Entry<BlockFamily.Variant, Block> entry : family.getVariants().entrySet()) {
			switch (entry.getKey()) {
				case BUTTON -> builderProvider.apply(BlockTags.BUTTONS).add(entry.getValue());
				case DOOR -> builderProvider.apply(BlockTags.DOORS).add(entry.getValue());
				case FENCE -> builderProvider.apply(BlockTags.FENCES).add(entry.getValue());
				case FENCE_GATE -> builderProvider.apply(BlockTags.FENCE_GATES).add(entry.getValue());
				case SIGN -> builderProvider.apply(BlockTags.SIGNS).add(entry.getValue());
				case SLAB -> builderProvider.apply(BlockTags.SLABS).add(entry.getValue());
				case STAIRS -> builderProvider.apply(BlockTags.STAIRS).add(entry.getValue());
				case PRESSURE_PLATE -> builderProvider.apply(BlockTags.PRESSURE_PLATES).add(entry.getValue());
				case TRAPDOOR -> builderProvider.apply(BlockTags.TRAPDOORS).add(entry.getValue());
				case WALL -> builderProvider.apply(BlockTags.WALLS).add(entry.getValue());
				case WALL_SIGN -> builderProvider.apply(BlockTags.WALL_SIGNS).add(entry.getValue());
			}
		}
	}
}
