package com.mmodding.library.worldgen.impl.feature;

import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.worldgen.api.feature.FeaturePack;
import com.mmodding.library.worldgen.api.feature.PlacementModifiers;
import com.mmodding.library.worldgen.impl.feature.replication.PlacementModifiersImpl;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class FeaturePackImpl<FC extends FeatureConfig> implements FeaturePack<FC> {

	private final Feature<FC> feature;

	private final List<ConfiguredFeaturePack<FC>> configuredFeaturePacks;

	public FeaturePackImpl(Feature<FC> feature) {
		this.feature = feature;
		this.configuredFeaturePacks = new ArrayList<>();
	}

	@Override
	public FeaturePack<FC> appendConfiguredFeature(RegistryKey<ConfiguredFeature<?, ?>> key, FC featureConfig, Consumer<FeaturePack.ConfiguredFeaturePack<FC>> action) {
		FeaturePack.ConfiguredFeaturePack<FC> configuredFeaturePack = new ConfiguredFeaturePackImpl<>(this.feature, key, ignored -> featureConfig);
		this.configuredFeaturePacks.add(configuredFeaturePack);
		action.accept(configuredFeaturePack);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public FeaturePack<FC> replicateConfiguredFeature(RegistryKey<ConfiguredFeature<?, ?>> source, RegistryKey<ConfiguredFeature<?, ?>> key, AutoMapper<FC> patcher, Consumer<ConfiguredFeaturePack<FC>> action) {
		FeaturePack.ConfiguredFeaturePack<FC> configuredFeaturePack = new ConfiguredFeaturePackImpl<>(this.feature, key, configuredFeatures -> {
			ConfiguredFeature<?, ?> sourceConfiguredFeature = configuredFeatures.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE).getOrThrow(source).value();
			return patcher.map((FC) sourceConfiguredFeature.config());
		});
		this.configuredFeaturePacks.add(configuredFeaturePack);
		action.accept(configuredFeaturePack);
		return this;
	}

	@Override
	public void registerConfiguredFeatures(Registerable<ConfiguredFeature<?, ?>> configuredFeatures) {
		this.configuredFeaturePacks.forEach(pack -> {
			ConfiguredFeaturePackImpl<FC> impl = (ConfiguredFeaturePackImpl<FC>) pack;
			impl.registerSelf(configuredFeatures);
		});
	}

	@Override
	public void registerPlacedFeatures(Registerable<PlacedFeature> placedFeatures) {
		this.configuredFeaturePacks.forEach(pack -> {
			ConfiguredFeaturePackImpl<FC> impl = (ConfiguredFeaturePackImpl<FC>) pack;
			impl.register(placedFeatures);
		});
	}

	private static class ConfiguredFeaturePackImpl<FC extends FeatureConfig> implements FeaturePack.ConfiguredFeaturePack<FC> {

		private final Feature<FC> feature;
		private final RegistryKey<ConfiguredFeature<?, ?>> configuredFeatureKey;
		private final Function<Registerable<ConfiguredFeature<?, ?>>, FC> featureConfig;
		private final BiList<RegistryKey<PlacedFeature>, Function<Registerable<PlacedFeature>, List<PlacementModifier>>> placedFeatures;

		private ConfiguredFeaturePackImpl(Feature<FC> feature, RegistryKey<ConfiguredFeature<?, ?>> configuredFeatureKey, Function<Registerable<ConfiguredFeature<?, ?>>, FC> featureConfig) {
			this.feature = feature;
			this.configuredFeatureKey = configuredFeatureKey;
			this.featureConfig = featureConfig;
			this.placedFeatures = BiList.create();
		}

		@Override
		public ConfiguredFeaturePack<FC> appendPlacedFeature(RegistryKey<PlacedFeature> key, PlacementModifier... modifiers) {
			return this.appendPlacedFeature(key, List.of(modifiers));
		}

		@Override
		public ConfiguredFeaturePack<FC> appendPlacedFeature(RegistryKey<PlacedFeature> key, List<PlacementModifier> modifiers) {
			this.placedFeatures.add(key, ignored -> modifiers);
			return this;
		}

		@Override
		public ConfiguredFeaturePack<FC> replicatePlacedFeature(RegistryKey<PlacedFeature> source, RegistryKey<PlacedFeature> key, Consumer<PlacementModifiers> patcher) {
			this.placedFeatures.add(key, placedFeatures -> {
				PlacedFeature sourcePlacedFeature = placedFeatures.getRegistryLookup(RegistryKeys.PLACED_FEATURE).getOrThrow(source).value();
				PlacementModifiersImpl modifiers = new PlacementModifiersImpl(sourcePlacedFeature.placementModifiers());
				patcher.accept(modifiers);
				return modifiers.retrieve();
			});
			return this;
		}

		private void registerSelf(Registerable<ConfiguredFeature<?, ?>> configuredFeatures) {
			ConfiguredFeatures.register(configuredFeatures, this.configuredFeatureKey, this.feature, this.featureConfig.apply(configuredFeatures));
		}

		private void register(Registerable<PlacedFeature> placedFeatures) {
			RegistryEntry.Reference<ConfiguredFeature<?, ?>> entry = placedFeatures.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE).getOrThrow(this.configuredFeatureKey);
			this.placedFeatures.forEach((placedFeatureKey, modifiers) -> PlacedFeatures.register(placedFeatures, placedFeatureKey, entry, modifiers.apply(placedFeatures)));
		}
	}
}
