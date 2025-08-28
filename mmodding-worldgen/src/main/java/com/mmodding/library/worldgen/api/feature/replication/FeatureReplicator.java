package com.mmodding.library.worldgen.api.feature.replication;

import com.mmodding.library.core.api.registry.WaitingRegistryEntry;
import com.mmodding.library.java.api.function.SingleTypeFunction;
import com.mmodding.library.worldgen.impl.feature.replication.FeatureReplicatorImpl;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

public interface FeatureReplicator {

	/**
	 * Allows to replicate a {@link ConfiguredFeature} to create a new {@link ConfiguredFeature} in a {@link WaitingRegistryEntry}
	 * @param key the key of the newly created configured feature
	 * @param configuredFeature the source configured feature
	 * @param mutator the mutator of the feature config
	 * @return the configured feature's waiting registry entry
	 * @param <FC> the feature config
	 */
	static <FC extends FeatureConfig> WaitingRegistryEntry<ConfiguredFeature<FC, Feature<FC>>> replicateConfiguredFeature(
		RegistryKey<ConfiguredFeature<?, ?>> key,
		ConfiguredFeature<?, ?> configuredFeature,
		SingleTypeFunction<FC> mutator
	) {
		return FeatureReplicatorImpl.replicateConfiguredFeature(key, configuredFeature, mutator);
	}

	/**
	 * Allows to replicate a {@link PlacedFeature} to create a new {@link PlacedFeature} in a {@link WaitingRegistryEntry}
	 * @param key the key of the newly created placed feature
	 * @param placedFeature the source placed feature
	 * @param mutator the mutator of the placement modifiers
	 * @return the placed feature's waiting registry entry
	 */
	static WaitingRegistryEntry<PlacedFeature> replicatePlacedFeature(
		RegistryKey<PlacedFeature> key,
		PlacedFeature placedFeature,
		SingleTypeFunction<PlacementModifiers> mutator
	) {
		return FeatureReplicatorImpl.replicatePlacedFeature(key, placedFeature, mutator);
	}
}
