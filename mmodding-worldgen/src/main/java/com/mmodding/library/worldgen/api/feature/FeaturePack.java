package com.mmodding.library.worldgen.api.feature;

import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.worldgen.impl.feature.FeaturePackImpl;
import java.util.function.Consumer;

import net.minecraft.data.worldgen.BootstrapContext;
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
	 * Adds a {@link ConfiguredFeaturePack} from an existing configured feature.
	 * @param source the configured feature source's key
	 * @param key the configured feature's key
	 * @param patcher the feature config patcher
	 * @param action the action handling placed features
	 * @return the feature pack
	 * @apiNote This only works with vanilla entries.
	 */
	FeaturePack<FC> replicateConfiguredFeature(ResourceKey<ConfiguredFeature<?, ?>> source, ResourceKey<ConfiguredFeature<?, ?>> key, AutoMapper<FC> patcher, Consumer<ConfiguredFeaturePack<FC>> action);

	/**
	 * Registers configured features of this feature pack.
	 * @param context the context
	 */
	void registerConfigs(BootstrapContext<ConfiguredFeature<?, ?>> context);

	/**
	 * Registers placed features of this feature pack.
	 * @param context the context
	 */
	void registerPlacements(BootstrapContext<PlacedFeature> context);
}
