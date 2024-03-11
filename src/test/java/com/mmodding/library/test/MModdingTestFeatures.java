package com.mmodding.library.test;

import com.mmodding.library.container.api.AdvancedContainer;
import com.mmodding.library.core.api.Reference;
import com.mmodding.library.feature.api.FeaturePack;
import com.mmodding.library.feature.api.replication.FeatureReplicator;
import com.mmodding.library.registry.api.content.DoubleContentHolder;
import com.mmodding.library.registry.api.content.ForBeing;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.PlacementModifierType;
import net.minecraft.world.gen.feature.*;

public class MModdingTestFeatures implements DoubleContentHolder<ConfiguredFeature<?, ?>, PlacedFeature> {

	public static final ForBeing.Vacant<FeaturePack<RandomPatchFeatureConfig>> RANDOM_PATCH = ForBeing.vacant();

	public MModdingTestFeatures(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures, AdvancedContainer mod) {
		RANDOM_PATCH.initialize(() -> FeaturePack.of(() -> Feature.RANDOM_PATCH));
		RANDOM_PATCH.execute(randomPatch -> {
			randomPatch.appendConfiguredFeature(
				Reference.cast(new Identifier("", "")),
				new RandomPatchFeatureConfig(0, 0, 0, null),
				configuredPack -> configuredPack.appendPlacedFeature(
					Reference.cast(new Identifier("", "")),
					BiomePlacementModifier.getInstance()
				)
			);
			randomPatch.appendConfiguredFeature(
				FeatureReplicator.replicateConfiguredFeature(
					Reference.cast(new Identifier("", "")),
					configuredFeatures.get(VegetationConfiguredFeatures.FLOWER_DEFAULT),
					fc -> {
						int tries = 3;
						return new RandomPatchFeatureConfig(
							tries, fc.spreadXz(), fc.spreadY(), fc.feature()
						);
					}
				),
				configuredPack -> {
					configuredPack.appendPlacedFeature(
						FeatureReplicator.replicatePlacedFeature(
							Reference.cast(new Identifier("", "")),
							placedFeatures.get(VegetationPlacedFeatures.FLOWER_DEFAULT),
							modifiers -> {
								modifiers.mutateTypeTo(
									PlacementModifierType.COUNT,
									modifier -> CountPlacementModifier.create(2)
								);
								return modifiers;
							}
						)
					);
				}
			);
		});
	}

	@Override
	public void register(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures, AdvancedContainer mod) {
		RANDOM_PATCH.get().register(configuredFeatures, placedFeatures);
	}
}
