package com.mmodding.library.worldgen.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.worldgen.api.feature.FeaturePack;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

public class FeatureTests {

	public static final FeaturePack<RandomPatchFeatureConfig> RANDOM_PATCH = FeaturePack.of(Feature.RANDOM_PATCH)
		.appendConfiguredFeature(
			configured("test"),
			new RandomPatchFeatureConfig(0, 0, 0, null),
			configuredPack -> configuredPack.appendPlacedFeature(
				placed("test"),
				BiomePlacementModifier.of()
			)
		)
		.replicateConfiguredFeature(
			VegetationConfiguredFeatures.FLOWER_DEFAULT,
			configured("inner_test"),
			fc -> {
				int tries = 3;
				return new RandomPatchFeatureConfig(
					tries, fc.xzSpread(), fc.ySpread(), fc.feature()
				);
			},
			configuredPack -> configuredPack.replicatePlacedFeature(
				VegetationPlacedFeatures.FLOWER_DEFAULT,
				placed("inner_test"),
				modifiers -> modifiers.mutateTypeTo(
					PlacementModifierType.COUNT,
					modifier -> CountPlacementModifier.of(2)
				)
			)
		);

	private static RegistryKey<ConfiguredFeature<?, ?>> configured(String path) {
		return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of("mmodding_worldgen_test", path));
	}

	private static RegistryKey<PlacedFeature> placed(String path) {
		return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("mmodding_worldgen_test", path));
	}

	public static void registerConfiguredFeatures(Registerable<ConfiguredFeature<?, ?>> configuredFeatures, AdvancedContainer mod) {
		RANDOM_PATCH.registerConfiguredFeatures(configuredFeatures);
	}

	public static void registerPlacedFeatures(Registerable<PlacedFeature> placedFeatures) {
		RANDOM_PATCH.registerPlacedFeatures(placedFeatures);
	}
}
