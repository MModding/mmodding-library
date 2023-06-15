package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.InSquarePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class CustomRandomPatchFeature implements CustomFeature, FeatureRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean();
	private final AtomicReference<Identifier> identifier = new AtomicReference<>();

	private final int countOrRarity;
	private final int tries;
	private final int spreadXZ;
	private final int spreadY;
	private final Holder<PlacedFeature> feature;
	private final AtomicBoolean isRarity = new AtomicBoolean();

	public CustomRandomPatchFeature(int countOrRarity, int tries, int spreadHorizontally, int spreadVertically, Holder<PlacedFeature> feature) {
		this.countOrRarity = countOrRarity;
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

	public void hasRarity() {
		this.isRarity.set(true);
	}

	@Override
	public PlacedFeature getPlacedFeature() {

		List<PlacementModifier> placementModifiers = new ArrayList<>();
		placementModifiers.add(this.isRarity.get() ? RarityFilterPlacementModifier.create(this.countOrRarity) : CountPlacementModifier.create(this.countOrRarity));
		placementModifiers.add(InSquarePlacementModifier.getInstance());
		placementModifiers.add(PlacedFeatureUtil.WORLD_SURFACE_WG_HEIGHTMAP);
		placementModifiers.add(BiomePlacementModifier.getInstance());

		return new PlacedFeature(Holder.createDirect(this.getConfiguredFeature()), placementModifiers);
	}

	@Override
	public void addToBiomes(Predicate<BiomeSelectionContext> ctx) {
		if (this.registered.get()) {
			BiomeModifications.addFeature(
				ctx, GenerationStep.Feature.VEGETAL_DECORATION,
				RegistryKey.of(Registry.PLACED_FEATURE_KEY, this.identifier.get())
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
