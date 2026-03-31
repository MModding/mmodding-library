package com.mmodding.library.worldgen.impl.feature;

import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.worldgen.api.feature.ConfiguredFeaturePack;
import com.mmodding.library.worldgen.api.feature.PlacementModifiers;
import com.mmodding.library.worldgen.impl.feature.replication.PlacementModifiersImpl;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ConfiguredFeaturePackImpl<FC extends FeatureConfig> implements ConfiguredFeaturePack<FC> {

	final RegistryKey<ConfiguredFeature<?, ?>> configuredFeatureKey;
	private final BiList<RegistryKey<PlacedFeature>, Function<Registerable<PlacedFeature>, List<PlacementModifier>>> placedFeatures;

	public ConfiguredFeaturePackImpl(RegistryKey<ConfiguredFeature<?, ?>> key) {
		this.configuredFeatureKey = key;
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

	@Override
	public void registerPlacedFeatures(Registerable<PlacedFeature> placedFeatures) {
		RegistryEntry.Reference<ConfiguredFeature<?, ?>> entry = placedFeatures.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE).getOrThrow(this.configuredFeatureKey);
		this.placedFeatures.forEach((placedFeatureKey, modifiers) -> PlacedFeatures.register(placedFeatures, placedFeatureKey, entry, modifiers.apply(placedFeatures)));
	}
}
