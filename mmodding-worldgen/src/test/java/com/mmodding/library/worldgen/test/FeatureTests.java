package com.mmodding.library.worldgen.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.java.api.object.Holder;
import com.mmodding.library.worldgen.api.feature.FeaturePack;
import com.mmodding.library.worldgen.api.feature.replication.FeatureReplicator;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

public class FeatureTests {

	public static final Holder<FeaturePack<RandomPatchFeatureConfig>> RANDOM_PATCH = Holder.create();

	public FeatureTests(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures, AdvancedContainer mod) {
		RANDOM_PATCH.assign(() -> FeaturePack.of(() -> Feature.RANDOM_PATCH));
		RANDOM_PATCH.ifPresent(randomPatch -> {
			randomPatch.appendConfiguredFeature(
				mod.createKey(RegistryKeys.CONFIGURED_FEATURE, ""),
				new RandomPatchFeatureConfig(0, 0, 0, null),
				configuredPack -> configuredPack.appendPlacedFeature(
					mod.createKey(RegistryKeys.PLACED_FEATURE, ""),
					BiomePlacementModifier.of()
				)
			);
			randomPatch.appendConfiguredFeature(
				FeatureReplicator.replicateConfiguredFeature(
					mod.createKey(RegistryKeys.CONFIGURED_FEATURE, ""),
					configuredFeatures.get(VegetationConfiguredFeatures.FLOWER_DEFAULT),
					fc -> {
						int tries = 3;
						return new RandomPatchFeatureConfig(
							tries, fc.comp_149(), fc.comp_150(), fc.comp_155()
						);
					}
				),
				configuredPack -> configuredPack.appendPlacedFeature(
					FeatureReplicator.replicatePlacedFeature(
						mod.createKey(RegistryKeys.PLACED_FEATURE, ""),
						placedFeatures.get(VegetationPlacedFeatures.FLOWER_DEFAULT),
						modifiers -> {
							modifiers.mutateTypeTo(
								PlacementModifierType.COUNT,
								modifier -> CountPlacementModifier.of(2)
							);
							return modifiers;
						}
					)
				)
			);
		});
	}

	public static void register(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures, AdvancedContainer mod) {
		RANDOM_PATCH.get().register(configuredFeatures, placedFeatures);
	}
}
