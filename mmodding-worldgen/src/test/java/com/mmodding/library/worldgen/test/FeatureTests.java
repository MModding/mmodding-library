package com.mmodding.library.worldgen.test;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.Reference;
import com.mmodding.library.core.api.management.content.ForBeing;
import com.mmodding.library.worldgen.api.feature.FeaturePack;
import com.mmodding.library.worldgen.api.feature.replication.FeatureReplicator;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

public class FeatureTests {

	public static final ForBeing.Vacant<FeaturePack<RandomPatchFeatureConfig>> RANDOM_PATCH = ForBeing.vacant();

	public FeatureTests(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures, AdvancedContainer mod) {
		Reference.LiteFactory factory = Reference.createFactory(path -> new Identifier(mod.getMetadata().getId(), path));
		RANDOM_PATCH.initialize(() -> FeaturePack.of(() -> Feature.RANDOM_PATCH));
		RANDOM_PATCH.execute(randomPatch -> {
			randomPatch.appendConfiguredFeature(
				factory.createId(""),
				new RandomPatchFeatureConfig(0, 0, 0, null),
				configuredPack -> configuredPack.appendPlacedFeature(
					factory.createId(""),
					BiomePlacementModifier.of()
				)
			);
			randomPatch.appendConfiguredFeature(
				FeatureReplicator.replicateConfiguredFeature(
					factory.createId(""),
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
						factory.createId(""),
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
