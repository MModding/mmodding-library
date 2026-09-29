package com.mmodding.library.levelgen.api.feature.catalog;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.LargeDripstoneFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/**
 * Feature variant of the vanilla one with <code>DRIPSTONE_BLOCKS</code> constants modified through mixins.
 */
public class AdvancedLargeDripstoneFeature extends LargeDripstoneFeature {

	public static final MapCodec<AdvancedLargeDripstoneFeature> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
		BlockStateProvider.CODEC.fieldOf("dripstone_block").forGetter(a -> a.dripstoneBlock),
		RegistryCodecs.holderSet(Registries.BLOCK).fieldOf("replaceable_blocks").forGetter(LargeDripstoneFeature::replaceableBlocks),
		Codec.intRange(1, 512).optionalFieldOf("floor_to_ceiling_search_range", 30).forGetter(LargeDripstoneFeature::floorToCeilingSearchRange),
		IntProviders.codec(1, 16).fieldOf("column_radius").forGetter(LargeDripstoneFeature::columnRadius), FloatProviders.codec(0.0F, 20.0F).fieldOf("height_scale").forGetter(LargeDripstoneFeature::heightScale),
		Codec.floatRange(0.1F, 1.0F).fieldOf("max_column_radius_to_cave_height_ratio").forGetter(LargeDripstoneFeature::maxColumnRadiusToCaveHeightRatio),
		FloatProviders.codec(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter(LargeDripstoneFeature::stalactiteBluntness),
		FloatProviders.codec(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter(LargeDripstoneFeature::stalagmiteBluntness),
		FloatProviders.codec(0.0F, 2.0F).fieldOf("wind_speed").forGetter(LargeDripstoneFeature::windSpeed),
		Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter(LargeDripstoneFeature::minRadiusForWind),
		Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter(LargeDripstoneFeature::minBluntnessForWind)
	).apply(i, AdvancedLargeDripstoneFeature::new));

	public final Holder<BlockStateProvider> dripstoneBlock;

	public AdvancedLargeDripstoneFeature(
		Holder<BlockStateProvider> dripstoneBlock,
		HolderSet<Block> replaceableBlocks,
		int floorToCeilingSearchRange,
		IntProvider columnRadius,
		FloatProvider heightScale,
		float maxColumnRadiusToCaveHeightRatio,
		FloatProvider stalactiteBluntness,
		FloatProvider stalagmiteBluntness,
		FloatProvider windSpeed,
		int minRadiusForWind,
		float minBluntnessForWind
	) {
		super(replaceableBlocks, floorToCeilingSearchRange, columnRadius, heightScale, maxColumnRadiusToCaveHeightRatio, stalactiteBluntness, stalagmiteBluntness, windSpeed, minRadiusForWind, minBluntnessForWind);
		this.dripstoneBlock = dripstoneBlock;
	}

	@Override
	public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		return super.place(level, chunkGenerator, random, origin);
	}

	// TODO: modify hardcoded dripstone block constants by mixins
	public static class AdvancedLargeDripstone extends LargeDripstone {

		public final BlockStateProvider dripstoneBlock;

		public AdvancedLargeDripstone(BlockStateProvider dripstoneBlock, BlockPos root, boolean pointingUp, int radius, double bluntness, double scale) {
			super(root, pointingUp, radius, bluntness, scale);
			this.dripstoneBlock = dripstoneBlock;
		}
	}
}
