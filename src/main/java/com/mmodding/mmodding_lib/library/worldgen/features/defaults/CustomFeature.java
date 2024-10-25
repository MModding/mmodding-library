package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import com.mmodding.mmodding_lib.library.utils.BiList;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;

public interface CustomFeature {

	Feature<?> getFeature();

	ConfiguredFeature<?, ?> getConfiguredFeature();

	PlacedFeature getDefaultPlacedFeature();

	BiList<PlacedFeature, String> getAdditionalPlacedFeatures();
}
