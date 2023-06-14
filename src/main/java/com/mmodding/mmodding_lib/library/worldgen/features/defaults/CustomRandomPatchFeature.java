package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import net.minecraft.util.Holder;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.InSquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomRandomPatchFeature implements CustomFeature, FeatureRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean();

	private final int count;
	private final int tries;
	private final int spreadXZ;
	private final int spreadY;
	private final Holder<PlacedFeature> feature;

	public CustomRandomPatchFeature(int count, int tries, int spreadHorizontally, int spreadVertically, Holder<PlacedFeature> feature) {
		this.count = count;
		this.tries = tries;
		this.spreadXZ = spreadHorizontally;
		this.spreadY = spreadVertically;
		this.feature = feature;
	}

	@Override
	public Feature<?> getFeature() {
		return Feature.RANDOM_PATCH;
	}

	@Override
	public ConfiguredFeature<?, ?> getConfiguredFeature() {
		return new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(this.tries, this.spreadXZ, this.spreadY, this.feature));
	}

	@Override
	public PlacedFeature getPlacedFeature() {

		List<PlacementModifier> placementModifiers = new ArrayList<>();
		placementModifiers.add(CountPlacementModifier.create(this.count));
		placementModifiers.add(InSquarePlacementModifier.getInstance());
		placementModifiers.add(PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
		placementModifiers.add(BiomePlacementModifier.getInstance());

		return new PlacedFeature(Holder.createDirect(this.getConfiguredFeature()), placementModifiers);
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
