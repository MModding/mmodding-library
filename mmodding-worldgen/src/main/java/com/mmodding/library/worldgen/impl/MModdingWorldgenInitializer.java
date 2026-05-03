package com.mmodding.library.worldgen.impl;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.worldgen.api.feature.MModdingFeatures;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class MModdingWorldgenInitializer implements ModInitializer {

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.FEATURE, MModdingLibrary.createId("advanced_freeze_top_layer"), MModdingFeatures.ADVANCED_FREEZE_TOP_LAYER);
		Registry.register(BuiltInRegistries.FEATURE, MModdingLibrary.createId("advanced_dripstone_cluster"), MModdingFeatures.ADVANCED_DRIPSTONE_CLUSTER);
		Registry.register(BuiltInRegistries.FEATURE, MModdingLibrary.createId("advanced_small_dripstone"), MModdingFeatures.ADVANCED_SMALL_DRIPSTONE);
		Registry.register(BuiltInRegistries.FEATURE, MModdingLibrary.createId("advanced_large_dripstone"), MModdingFeatures.ADVANCED_LARGE_DRIPSTONE);
		Registry.register(BuiltInRegistries.FEATURE, MModdingLibrary.createId("advanced_liquid_vegetation_patch"), MModdingFeatures.ADVANCED_LIQUID_VEGETATION_PATCH);
		Registry.register(BuiltInRegistries.FEATURE, MModdingLibrary.createId("layered"), MModdingFeatures.LAYERED);
	}
}
