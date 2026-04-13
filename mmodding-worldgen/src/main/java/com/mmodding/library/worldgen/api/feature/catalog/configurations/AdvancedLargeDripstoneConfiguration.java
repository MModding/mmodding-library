package com.mmodding.library.worldgen.api.feature.catalog.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AdvancedLargeDripstoneConfiguration extends LargeDripstoneConfiguration {

	public static final Codec<AdvancedLargeDripstoneConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		BlockStateProvider.CODEC.fieldOf("dripstone_block").forGetter(config -> config.dripstoneBlock),
		Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").orElse(30).forGetter(config -> config.floorToCeilingSearchRange),
		IntProviders.codec(1, 60).fieldOf("column_radius").forGetter(config -> config.columnRadius),
		FloatProviders.codec(0.0F, 20.0F).fieldOf("height_scale").forGetter(config -> config.heightScale),
		Codec.floatRange(0.1F, 1.0F).fieldOf("max_column_radius_to_cave_height_ratio").forGetter(config -> config.maxColumnRadiusToCaveHeightRatio),
		FloatProviders.codec(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter(config -> config.stalactiteBluntness),
		FloatProviders.codec(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter(config -> config.stalagmiteBluntness),
		FloatProviders.codec(0.0F, 2.0F).fieldOf("wind_speed").forGetter(config -> config.windSpeed),
		Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter(config -> config.minRadiusForWind),
		Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter(config -> config.minBluntnessForWind)
	).apply(instance, AdvancedLargeDripstoneConfiguration::new));

	public final BlockStateProvider dripstoneBlock;

	public AdvancedLargeDripstoneConfiguration(BlockStateProvider dripstoneBlock, int floorToCeilingSearchRange, IntProvider columnRadius, FloatProvider heightScale, float maxColumnRadiusToCaveHeightRatio, FloatProvider stalactiteBluntness, FloatProvider stalagmiteBluntness, FloatProvider windSpeed, int minRadiusForWind, float minBluntnessForWind) {
		super(floorToCeilingSearchRange, columnRadius, heightScale, maxColumnRadiusToCaveHeightRatio, stalactiteBluntness, stalagmiteBluntness, windSpeed, minRadiusForWind, minBluntnessForWind);
		this.dripstoneBlock = dripstoneBlock;
	}
}
