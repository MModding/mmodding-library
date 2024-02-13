package com.mmodding.library.feature.api;

import com.mmodding.library.core.api.Reference;
import com.mmodding.library.feature.impl.FeaturePackImpl;
import com.mmodding.library.registry.WaitingRegistryEntry;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface FeaturePack<FC extends FeatureConfig> {

	static <FC extends FeatureConfig> FeaturePack<FC> of(Supplier<Feature<FC>> feature) {
		return new FeaturePackImpl<>(feature);
	}

	Feature<FC> getFeature();

	void appendConfiguredFeature(
		Reference<ConfiguredFeature<FC, Feature<FC>>> reference,
		FC featureConfig,
		Consumer<FeaturePack.ConfiguredFeaturePack<FC>> action
	);

	void appendConfiguredFeature(
		WaitingRegistryEntry<ConfiguredFeature<FC, Feature<FC>>> configuredFeature,
		Consumer<FeaturePack.ConfiguredFeaturePack<FC>> action
	);

	List<ConfiguredFeaturePack<FC>> getConfiguredFeaturePacks();

	void register(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures);

	interface ConfiguredFeaturePack<FC extends FeatureConfig> {

		void appendPlacedFeature(Reference<PlacedFeature> reference, PlacementModifier... modifiers);

		void appendPlacedFeature(WaitingRegistryEntry<PlacedFeature> placedFeature);

		ConfiguredFeature<FC, Feature<FC>> getConfiguredFeature();

		List<PlacedFeature> getPlacedFeatures();
	}
}
