package com.mmodding.library.worldgen.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.worldgen.api.feature.FeaturePack;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class FeatureTests {

	public static final FeaturePack<RandomPatchConfiguration> RANDOM_PATCH = FeaturePack.of(Feature.RANDOM_PATCH)
		.appendConfiguredFeature(
			configured("test"),
			new RandomPatchConfiguration(0, 0, 0, null),
			configuredPack -> configuredPack.appendPlacedFeature(
				placed("test"),
				BiomeFilter.biome()
			)
		)
		.replicateConfiguredFeature(
			VegetationFeatures.FLOWER_DEFAULT,
			configured("inner_test"),
			fc -> {
				int tries = 3;
				return new RandomPatchConfiguration(
					tries, fc.xzSpread(), fc.ySpread(), fc.feature()
				);
			},
			configuredPack -> configuredPack.replicatePlacedFeature(
				VegetationPlacements.FLOWER_DEFAULT,
				placed("inner_test"),
				modifiers -> modifiers.mutateTypeTo(
					PlacementModifierType.COUNT,
					modifier -> CountPlacement.of(2)
				)
			)
		);

	private static ResourceKey<ConfiguredFeature<?, ?>> configured(String path) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath("mmodding_worldgen_test", path));
	}

	private static ResourceKey<PlacedFeature> placed(String path) {
		return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath("mmodding_worldgen_test", path));
	}

	public static void registerConfiguredFeatures(BootstapContext<ConfiguredFeature<?, ?>> configuredFeatures, AdvancedContainer mod) {
		RANDOM_PATCH.registerConfiguredFeatures(configuredFeatures);
	}

	public static void registerPlacedFeatures(BootstapContext<PlacedFeature> placedFeatures) {
		RANDOM_PATCH.registerPlacedFeatures(placedFeatures);
	}
}
