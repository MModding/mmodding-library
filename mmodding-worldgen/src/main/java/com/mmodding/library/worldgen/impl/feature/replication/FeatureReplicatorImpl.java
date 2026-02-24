package com.mmodding.library.worldgen.impl.feature.replication;

import com.mmodding.library.core.api.registry.WaitingRegistryEntry;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.worldgen.api.feature.replication.PlacementModifiers;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.ArrayList;
import java.util.List;

public class FeatureReplicatorImpl {

	@SuppressWarnings("unchecked")
	public static <FC extends FeatureConfig> WaitingRegistryEntry<ConfiguredFeature<FC, Feature<FC>>> replicateConfiguredFeature(RegistryKey<ConfiguredFeature<?, ?>> key, ConfiguredFeature<?, ?> configuredFeature, AutoMapper<FC> mutator) {
		RegistryKey<ConfiguredFeature<FC, Feature<FC>>> castedKey = (RegistryKey<ConfiguredFeature<FC, Feature<FC>>>) (RegistryKey<?>) key;
		ConfiguredFeature<FC, Feature<FC>> castedConfiguredFeature = (ConfiguredFeature<FC, Feature<FC>>) configuredFeature;
		ConfigReplicator<FC> replicator = new ConfigReplicator<>(castedConfiguredFeature);
		replicator.mutateConfig(mutator);
		FC featureConfig = replicator.replicate();
		return new WaitingRegistryEntry<>(castedKey, new ConfiguredFeature<>(castedConfiguredFeature.feature(), featureConfig));
	}

	public static WaitingRegistryEntry<PlacedFeature> replicatePlacedFeature(RegistryKey<PlacedFeature> key, PlacedFeature placedFeature, AutoMapper<PlacementModifiers> mutator) {
		PlacementModifiersReplicator replicator = new PlacementModifiersReplicator(placedFeature);
		replicator.mutatePlacementModifiers(mutator);
		List<PlacementModifier> placementModifiers = replicator.replicate();
		return new WaitingRegistryEntry<>(key, new PlacedFeature(placedFeature.feature(), placementModifiers));
	}

	private static class ConfigReplicator<FC extends FeatureConfig> {

		private FC featureConfig;

		public ConfigReplicator(ConfiguredFeature<FC, Feature<FC>> configuredFeature) {
			this.featureConfig = configuredFeature.config();
		}

		public void mutateConfig(AutoMapper<FC> mutator) {
			this.featureConfig = mutator.map(this.featureConfig);
		}

		public FC replicate() {
			return this.featureConfig;
		}
	}

	private static class PlacementModifiersReplicator {

		private PlacementModifiers placementModifiers;

		public PlacementModifiersReplicator(PlacedFeature placedFeature) {
			this.placementModifiers = new PlacementModifiersImpl(placedFeature.placementModifiers());
		}

		public void mutatePlacementModifiers(AutoMapper<PlacementModifiers> mutator) {
			this.placementModifiers = mutator.map(this.placementModifiers);
		}

		public List<PlacementModifier> replicate() {
			return new ArrayList<>(((PlacementModifiersImpl) this.placementModifiers).retrieve());
		}
	}
}
