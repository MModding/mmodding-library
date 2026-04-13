package com.mmodding.library.worldgen.api.feature;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.worldgen.api.feature.catalog.*;
import com.mmodding.library.worldgen.api.feature.catalog.configurations.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class MModdingFeatures {

	public static final Feature<AdvancedFreezeTopLayerConfiguration> ADVANCED_FREEZE_TOP_LAYER = register("advanced_freeze_top_layer", new AdvancedFreezeTopLayerFeature(AdvancedFreezeTopLayerConfiguration.CODEC));
	public static final Feature<AdvancedDripstoneClusterConfiguration> ADVANCED_DRIPSTONE_CLUSTER = register("advanced_dripstone_cluster", new AdvancedDripstoneClusterFeature(AdvancedDripstoneClusterConfiguration.CODEC));
	public static final Feature<AdvancedPointedDripstoneConfiguration> ADVANCED_SMALL_DRIPSTONE = register("advanced_small_dripstone", new AdvancedPointedDripstoneFeature(AdvancedPointedDripstoneConfiguration.CODEC));
	public static final Feature<AdvancedLargeDripstoneConfiguration> ADVANCED_LARGE_DRIPSTONE = register("advanced_large_dripstone", new AdvancedLargeDripstoneFeature(AdvancedLargeDripstoneConfiguration.CODEC));
	public static final Feature<AdvancedLiquidVegetationPatchConfiguration> ADVANCED_LIQUID_VEGETATION_PATCH = register("advanced_liquid_vegetation_patch", new AdvancedLiquidVegetationPatchFeature(AdvancedLiquidVegetationPatchConfiguration.CODEC));
	public static final Feature<LayeredConfiguration> LAYERED = register("layered", new LayeredFeature(LayeredConfiguration.CODEC));

	private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String path, F feature) {
		return Registry.register(BuiltInRegistries.FEATURE, MModdingLibrary.createId(path), feature);
	}
}
