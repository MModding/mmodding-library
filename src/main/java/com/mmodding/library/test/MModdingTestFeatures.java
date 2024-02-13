package com.mmodding.library.test;

import com.mmodding.library.container.AdvancedContainer;
import com.mmodding.library.core.Reference;
import com.mmodding.library.feature.FeaturePack;
import com.mmodding.library.feature.replication.FeatureReplicator;
import com.mmodding.library.registry.content.DoubleContentHolder;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.PlacementModifierType;
import net.minecraft.world.gen.feature.*;

public class MModdingTestFeatures implements DoubleContentHolder<ConfiguredFeature<?, ?>, PlacedFeature> {

	private final FeaturePack<RandomPatchFeatureConfig> RANDOM_PATCH;

	public MModdingTestFeatures(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures, AdvancedContainer mod) {
		this.RANDOM_PATCH = new FeaturePack<>(() -> Feature.RANDOM_PATCH);
		this.RANDOM_PATCH.appendConfiguredFeature(
			Reference.cast(new Identifier("", "")),
			new RandomPatchFeatureConfig(0, 0, 0, null),
			configuredPack -> {
				configuredPack.appendPlacedFeature(
					Reference.cast(new Identifier("", "")),
					BiomePlacementModifier.getInstance()
				);
			}
		);
		this.RANDOM_PATCH.appendConfiguredFeature(
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
	}

	@Override
	public void register(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures, AdvancedContainer mod) {
		this.RANDOM_PATCH.register(configuredFeatures, placedFeatures);
	}
}
