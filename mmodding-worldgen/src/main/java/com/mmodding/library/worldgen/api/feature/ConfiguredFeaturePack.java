package com.mmodding.library.worldgen.api.feature;

import com.mmodding.library.worldgen.impl.feature.ConfiguredFeaturePackImpl;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;
import java.util.function.Consumer;

public interface ConfiguredFeaturePack<FC extends FeatureConfig> {

	/**
	 * Creates a new {@link ConfiguredFeaturePack} for a {@link ConfiguredFeature}.
	 * @param key the configured feature's key
	 * @return the configured feature pack
	 * @param <FC> the feature config
	 */
	static <FC extends FeatureConfig> ConfiguredFeaturePack<FC> of(RegistryKey<ConfiguredFeature<?, ?>> key) {
		return new ConfiguredFeaturePackImpl<>(key);
	}

	/**
	 * Adds a new {@link PlacedFeature} in this {@link ConfiguredFeaturePack<FC>}.
	 * @param key the placed feature's key
	 * @param modifiers the placement modifiers
	 * @return the configured feature pack
	 */
	ConfiguredFeaturePack<FC> appendPlacedFeature(RegistryKey<PlacedFeature> key, PlacementModifier... modifiers);

	/**
	 * Adds a new {@link PlacedFeature} in this {@link ConfiguredFeaturePack<FC>}.
	 * @param key the placed feature's key
	 * @param modifiers the placement modifiers
	 * @return the configured feature pack
	 */
	ConfiguredFeaturePack<FC> appendPlacedFeature(RegistryKey<PlacedFeature> key, List<PlacementModifier> modifiers);

	/**
	 * Adds a {@link ConfiguredFeaturePack} from an existing placed feature.
	 * @param source the placed feature source's key
	 * @param key the placed feature's key
	 * @param patcher the placement modifiers patcher
	 * @return the configured feature pack
	 * @apiNote In case you depend on external mods for those placed features, make sure the source mod's resources are loaded before yours, so that the data generator does not fail.
	 */
	ConfiguredFeaturePack<FC> replicatePlacedFeature(RegistryKey<PlacedFeature> source, RegistryKey<PlacedFeature> key, Consumer<PlacementModifiers> patcher);

	/**
	 * Registers placed features of this configured feature pack.
	 * @param placedFeatures the placed feature registry
	 */
	void registerPlacedFeatures(Registerable<PlacedFeature> placedFeatures);
}
