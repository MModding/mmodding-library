package com.mmodding.library.feature.api.replication;

import com.mmodding.library.core.api.Reference;
import com.mmodding.library.feature.impl.replication.FeatureReplicatorImpl;
import com.mmodding.library.registry.WaitingRegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.function.Function;

public interface FeatureReplicator {

	static <FC extends FeatureConfig> WaitingRegistryEntry<ConfiguredFeature<FC, Feature<FC>>> replicateConfiguredFeature(
		Reference<ConfiguredFeature<?, ?>> reference,
		ConfiguredFeature<?, ?> configuredFeature,
		Function<FC, FC> mutator
	) {
		return FeatureReplicatorImpl.replicateConfiguredFeature(reference, configuredFeature, mutator);
	}

	static WaitingRegistryEntry<PlacedFeature> replicatePlacedFeature(
		Reference<PlacedFeature> reference,
		PlacedFeature placedFeature,
		Function<PlacementModifiers, PlacementModifiers> mutator
	) {
		return FeatureReplicatorImpl.replicatePlacedFeature(reference, placedFeature, mutator);
	}
}
