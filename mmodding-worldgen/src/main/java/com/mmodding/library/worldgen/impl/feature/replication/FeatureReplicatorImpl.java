package com.mmodding.library.worldgen.impl.feature.replication;

import com.mmodding.library.core.api.Reference;
import com.mmodding.library.core.api.registry.WaitingRegistryEntry;
import com.mmodding.library.java.api.function.SingleTypeFunction;
import com.mmodding.library.worldgen.api.feature.replication.PlacementModifiers;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.ArrayList;
import java.util.List;

public class FeatureReplicatorImpl {

	@SuppressWarnings("unchecked")
	public static <FC extends FeatureConfig> WaitingRegistryEntry<ConfiguredFeature<FC, Feature<FC>>> replicateConfiguredFeature(Reference<ConfiguredFeature<?, ?>> reference, ConfiguredFeature<?, ?> configuredFeature, SingleTypeFunction<FC> mutator) {
		Reference<ConfiguredFeature<FC, Feature<FC>>> castedReference = (Reference<ConfiguredFeature<FC, Feature<FC>>>) (Reference<?>) reference;
		ConfiguredFeature<FC, Feature<FC>> castedConfiguredFeature = (ConfiguredFeature<FC, Feature<FC>>) configuredFeature;
		ConfigReplicator<FC> replicator = new ConfigReplicator<>(castedConfiguredFeature);
		replicator.mutateConfig(mutator);
		FC featureConfig = replicator.replicate();
		return new WaitingRegistryEntry<>(castedReference, new ConfiguredFeature<>(castedConfiguredFeature.feature(), featureConfig));
	}

	public static WaitingRegistryEntry<PlacedFeature> replicatePlacedFeature(Reference<PlacedFeature> reference, PlacedFeature placedFeature, SingleTypeFunction<PlacementModifiers> mutator) {
		PlacementModifiersReplicator replicator = new PlacementModifiersReplicator(placedFeature);
		replicator.mutatePlacementModifiers(mutator);
		List<PlacementModifier> placementModifiers = replicator.replicate();
		return new WaitingRegistryEntry<>(reference, new PlacedFeature(placedFeature.feature(), placementModifiers));
	}

	private static class ConfigReplicator<FC extends FeatureConfig> {

		private FC featureConfig;

		public ConfigReplicator(ConfiguredFeature<FC, Feature<FC>> configuredFeature) {
			this.featureConfig = configuredFeature.config();
		}

		public void mutateConfig(SingleTypeFunction<FC> mutator) {
			this.featureConfig = mutator.apply(this.featureConfig);
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

		public void mutatePlacementModifiers(SingleTypeFunction<PlacementModifiers> mutator) {
			this.placementModifiers = mutator.apply(this.placementModifiers);
		}

		public List<PlacementModifier> replicate() {
			return new ArrayList<>(((PlacementModifiersImpl) this.placementModifiers).retrieve());
		}
	}
}
