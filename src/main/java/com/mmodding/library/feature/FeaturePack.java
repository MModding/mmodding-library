package com.mmodding.library.feature;

import com.mmodding.library.core.Reference;
import com.mmodding.library.registry.WaitingRegistryEntry;
import net.minecraft.registry.Holder;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FeaturePack<FC extends FeatureConfig> {

	private final Supplier<Feature<FC>> feature;

	private final List<ConfiguredFeaturePack<FC>> configuredFeaturePacks = new ArrayList<>();

	public FeaturePack(Supplier<Feature<FC>> feature) {
		this.feature = feature;
	}

	public Feature<FC> getFeature() {
		return this.feature.get();
	}

	public void appendConfiguredFeature(Reference<ConfiguredFeature<FC, Feature<FC>>> reference, FC featureConfig, Consumer<ConfiguredFeaturePack<FC>> action) {
		ConfiguredFeaturePack<FC> configuredFeaturePack = new ConfiguredFeaturePackImpl<>(
			new WaitingRegistryEntry<>(reference, new ConfiguredFeature<>(this.feature.get(), featureConfig))
		);
		this.configuredFeaturePacks.add(configuredFeaturePack);
		action.accept(configuredFeaturePack);
	}

	public void appendConfiguredFeature(WaitingRegistryEntry<ConfiguredFeature<FC, Feature<FC>>> configuredFeature, Consumer<ConfiguredFeaturePack<FC>> action) {
		ConfiguredFeaturePack<FC> configuredFeaturePack = new ConfiguredFeaturePackImpl<>(configuredFeature);
		this.configuredFeaturePacks.add(configuredFeaturePack);
		action.accept(configuredFeaturePack);
	}

	public List<ConfiguredFeaturePack<FC>> getConfiguredFeaturePacks() {
		return this.configuredFeaturePacks;
	}

	public void register(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures) {
		this.configuredFeaturePacks.forEach(pack -> {
			ConfiguredFeaturePackImpl<FC> impl = (ConfiguredFeaturePackImpl<FC>) pack;
			impl.register(configuredFeatures, placedFeatures);
		});
	}

	public interface ConfiguredFeaturePack<FC extends FeatureConfig> {

		void appendPlacedFeature(Reference<PlacedFeature> reference, PlacementModifier... modifiers);

		void appendPlacedFeature(WaitingRegistryEntry<PlacedFeature> placedFeature);

		ConfiguredFeature<FC, Feature<FC>> getConfiguredFeature();

		List<PlacedFeature> getPlacedFeatures();
	}

	private static class ConfiguredFeaturePackImpl<FC extends FeatureConfig> implements ConfiguredFeaturePack<FC> {

		private final WaitingRegistryEntry<ConfiguredFeature<FC, Feature<FC>>> configuredFeature;

		private final List<WaitingRegistryEntry<PlacedFeature>> placedFeatures;

		private ConfiguredFeaturePackImpl(WaitingRegistryEntry<ConfiguredFeature<FC, Feature<FC>>> configuredFeature) {
			this.configuredFeature = configuredFeature;
			this.placedFeatures = new ArrayList<>();
		}

		@Override
		public void appendPlacedFeature(Reference<PlacedFeature> reference, PlacementModifier... modifiers) {
			this.placedFeatures.add(new WaitingRegistryEntry<>(reference, new PlacedFeature(Holder.createDirect(WaitingRegistryEntry.retrieveElements(List.of(this.configuredFeature)).get(0)), List.of(modifiers))));
		}

		@Override
		public void appendPlacedFeature(WaitingRegistryEntry<PlacedFeature> placedFeature) {
			this.placedFeatures.add(placedFeature);
		}

		@Override
		public ConfiguredFeature<FC, Feature<FC>> getConfiguredFeature() {
			return WaitingRegistryEntry.retrieveElements(List.of(this.configuredFeature)).get(0);
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
