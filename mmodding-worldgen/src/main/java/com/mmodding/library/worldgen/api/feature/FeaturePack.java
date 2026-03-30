package com.mmodding.library.worldgen.api.feature;

import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.worldgen.impl.feature.FeaturePackImpl;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.function.Consumer;

public interface FeaturePack<FC extends FeatureConfig> {

	/**
	 * Creates a new {@link FeaturePack} for a {@link Feature}.
	 * @param feature the feature
	 * @return the feature pack
	 * @param <FC> the feature config
	 */
	static <FC extends FeatureConfig> FeaturePack<FC> of(Feature<FC> feature) {
		return new FeaturePackImpl<>(feature);
	}

	/**
	 * Adds a {@link ConfiguredFeaturePack} in this {@link FeaturePack}.
	 * @param key the configured feature's key
	 * @param featureConfig the feature config
	 * @param action the action handling placed features
	 * @return the feature pack
	 */
	FeaturePack<FC> appendConfiguredFeature(RegistryKey<ConfiguredFeature<?, ?>> key, FC featureConfig, Consumer<ConfiguredFeaturePack<FC>> action);

	/**
	 * Adds a {@link ConfiguredFeaturePack} from an existing configured feature.
	 * @param source the configured feature source's key
	 * @param key the configured feature's key
	 * @param patcher the feature config patcher
	 * @param action the action handling placed features
	 * @return the feature pack
	 * @apiNote In case you depend on external mods for those configured features, make sure the source mod's resources are loaded before yours, so that the data generator does not fail.
	 */
	FeaturePack<FC> replicateConfiguredFeature(RegistryKey<ConfiguredFeature<?, ?>> source, RegistryKey<ConfiguredFeature<?, ?>> key, AutoMapper<FC> patcher, Consumer<ConfiguredFeaturePack<FC>> action);

	/**
	 * Registers configured features of this feature pack.
	 * @param configuredFeatures the configured feature registry
	 */
	void registerConfiguredFeatures(Registerable<ConfiguredFeature<?, ?>> configuredFeatures);

	/**
	 * Registers placed features of this feature pack.
	 * @param placedFeatures the placed feature registry
	 */
	void registerPlacedFeatures(Registerable<PlacedFeature> placedFeatures);

	interface ConfiguredFeaturePack<FC extends FeatureConfig> {

		/**
		 * Adds a new {@link PlacedFeature} in this {@link ConfiguredFeaturePack<FC>}.
		 * @param key the placed feature's key
		 * @param modifiers the placement modifiers
		 * @return the configured feature pack
		 */
		ConfiguredFeaturePack<FC> appendPlacedFeature(RegistryKey<PlacedFeature> key, PlacementModifier... modifiers);

		/**
		 * Adds a {@link ConfiguredFeaturePack} from an existing placed feature.
		 * @param source the placed feature source's key
		 * @param key the placed feature's key
		 * @param patcher the placement modifiers patcher
		 * @return the configured feature pack
		 * @apiNote In case you depend on external mods for those placed features, make sure the source mod's resources are loaded before yours, so that the data generator does not fail.
		 */
		ConfiguredFeaturePack<FC> replicatePlacedFeature(RegistryKey<PlacedFeature> source, RegistryKey<PlacedFeature> key, Consumer<PlacementModifiers> patcher);
	}
}
