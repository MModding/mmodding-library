package com.mmodding.library.worldgen.impl.feature;

import com.mmodding.library.core.api.Reference;
import com.mmodding.library.core.api.registry.WaitingRegistryEntry;
import com.mmodding.library.worldgen.api.feature.FeaturePack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FeaturePackImpl<FC extends FeatureConfig> implements FeaturePack<FC> {

	private final Supplier<Feature<FC>> feature;

	private final List<ConfiguredFeaturePack<FC>> configuredFeaturePacks;

	public FeaturePackImpl(Supplier<Feature<FC>> feature) {
		this.feature = feature;
		this.configuredFeaturePacks = new ArrayList<>();
	}

	@Override
	public Feature<FC> getFeature() {
		return this.feature.get();
	}

	@Override
	public void appendConfiguredFeature(Reference<ConfiguredFeature<FC, Feature<FC>>> reference, FC featureConfig, Consumer<FeaturePack.ConfiguredFeaturePack<FC>> action) {
		FeaturePack.ConfiguredFeaturePack<FC> configuredFeaturePack = new ConfiguredFeaturePackImpl<>(
			new WaitingRegistryEntry<>(reference, new ConfiguredFeature<>(this.feature.get(), featureConfig))
		);
		this.configuredFeaturePacks.add(configuredFeaturePack);
		action.accept(configuredFeaturePack);
	}

	@Override
	public void appendConfiguredFeature(WaitingRegistryEntry<ConfiguredFeature<FC, Feature<FC>>> configuredFeature, Consumer<FeaturePack.ConfiguredFeaturePack<FC>> action) {
		FeaturePack.ConfiguredFeaturePack<FC> configuredFeaturePack = new ConfiguredFeaturePackImpl<>(configuredFeature);
		this.configuredFeaturePacks.add(configuredFeaturePack);
		action.accept(configuredFeaturePack);
	}

	@Override
	public List<FeaturePack.ConfiguredFeaturePack<FC>> getConfiguredFeaturePacks() {
		return this.configuredFeaturePacks;
	}

	@Override
	public void register(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures) {
		this.configuredFeaturePacks.forEach(pack -> {
			ConfiguredFeaturePackImpl<FC> impl = (ConfiguredFeaturePackImpl<FC>) pack;
			impl.register(configuredFeatures, placedFeatures);
		});
	}

	private static class ConfiguredFeaturePackImpl<FC extends FeatureConfig> implements FeaturePack.ConfiguredFeaturePack<FC> {

		private final WaitingRegistryEntry<ConfiguredFeature<FC, Feature<FC>>> configuredFeature;

		private final List<WaitingRegistryEntry<PlacedFeature>> placedFeatures;

		private ConfiguredFeaturePackImpl(WaitingRegistryEntry<ConfiguredFeature<FC, Feature<FC>>> configuredFeature) {
			this.configuredFeature = configuredFeature;
			this.placedFeatures = new ArrayList<>();
		}

		@Override
		public ConfiguredFeature<FC, Feature<FC>> getConfiguredFeature() {
			return WaitingRegistryEntry.retrieveElements(List.of(this.configuredFeature)).get(0);
		}

		@Override
		public void appendPlacedFeature(Reference<PlacedFeature> reference, PlacementModifier... modifiers) {
			this.placedFeatures.add(new WaitingRegistryEntry<>(reference, new PlacedFeature(RegistryEntry.of(WaitingRegistryEntry.retrieveElements(List.of(this.configuredFeature)).get(0)), List.of(modifiers))));
		}

		@Override
		public void appendPlacedFeature(WaitingRegistryEntry<PlacedFeature> placedFeature) {
			this.placedFeatures.add(placedFeature);
		}

		@Override
		public List<PlacedFeature> getPlacedFeatures() {
			return WaitingRegistryEntry.retrieveElements(this.placedFeatures);
		}

		@SuppressWarnings("unchecked")
		private void register(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures) {
			((WaitingRegistryEntry<ConfiguredFeature<?,?>>) (WaitingRegistryEntry<?>) this.configuredFeature).register(configuredFeatures);
			this.placedFeatures.forEach(placedFeature -> placedFeature.register(placedFeatures));
		}
	}
}
