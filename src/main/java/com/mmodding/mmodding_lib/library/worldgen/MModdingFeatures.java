package com.mmodding.mmodding_lib.library.worldgen;

import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import com.mmodding.mmodding_lib.library.worldgen.features.differeds.DifferedDripstoneClusterFeature;
import com.mmodding.mmodding_lib.library.worldgen.features.differeds.DifferedLargeDripstoneFeature;
import com.mmodding.mmodding_lib.library.worldgen.features.differeds.DifferedPointedDripstoneFeature;
import com.mmodding.mmodding_lib.library.worldgen.features.differeds.DifferedLiquidVegetationPatch;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class MModdingFeatures {

	public static final Feature<DifferedDripstoneClusterFeature.Config> DIFFERED_DRIPSTONE_CLUSTER = MModdingFeatures.register(
		"differed_dripstone_cluster", new DifferedDripstoneClusterFeature(DifferedDripstoneClusterFeature.Config.CODEC)
	);

	public static final Feature<DifferedLargeDripstoneFeature.Config> DIFFERED_LARGE_DRIPSTONE = MModdingFeatures.register(
		"differed_large_dripstone", new DifferedLargeDripstoneFeature(DifferedLargeDripstoneFeature.Config.CODEC)
	);

	public static final Feature<DifferedPointedDripstoneFeature.Config> DIFFERED_POINTED_DRIPSTONE = MModdingFeatures.register(
		"differed_pointed_dripstone", new DifferedPointedDripstoneFeature(DifferedPointedDripstoneFeature.Config.CODEC)
	);

	public static final Feature<DifferedLiquidVegetationPatch.Config> DIFFERED_LIQUID_VEGETATION_PATCH = MModdingFeatures.register(
		"differed_liquid_vegetation_patch", new DifferedLiquidVegetationPatch(DifferedLiquidVegetationPatch.Config.CODEC)
	);

	private static <C extends FeatureConfig, F extends Feature<C>> F register(String path, F feature) {
		RegistrationUtils.registerFeatureWithoutConfiguredAndPlaced(new MModdingIdentifier(path), feature);
		return feature;
	}
}
