package com.mmodding.library.worldgen.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.worldgen.api.feature.FeaturePack;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class FeatureTests {

	public static final FeaturePack<BlockPileConfiguration> RANDOM_PATCH = FeaturePack.of(Feature.BLOCK_PILE)
		.appendConfiguredFeature(
			configured("test"),
			new BlockPileConfiguration(BlockStateProvider.simple(Blocks.DIAMOND_BLOCK)),
			configuredPack -> configuredPack.appendPlacedFeature(
				placed("test"),
				BiomeFilter.biome()
			)
		)
		.replicateConfiguredFeature(
			VegetationFeatures.SUGAR_CANE,
			configured("inner_test"),
			fc -> new BlockPileConfiguration(fc.stateProvider),
			configuredPack -> configuredPack.replicatePlacedFeature(
				VegetationPlacements.PATCH_SUGAR_CANE,
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

	public static void register(FabricDynamicRegistryProvider.Entries registrable, AdvancedContainer mod) {
		RANDOM_PATCH.register(registrable);
	}
}
