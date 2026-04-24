package com.mmodding.library.worldgen.api.feature;

import com.mmodding.library.worldgen.impl.feature.ConfiguredFeaturePackImpl;
import java.util.List;
import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public interface ConfiguredFeaturePack<FC extends FeatureConfiguration> {

	/**
	 * Creates a new {@link ConfiguredFeaturePack} for a {@link ConfiguredFeature}.
	 * @param key the configured feature's key
	 * @return the configured feature pack
	 * @param <FC> the feature config
	 */
	static <FC extends FeatureConfiguration> ConfiguredFeaturePack<FC> of(ResourceKey<ConfiguredFeature<?, ?>> key) {
		return new ConfiguredFeaturePackImpl<>(key);
	}

	/**
	 * Adds a new {@link PlacedFeature} in this {@link ConfiguredFeaturePack<FC>}.
	 * @param key the placed feature's key
	 * @param modifiers the placement modifiers
	 * @return the configured feature pack
	 */
	ConfiguredFeaturePack<FC> appendPlacedFeature(ResourceKey<PlacedFeature> key, PlacementModifier... modifiers);

	/**
	 * Adds a new {@link PlacedFeature} in this {@link ConfiguredFeaturePack<FC>}.
	 * @param key the placed feature's key
	 * @param modifiers the placement modifiers
	 * @return the configured feature pack
	 */
	ConfiguredFeaturePack<FC> appendPlacedFeature(ResourceKey<PlacedFeature> key, List<PlacementModifier> modifiers);

	/**
	 * Adds a {@link ConfiguredFeaturePack} from an existing placed feature.
	 * @param source the placed feature source's key
	 * @param key the placed feature's key
	 * @param patcher the placement modifiers patcher
	 * @return the configured feature pack
	 * @apiNote In case you depend on external mods for those placed features, make sure the source mod's resources are loaded before yours, so that the data generator does not fail.
	 */
	ConfiguredFeaturePack<FC> replicatePlacedFeature(ResourceKey<PlacedFeature> source, ResourceKey<PlacedFeature> key, Consumer<PlacementModifiers> patcher);

	/**
	 * Registers this configured feature pack content.
	 * @param configuredFeatureRef the configured feature reference
	 * @param registrable the registrable
	 */
	void register(Holder<ConfiguredFeature<?, ?>> configuredFeatureRef, FabricDynamicRegistryProvider.Entries registrable);
}
