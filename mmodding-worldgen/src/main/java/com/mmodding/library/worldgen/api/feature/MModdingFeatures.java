package com.mmodding.library.worldgen.api.feature;

import com.mmodding.library.worldgen.api.feature.catalog.*;
import com.mmodding.library.worldgen.api.feature.catalog.configurations.*;
import net.minecraft.world.level.levelgen.feature.Feature;

public class MModdingFeatures {

	public static final Feature<AdvancedFreezeTopLayerConfiguration> ADVANCED_FREEZE_TOP_LAYER = new AdvancedFreezeTopLayerFeature(AdvancedFreezeTopLayerConfiguration.CODEC);
	public static final Feature<AdvancedDripstoneClusterConfiguration> ADVANCED_DRIPSTONE_CLUSTER = new AdvancedDripstoneClusterFeature(AdvancedDripstoneClusterConfiguration.CODEC);
	public static final Feature<AdvancedPointedDripstoneConfiguration> ADVANCED_SMALL_DRIPSTONE = new AdvancedPointedDripstoneFeature(AdvancedPointedDripstoneConfiguration.CODEC);
	public static final Feature<AdvancedLargeDripstoneConfiguration> ADVANCED_LARGE_DRIPSTONE = new AdvancedLargeDripstoneFeature(AdvancedLargeDripstoneConfiguration.CODEC);
	public static final Feature<AdvancedLiquidVegetationPatchConfiguration> ADVANCED_LIQUID_VEGETATION_PATCH = new AdvancedLiquidVegetationPatchFeature(AdvancedLiquidVegetationPatchConfiguration.CODEC);
	public static final Feature<LayeredConfiguration> LAYERED = new LayeredFeature(LayeredConfiguration.CODEC);
}
