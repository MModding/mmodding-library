package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.List;

public interface CustomFeature {

	Feature<?> getFeature();

	ConfiguredFeature<?, ?> getConfiguredFeature();

	PlacedFeature getDefaultPlacedFeature();

	List<Pair<PlacedFeature, String>> getAdditionalPlacedFeatures();
}
