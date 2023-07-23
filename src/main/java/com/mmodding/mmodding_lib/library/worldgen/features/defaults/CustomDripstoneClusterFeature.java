package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.InSquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class CustomDripstoneClusterFeature implements CustomFeature, FeatureRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean();
	private final AtomicReference<Identifier> identifier = new AtomicReference<>();
	private final List<Pair<PlacedFeature, String>> additionalPlacedFeatures = new ArrayList<>();

	private final IntProvider countRange;
	private final int floorToCeilingSearchRange;
	private final IntProvider height;
	private final IntProvider radius;
	private final int maxStalagmiteStalactiteHeightDiff;
	private final int heightDeviation;
	private final IntProvider dripstoneBlockLayerThickness;
	private final FloatProvider density;
	private final FloatProvider wetness;
	private final float chanceOfDripstoneColumnAtMaxDistanceFromCenter;
	private final int maxDistanceFromCenterAffectingChanceOfDripstoneColumn;
	private final int maxDistanceFromCenterAffectingHeightBias;

	public CustomDripstoneClusterFeature(IntProvider countRange, int floorToCeilingSearchRange, IntProvider height, IntProvider radius, int maxStalagmiteStalactiteHeightDiff, int heightDeviation, IntProvider dripstoneBlockLayerThickness, FloatProvider density, FloatProvider wetness, float chanceOfDripstoneColumnAtMaxDistanceFromCenter, int maxDistanceFromCenterAffectingChanceOfDripstoneColumn, int maxDistanceFromCenterAffectingHeightBias) {
		this.countRange = countRange;
		this.floorToCeilingSearchRange = floorToCeilingSearchRange;
		this.height = height;
		this.radius = radius;
		this.maxStalagmiteStalactiteHeightDiff = maxStalagmiteStalactiteHeightDiff;
		this.heightDeviation = heightDeviation;
		this.dripstoneBlockLayerThickness = dripstoneBlockLayerThickness;
		this.density = density;
		this.wetness = wetness;
		this.chanceOfDripstoneColumnAtMaxDistanceFromCenter = chanceOfDripstoneColumnAtMaxDistanceFromCenter;
		this.maxDistanceFromCenterAffectingChanceOfDripstoneColumn = maxDistanceFromCenterAffectingChanceOfDripstoneColumn;
		this.maxDistanceFromCenterAffectingHeightBias = maxDistanceFromCenterAffectingHeightBias;
	}

	@Override
	public Feature<DripstoneClusterFeatureConfig> getFeature() {
		return Feature.DRIPSTONE_CLUSTER;
	}

	@Override
	public ConfiguredFeature<?, ?> getConfiguredFeature() {
		return new ConfiguredFeature<>(this.getFeature(), new DripstoneClusterFeatureConfig(
			this.floorToCeilingSearchRange,
			this.height,
			this.radius,
			this.maxStalagmiteStalactiteHeightDiff,
			this.heightDeviation,
			this.dripstoneBlockLayerThickness,
			this.density,
			this.wetness,
			this.chanceOfDripstoneColumnAtMaxDistanceFromCenter,
			this.maxDistanceFromCenterAffectingChanceOfDripstoneColumn,
			this.maxDistanceFromCenterAffectingHeightBias
		));
	}

	public PlacedFeature createPlacedFeature(IntProvider countRange) {

		List<PlacementModifier> placementModifiers = new ArrayList<>();
		placementModifiers.add(CountPlacementModifier.create(countRange));
		placementModifiers.add(InSquarePlacementModifier.getInstance());
		placementModifiers.add(PlacedFeatureUtil.BOTTOM_TO_MAX_TERRAIN_HEIGHT_RANGE);
		placementModifiers.add(BiomePlacementModifier.getInstance());

		return new PlacedFeature(Holder.createDirect(this.getConfiguredFeature()), placementModifiers);
	}

	@Override
	public PlacedFeature getDefaultPlacedFeature() {
		return this.createPlacedFeature(this.countRange);
	}

	public CustomDripstoneClusterFeature addPlacedFeature(IntProvider countRange, String idExt) {
		this.additionalPlacedFeatures.add(new Pair<>(this.createPlacedFeature(countRange), idExt));
		return this;
	}

	@Override
	public List<Pair<PlacedFeature, String>> getAdditionalPlacedFeatures() {
		return this.additionalPlacedFeatures;
	}

	@Override
	public void addDefaultToBiomes(Predicate<BiomeSelectionContext> ctx) {
		this.addAdditionalToBiomes(ctx, "");
	}

	@Override
	public void addAdditionalToBiomes(Predicate<BiomeSelectionContext> ctx, String idExt) {
		if (this.registered.get()) {
			BiomeModifications.addFeature(
				ctx, GenerationStep.Feature.UNDERGROUND_DECORATION,
				RegistryKey.of(Registry.PLACED_FEATURE_KEY, this.addIdExt(this.identifier.get(), idExt))
			);
		}
	}

	@Override
	public void setIdentifier(Identifier identifier) {
		this.identifier.set(identifier);
	}

	@Override
	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		this.registered.set(true);
	}
}
