package com.mmodding.library.worldgen.api.feature.catalog.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AdvancedFreezeTopLayerConfiguration implements FeatureConfiguration {

	public static final Codec<AdvancedFreezeTopLayerConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		BlockStateProvider.CODEC.fieldOf("ice_block").forGetter(config -> config.iceBlock),
		BlockStateProvider.CODEC.fieldOf("snow_layer").forGetter(config -> config.snowLayer),
		IntProviders.POSITIVE_CODEC.fieldOf("depth_coverage").forGetter(config -> config.depthCoverage)
	).apply(instance, AdvancedFreezeTopLayerConfiguration::new));

	public final BlockStateProvider iceBlock;
	public final BlockStateProvider snowLayer;
	public final IntProvider depthCoverage;

	public AdvancedFreezeTopLayerConfiguration(BlockStateProvider iceBlock, BlockStateProvider snowLayer, IntProvider depthCoverage) {
		this.iceBlock = iceBlock;
		this.snowLayer = snowLayer;
		this.depthCoverage = depthCoverage;
	}
}
