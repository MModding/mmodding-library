package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;

public interface CustomFeature {

	Feature<?> getFeature();

	ConfiguredFeature<?, ?> getConfiguredFeature();

	PlacedFeature getPlacedFeature();
}
