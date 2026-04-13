package com.mmodding.library.worldgen.api.feature.catalog.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AdvancedDripstoneClusterConfiguration extends DripstoneClusterConfiguration {

	public static final Codec<AdvancedDripstoneClusterConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		BlockStateProvider.CODEC.fieldOf("pointed_dripstone_block").forGetter(config -> config.pointedDripstoneBlock),
		BlockStateProvider.CODEC.fieldOf("dripstone_block").forGetter(config -> config.dripstoneBlock),
		Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").forGetter(config -> config.floorToCeilingSearchRange),
		IntProviders.codec(1, 128).fieldOf("height").forGetter(config -> config.height),
		IntProviders.codec(1, 128).fieldOf("radius").forGetter(config -> config.radius),
		Codec.intRange(0, 64).fieldOf("max_stalagmite_stalactite_height_diff").forGetter(config -> config.maxStalagmiteStalactiteHeightDiff),
		Codec.intRange(1, 64).fieldOf("height_deviation").forGetter(config -> config.heightDeviation),
		IntProviders.codec(0, 128).fieldOf("dripstone_block_layer_thickness").forGetter(config -> config.dripstoneBlockLayerThickness),
		FloatProviders.codec(0.0F, 2.0F).fieldOf("density").forGetter(config -> config.density),
		FloatProviders.codec(0.0F, 2.0F).fieldOf("wetness").forGetter(config -> config.wetness),
		Codec.floatRange(0.0F, 1.0F)
			.fieldOf("chance_of_dripstone_column_at_max_distance_from_center")
			.forGetter(config -> config.chanceOfDripstoneColumnAtMaxDistanceFromCenter),
		Codec.intRange(1, 64)
			.fieldOf("max_distance_from_edge_affecting_chance_of_dripstone_column")
			.forGetter(config -> config.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn),
		Codec.intRange(1, 64).fieldOf("max_distance_from_center_affecting_height_bias").forGetter(config -> config.maxDistanceFromCenterAffectingHeightBias)
	).apply(instance, AdvancedDripstoneClusterConfiguration::new));

	public final BlockStateProvider pointedDripstoneBlock;
	public final BlockStateProvider dripstoneBlock;

	public AdvancedDripstoneClusterConfiguration(BlockStateProvider pointedDripstoneBlock, BlockStateProvider dripstoneBlock, int floorToCeilingSearchRange, IntProvider height, IntProvider radius, int maxStalagmiteStalactiteHeightDiff, int heightDeviation, IntProvider dripstoneBlockLayerThickness, FloatProvider density, FloatProvider wetness, float wetnessMean, int maxDistanceFromCenterAffectingChanceOfDripstoneColumn, int maxDistanceFromCenterAffectingHeightBias) {
		super(floorToCeilingSearchRange, height, radius, maxStalagmiteStalactiteHeightDiff, heightDeviation, dripstoneBlockLayerThickness, density, wetness, wetnessMean, maxDistanceFromCenterAffectingChanceOfDripstoneColumn, maxDistanceFromCenterAffectingHeightBias);
		this.pointedDripstoneBlock = pointedDripstoneBlock;
		this.dripstoneBlock = dripstoneBlock;
	}
}
