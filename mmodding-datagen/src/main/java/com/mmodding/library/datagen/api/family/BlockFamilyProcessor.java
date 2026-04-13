package com.mmodding.library.datagen.api.family;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import java.util.Map;
import java.util.function.Function;

/**
 * We deal with those in only one way.
 */
public final class BlockFamilyProcessor {

	public void process(BlockModelGenerators generator, BlockFamily family) {
		if (family.shouldGenerateModel()) {
			generator.family(family.getBaseBlock()).generateFor(family);
		}
	}

	public void process(Function<TagKey<Block>, TagAppender<Block, Block>> builderProvider, BlockFamily family) {
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
