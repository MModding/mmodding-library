package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import net.minecraft.util.Holder;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.InSquarePlacementModifier;
import net.minecraft.world.gen.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomOreFeature implements CustomFeature, FeatureRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean();

	private final int veinSize;
	private final int veinNumber;
	private final int minHeight;
	private final int maxHeight;
	private final List<OreFeatureConfig.Target> targets;
	private final float discardOnAirChance;

	public CustomOreFeature(int veinSize, int veinNumber, int minHeight, int maxHeight, List<OreFeatureConfig.Target> targets) {
		this(veinSize, veinNumber, minHeight, maxHeight, targets, 0.0f);
	}

	public CustomOreFeature(int veinSize, int veinNumber, int minHeight, int maxHeight, List<OreFeatureConfig.Target> targets, float discardOnAirChance) {
		this.veinSize = veinSize;
		this.veinNumber = veinNumber;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.targets = targets;
		this.discardOnAirChance = discardOnAirChance;
	}

	@Override
	public Feature<?> getFeature() {
		return Feature.ORE;
	}

	@Override
	public ConfiguredFeature<?, ?> getConfiguredFeature() {
		return new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(this.targets, this.veinSize, this.discardOnAirChance));
	}

	@Override
	public PlacedFeature getPlacedFeature() {

		List<PlacementModifier> placementModifiers = new ArrayList<>();
		placementModifiers.add(CountPlacementModifier.create(this.veinNumber));
		placementModifiers.add(InSquarePlacementModifier.getInstance());
		placementModifiers.add(HeightRangePlacementModifier.createUniform(YOffset.fixed(this.minHeight), YOffset.fixed(this.maxHeight)));

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
