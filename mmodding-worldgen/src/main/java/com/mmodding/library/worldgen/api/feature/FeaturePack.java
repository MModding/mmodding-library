package com.mmodding.library.worldgen.api.feature;

import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.worldgen.impl.feature.FeaturePackImpl;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public interface FeaturePack<FC extends FeatureConfiguration> {

	/**
	 * Creates a new {@link FeaturePack} for a {@link Feature}.
	 * @param feature the feature
	 * @return the feature pack
	 * @param <FC> the feature config
	 */
	static <FC extends FeatureConfiguration> FeaturePack<FC> of(Feature<FC> feature) {
		return new FeaturePackImpl<>(feature);
	}

	/**
	 * Adds a {@link ConfiguredFeaturePack} in this {@link FeaturePack}.
	 * @param key the configured feature's key
	 * @param featureConfig the feature config
	 * @param action the action handling placed features
	 * @return the feature pack
	 */
	FeaturePack<FC> appendConfiguredFeature(ResourceKey<ConfiguredFeature<?, ?>> key, FC featureConfig, Consumer<ConfiguredFeaturePack<FC>> action);

	/**
	 * Adds a {@link ConfiguredFeaturePack} in this {@link FeaturePack} through granted access to the configured feature registry.
	 * @param key the configured feature's key
	 * @param featureConfigFactory the feature config factory
	 * @param action the action handling placed features
	 * @return the feature pack
	 */
	FeaturePack<FC> appendConfiguredFeature(ResourceKey<ConfiguredFeature<?, ?>> key, Function<ConfiguredFeatureLookup<FC>, FC> featureConfigFactory, Consumer<ConfiguredFeaturePack<FC>> action);

	/**
	 * Adds a {@link ConfiguredFeaturePack} from an existing configured feature.
	 * @param source the configured feature source's key
	 * @param key the configured feature's key
	 * @param patcher the feature config patcher
	 * @param action the action handling placed features
	 * @return the feature pack
	 * @apiNote In case you depend on external mods for those configured features, make sure the source mod's resources are loaded before yours, so that the data generator does not fail.
	 */
	FeaturePack<FC> replicateConfiguredFeature(ResourceKey<ConfiguredFeature<?, ?>> source, ResourceKey<ConfiguredFeature<?, ?>> key, AutoMapper<FC> patcher, Consumer<ConfiguredFeaturePack<FC>> action);

	/**
	 * Registers configured features of this feature pack.
	 * @param configuredFeatures the configured feature registry
	 */
	void registerConfiguredFeatures(BootstapContext<ConfiguredFeature<?, ?>> configuredFeatures);

	/**
	 * Registers placed features of this feature pack.
	 * @param placedFeatures the placed feature registry
	 */
	void registerPlacedFeatures(BootstapContext<PlacedFeature> placedFeatures);

	@FunctionalInterface
	interface ConfiguredFeatureLookup<FC extends FeatureConfiguration> {

		/**
		 * Gets a registry entry reference of a configured feature from its registry key.
		 * @param key the registry key
		 * @return the registry entry reference
		 */
		Holder.Reference<ConfiguredFeature<?, ?>> get(ResourceKey<ConfiguredFeature<?, ?>> key);
	}
}
