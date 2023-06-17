package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.InSquarePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.NoiseBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class CustomFlowerFeature implements CustomFeature, FeatureRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean();
	private final AtomicReference<Identifier> identifier = new AtomicReference<>();

	private final AtomicInteger count = new AtomicInteger();
	private final AtomicInteger rarity = new AtomicInteger();
	private final int tries;
	private final int spreadXZ;
	private final int spreadY;
	private final List<Pair<Block, Integer>> flowers;
	private final AtomicBoolean noised = new AtomicBoolean();

	public CustomFlowerFeature(int tries, int spreadHorizontally, int spreadVertically, Block... noisedFlowers) {
		this.flowers = new ArrayList<>();
		Arrays.stream(noisedFlowers).toList().forEach(block -> flowers.add(new Pair<>(block, 0)));
		this.tries = tries;
		this.spreadXZ = spreadHorizontally;
		this.spreadY = spreadVertically;
		this.noised.set(true);
	}

	public CustomFlowerFeature(int tries, int spreadHorizontally, int spreadVertically, List<Pair<Block, Integer>> flowers) {
		this.tries = tries;
		this.spreadXZ = spreadHorizontally;
		this.spreadY = spreadVertically;
		this.flowers = flowers;
	}

	@Override
	public Feature<?> getFeature() {
		return Feature.FLOWER;
	}

	@Override
	public ConfiguredFeature<?, ?> getConfiguredFeature() {

		BlockStateProvider provider;

		if (this.noised.get()) {
			List<BlockState> flowerStates = new ArrayList<>();
			this.flowers.forEach(pair -> flowerStates.add(pair.getLeft().getDefaultState()));

			provider = new NoiseBlockStateProvider(
				2345L,
				new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0),
				0.05f,
				flowerStates
			);
		}
		else {
			DataPool.Builder<BlockState> builder = DataPool.builder();
			this.flowers.forEach(pair -> builder.add(pair.getLeft().getDefaultState(), pair.getRight()));

			provider = new WeightedBlockStateProvider(builder);
		}

		return new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchFeatureConfig(this.tries, this.spreadXZ, this.spreadY, PlacedFeatureUtil.onlyWhenEmpty(
			Feature.SIMPLE_BLOCK,
			new SimpleBlockFeatureConfig(provider)
		)));
	}

	public CustomFlowerFeature setCount(int count) {
		this.count.set(count);
		return this;
	}

	public CustomFlowerFeature setRarity(int rarity) {
		this.rarity.set(rarity);
		return this;
	}

	@Override
	public PlacedFeature getPlacedFeature() {

		List<PlacementModifier> placementModifiers = new ArrayList<>();
		if (this.count.get() != 0) placementModifiers.add(CountPlacementModifier.create(this.count.get()));
		if (this.rarity.get() != 0) placementModifiers.add(RarityFilterPlacementModifier.create(this.rarity.get()));
		placementModifiers.add(InSquarePlacementModifier.getInstance());
		placementModifiers.add(PlacedFeatureUtil.MOTION_BLOCKING_HEIGHTMAP);
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
