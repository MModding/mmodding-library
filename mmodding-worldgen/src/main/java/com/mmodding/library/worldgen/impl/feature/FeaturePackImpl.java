package com.mmodding.library.worldgen.impl.feature;

import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.worldgen.api.feature.ConfiguredFeaturePack;
import com.mmodding.library.worldgen.api.feature.FeaturePack;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.*;

import java.util.function.Consumer;
import java.util.function.Function;

public class FeaturePackImpl<FC extends FeatureConfig> implements FeaturePack<FC> {

	private final Feature<FC> feature;

	private final BiList<ConfiguredFeaturePack<FC>, Function<Registerable<ConfiguredFeature<?, ?>>, FC>> configuredFeaturePacks;

	public FeaturePackImpl(Feature<FC> feature) {
		this.feature = feature;
		this.configuredFeaturePacks = BiList.create();
	}

	@Override
	public FeaturePack<FC> appendConfiguredFeature(RegistryKey<ConfiguredFeature<?, ?>> key, FC featureConfig, Consumer<ConfiguredFeaturePack<FC>> action) {
		ConfiguredFeaturePack<FC> configuredFeaturePack = ConfiguredFeaturePack.of(key);
		this.configuredFeaturePacks.add(configuredFeaturePack, ignored -> featureConfig);
		action.accept(configuredFeaturePack);
		return this;
	}

	@Override
	public FeaturePack<FC> appendConfiguredFeature(RegistryKey<ConfiguredFeature<?, ?>> key, Function<FeaturePack.ConfiguredFeatureLookup<FC>, FC> featureConfigFactory, Consumer<ConfiguredFeaturePack<FC>> action) {
		ConfiguredFeaturePack<FC> configuredFeaturePack = new ConfiguredFeaturePackImpl<>(key);
		this.configuredFeaturePacks.add(configuredFeaturePack, configuredFeatures -> {
			RegistryEntryLookup<ConfiguredFeature<?, ?>> lookup = configuredFeatures.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
			return featureConfigFactory.apply(lookup::getOrThrow);
		});
		action.accept(configuredFeaturePack);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public FeaturePack<FC> replicateConfiguredFeature(RegistryKey<ConfiguredFeature<?, ?>> source, RegistryKey<ConfiguredFeature<?, ?>> key, AutoMapper<FC> patcher, Consumer<ConfiguredFeaturePack<FC>> action) {
		ConfiguredFeaturePack<FC> configuredFeaturePack = new ConfiguredFeaturePackImpl<>(key);
		this.configuredFeaturePacks.add(configuredFeaturePack, configuredFeatures -> {
			ConfiguredFeature<?, ?> sourceConfiguredFeature = configuredFeatures.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE).getOrThrow(source).value();
			return patcher.map((FC) sourceConfiguredFeature.config());
		});
		action.accept(configuredFeaturePack);
		return this;
	}

	@Override
	public void registerConfiguredFeatures(Registerable<ConfiguredFeature<?, ?>> configuredFeatures) {
		this.configuredFeaturePacks.forEach((pack, featureConfig) -> {
			ConfiguredFeaturePackImpl<FC> impl = (ConfiguredFeaturePackImpl<FC>) pack;
			ConfiguredFeatures.register(configuredFeatures, impl.configuredFeatureKey, this.feature, featureConfig.apply(configuredFeatures));
		});
	}

	@Override
	public void registerPlacedFeatures(Registerable<PlacedFeature> placedFeatures) {
		this.configuredFeaturePacks.forEach((pack, featureConfig) -> pack.registerPlacedFeatures(placedFeatures));
	}
}
