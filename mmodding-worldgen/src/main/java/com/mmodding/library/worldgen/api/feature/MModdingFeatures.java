package com.mmodding.library.worldgen.api.feature;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.worldgen.api.feature.catalog.AdvancedDripstoneClusterFeature;
import com.mmodding.library.worldgen.api.feature.catalog.AdvancedFreezeTopLayerFeature;
import com.mmodding.library.worldgen.api.feature.catalog.AdvancedLargeDripstoneFeature;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class MModdingFeatures {

	public static final Feature<AdvancedFreezeTopLayerFeature.Config> ADVANCED_FREEZE_TOP_LAYER = register("advanced_freeze_top_layer", new AdvancedFreezeTopLayerFeature(AdvancedFreezeTopLayerFeature.Config.CODEC));
	public static final Feature<AdvancedDripstoneClusterFeature.Config> ADVANCED_DRIPSTONE_CLUSTER = register("advanced_dripstone_cluster", new AdvancedDripstoneClusterFeature(AdvancedDripstoneClusterFeature.Config.CODEC));
	public static final Feature<AdvancedLargeDripstoneFeature.Config> ADVANCED_LARGE_DRIPSTONE = register("advanced_large_dripstone", new AdvancedLargeDripstoneFeature(AdvancedLargeDripstoneFeature.Config.CODEC));

	private static <C extends FeatureConfig, F extends Feature<C>> F register(String path, F feature) {
		return Registry.register(Registries.FEATURE, MModdingLibrary.createId(path), feature);
	}
}
