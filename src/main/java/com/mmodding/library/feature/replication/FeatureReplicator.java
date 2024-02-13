package com.mmodding.library.feature.replication;

import com.mmodding.library.core.Reference;
import com.mmodding.library.registry.WaitingRegistryEntry;
import net.minecraft.world.gen.feature.*;

import java.util.List;
import java.util.function.Function;

public class FeatureReplicator {

	@SuppressWarnings("unchecked")
	public static <FC extends FeatureConfig> WaitingRegistryEntry<ConfiguredFeature<FC, Feature<FC>>> replicateConfiguredFeature(Reference<ConfiguredFeature<?, ?>> reference, ConfiguredFeature<?, ?> configuredFeature, Function<FC, FC> mutator) {
		Reference<ConfiguredFeature<FC, Feature<FC>>> castedReference = (Reference<ConfiguredFeature<FC, Feature<FC>>>) (Reference<?>) reference;
		ConfiguredFeature<FC, Feature<FC>> castedConfiguredFeature = (ConfiguredFeature<FC, Feature<FC>>) configuredFeature;
		FeatureConfigReplicator<FC> replicator = new FeatureConfigReplicator<>(castedConfiguredFeature);
		replicator.mutateConfig(mutator);
		FC featureConfig = replicator.replicate();
		return new WaitingRegistryEntry<>(castedReference, new ConfiguredFeature<>(castedConfiguredFeature.getFeature(), featureConfig));
	}

	public static WaitingRegistryEntry<PlacedFeature> replicatePlacedFeature(Reference<PlacedFeature> reference, PlacedFeature placedFeature, Function<PlacementModifiers, PlacementModifiers> mutator) {
		FeaturePlacementModifiersReplicator replicator = new FeaturePlacementModifiersReplicator(placedFeature);
		replicator.mutatePlacementModifiers(mutator);
		List<PlacementModifier> placementModifiers = replicator.replicate();
		return new WaitingRegistryEntry<>(reference, new PlacedFeature(placedFeature.feature(), placementModifiers));
	}
}
