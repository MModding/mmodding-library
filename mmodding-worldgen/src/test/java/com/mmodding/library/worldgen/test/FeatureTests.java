package com.mmodding.library.worldgen.test;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.Reference;
import com.mmodding.library.java.api.FeaturePack;
import com.mmodding.library.java.api.replication.FeatureReplicator;
import com.mmodding.library.core.api.management.content.DoubleContentHolder;
import com.mmodding.library.core.api.management.content.ForBeing;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.PlacementModifierType;
import net.minecraft.world.gen.feature.*;

public class FeatureTests implements DoubleContentHolder<ConfiguredFeature<?, ?>, PlacedFeature> {

	public static final ForBeing.Vacant<FeaturePack<RandomPatchFeatureConfig>> RANDOM_PATCH = ForBeing.vacant();

	public FeatureTests(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures, AdvancedContainer mod) {
		Reference.LiteFactory factory = Reference.createFactory(path -> new Identifier(mod.metadata().id(), path));
		RANDOM_PATCH.initialize(() -> FeaturePack.of(() -> Feature.RANDOM_PATCH));
		RANDOM_PATCH.execute(randomPatch -> {
			randomPatch.appendConfiguredFeature(
				factory.createId(""),
				new RandomPatchFeatureConfig(0, 0, 0, null),
				configuredPack -> configuredPack.appendPlacedFeature(
					factory.createId(""),
					BiomePlacementModifier.getInstance()
				)
			);
			randomPatch.appendConfiguredFeature(
				FeatureReplicator.replicateConfiguredFeature(
					factory.createId(""),
					configuredFeatures.get(VegetationConfiguredFeatures.FLOWER_DEFAULT),
					fc -> {
						int tries = 3;
						return new RandomPatchFeatureConfig(
							tries, fc.spreadXz(), fc.spreadY(), fc.feature()
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
								modifier -> CountPlacementModifier.create(2)
							);
							return modifiers;
						}
					)
				)
			);
		});
	}

	@Override
	public void register(Registry<ConfiguredFeature<?, ?>> configuredFeatures, Registry<PlacedFeature> placedFeatures, AdvancedContainer mod) {
		RANDOM_PATCH.get().register(configuredFeatures, placedFeatures);
	}
}
