package com.mmodding.library.levelgen.impl;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.levelgen.api.feature.catalog.AdvancedLargeDripstoneFeature;
import com.mmodding.library.levelgen.api.feature.catalog.AdvancedLiquidVegetationPatchFeature;
import com.mmodding.library.levelgen.api.feature.catalog.AdvancedSnowAndFreezeFeature;
import com.mmodding.library.levelgen.api.feature.catalog.LayeredFeature;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class MModdingLevelgenInitializer implements ModInitializer {

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.FEATURE_TYPE, MModdingLibrary.createId("advanced_snow_and_freeze"), AdvancedSnowAndFreezeFeature.CODEC);
		Registry.register(BuiltInRegistries.FEATURE_TYPE, MModdingLibrary.createId("advanced_large_dripstone"), AdvancedLargeDripstoneFeature.CODEC);
		Registry.register(BuiltInRegistries.FEATURE_TYPE, MModdingLibrary.createId("advanced_liquid_vegetation_patch"), AdvancedLiquidVegetationPatchFeature.CODEC);
		Registry.register(BuiltInRegistries.FEATURE_TYPE, MModdingLibrary.createId("layered"), LayeredFeature.CODEC);
	}
}
