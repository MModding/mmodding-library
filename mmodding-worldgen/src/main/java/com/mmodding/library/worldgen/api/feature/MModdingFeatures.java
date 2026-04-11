package com.mmodding.library.worldgen.api.feature;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.worldgen.api.feature.catalog.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class MModdingFeatures {

	public static final Feature<AdvancedFreezeTopLayerFeature.Config> ADVANCED_FREEZE_TOP_LAYER = register("advanced_freeze_top_layer", new AdvancedFreezeTopLayerFeature(AdvancedFreezeTopLayerFeature.Config.CODEC));
	public static final Feature<AdvancedDripstoneClusterFeature.Config> ADVANCED_DRIPSTONE_CLUSTER = register("advanced_dripstone_cluster", new AdvancedDripstoneClusterFeature(AdvancedDripstoneClusterFeature.Config.CODEC));
	public static final Feature<AdvancedSmallDripstoneFeature.Config> ADVANCED_SMALL_DRIPSTONE = register("advanced_small_dripstone", new AdvancedSmallDripstoneFeature(AdvancedSmallDripstoneFeature.Config.CODEC));
	public static final Feature<AdvancedLargeDripstoneFeature.Config> ADVANCED_LARGE_DRIPSTONE = register("advanced_large_dripstone", new AdvancedLargeDripstoneFeature(AdvancedLargeDripstoneFeature.Config.CODEC));
	public static final Feature<AdvancedLiquidVegetationPatchFeature.Config> ADVANCED_LIQUID_VEGETATION_PATCH = register("advanced_liquid_vegetation_patch", new AdvancedLiquidVegetationPatchFeature(AdvancedLiquidVegetationPatchFeature.Config.CODEC));
	public static final Feature<LayeredFeature.Config> LAYERED = register("layered", new LayeredFeature(LayeredFeature.Config.CODEC));

	private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String path, F feature) {
		return Registry.register(BuiltInRegistries.FEATURE, MModdingLibrary.createId(path), feature);
	}
}
