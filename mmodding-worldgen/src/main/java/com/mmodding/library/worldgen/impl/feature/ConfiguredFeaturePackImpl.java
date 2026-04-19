package com.mmodding.library.worldgen.impl.feature;

import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.worldgen.api.feature.ConfiguredFeaturePack;
import com.mmodding.library.worldgen.api.feature.PlacementModifiers;
import com.mmodding.library.worldgen.impl.feature.replication.PlacementModifiersImpl;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ConfiguredFeaturePackImpl<FC extends FeatureConfiguration> implements ConfiguredFeaturePack<FC> {

	final ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey;
	private final BiList<ResourceKey<PlacedFeature>, Function<FabricDynamicRegistryProvider.Entries, List<PlacementModifier>>> placedFeatures;

	public ConfiguredFeaturePackImpl(ResourceKey<ConfiguredFeature<?, ?>> key) {
		this.configuredFeatureKey = key;
		this.placedFeatures = BiList.create();
	}

	@Override
	public ConfiguredFeaturePack<FC> appendPlacedFeature(ResourceKey<PlacedFeature> key, PlacementModifier... modifiers) {
		return this.appendPlacedFeature(key, List.of(modifiers));
	}

	@Override
	public ConfiguredFeaturePack<FC> appendPlacedFeature(ResourceKey<PlacedFeature> key, List<PlacementModifier> modifiers) {
		this.placedFeatures.add(key, ignored -> modifiers);
		return this;
	}

	@Override
	public ConfiguredFeaturePack<FC> replicatePlacedFeature(ResourceKey<PlacedFeature> source, ResourceKey<PlacedFeature> key, Consumer<PlacementModifiers> patcher) {
		this.placedFeatures.add(key, placedFeatures -> {
			PlacedFeature sourcePlacedFeature = placedFeatures.getLookup(Registries.PLACED_FEATURE).getOrThrow(source).value();
			PlacementModifiersImpl modifiers = new PlacementModifiersImpl(sourcePlacedFeature.placement());
			patcher.accept(modifiers);
			return modifiers.retrieve();
		});
		return this;
	}

	@Override
	public void register(FabricDynamicRegistryProvider.Entries registrable) {
		Holder.Reference<ConfiguredFeature<?, ?>> entry = registrable.getLookup(Registries.CONFIGURED_FEATURE).getOrThrow(this.configuredFeatureKey);
		this.placedFeatures.forEach((placedFeatureKey, modifiers) -> registrable.add(placedFeatureKey, new PlacedFeature(entry, List.copyOf(modifiers.apply(registrable)))));
	}
}
