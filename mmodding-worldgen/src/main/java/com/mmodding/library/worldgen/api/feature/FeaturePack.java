package com.mmodding.library.worldgen.api.feature;

import com.mmodding.library.core.api.Reference;
import com.mmodding.library.core.api.registry.WaitingRegistryEntry;
import com.mmodding.library.worldgen.impl.feature.FeaturePackImpl;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface FeaturePack<FC extends FeatureConfig> {

	/**
	 * Allows to create a new {@link FeaturePack} made around a {@link Feature}
	 * @param feature the feature
	 * @return the feature pack
	 * @param <FC> the feature config
	 */
	static <FC extends FeatureConfig> FeaturePack<FC> of(Supplier<Feature<FC>> feature) {
		return new FeaturePackImpl<>(feature);
	}

	/**
	 * Returns the {@link Feature} for which the pack is made around
	 * @return the feature
	 */
	Feature<FC> getFeature();

	/**
	 * Adds a {@link ConfiguredFeaturePack} in this {@link FeaturePack}
	 * @param reference the configured feature's reference
	 * @param featureConfig the feature config
	 * @param action the action handling placed features
	 */
	void appendConfiguredFeature(
		Reference<ConfiguredFeature<FC, Feature<FC>>> reference,
		FC featureConfig,
		Consumer<FeaturePack.ConfiguredFeaturePack<FC>> action
	);

	/**
	 * Adds a {@link ConfiguredFeaturePack} in this {@link FeaturePack}
	 * @param configuredFeature the configured feature's waiting registry entry
	 * @param action the action handling placed features
	 */
	void appendConfiguredFeature(
		WaitingRegistryEntry<ConfiguredFeature<FC, Feature<FC>>> configuredFeature,
		Consumer<FeaturePack.ConfiguredFeaturePack<FC>> action
	);

	/**
	 * Allows to retrieve the current list of all {@link ConfiguredFeaturePack} that are in this {@link FeaturePack<FC>}
	 * @return the list of all current configured feature packs
	 */
	List<ConfiguredFeaturePack<FC>> getConfiguredFeaturePacks();

	/**
	 * Allows to register all elements that are in this pack
	 * @param configuredFeatures the configured feature registry
	 * @param placedFeatures the placed feature registry
	 */
	void register(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures);

	interface ConfiguredFeaturePack<FC extends FeatureConfig> {

		/**
		 * Returns the {@link ConfiguredFeature} for which the pack is made around
		 * @return the configured feature
		 */
		ConfiguredFeature<FC, Feature<FC>> getConfiguredFeature();

		/**
		 * Adds a new {@link PlacedFeature} in this {@link ConfiguredFeaturePack<FC>}
		 * @param reference the placed feature's reference
		 * @param modifiers the placement modifiers
		 */
		void appendPlacedFeature(Reference<PlacedFeature> reference, PlacementModifier... modifiers);

		/**
		 * Adds a new {@link PlacedFeature} in this {@link ConfiguredFeaturePack<FC>}
		 * @param placedFeature the placed feature's waiting registry entry
		 */
		void appendPlacedFeature(WaitingRegistryEntry<PlacedFeature> placedFeature);

		/**
		 * Allows to return the current list of all {@link PlacedFeature} that are in this {@link ConfiguredFeaturePack}
		 * @return the list of all current placed features
		 */
		List<PlacedFeature> getPlacedFeatures();
	}
}
