package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import com.mmodding.mmodding_lib.library.utils.BiArrayList;
import com.mmodding.mmodding_lib.library.utils.BiList;
import com.mmodding.mmodding_lib.library.utils.IdentifierUtils;
import com.mmodding.mmodding_lib.library.utils.ListUtils;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
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

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class CustomFlowerFeature implements CustomFeature, FeatureRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean();
	private final AtomicReference<Identifier> identifier = new AtomicReference<>();
	private final BiList<PlacedFeature, String> additionalPlacedFeatures = new BiArrayList<>();

	private final AtomicInteger count = new AtomicInteger();
	private final AtomicInteger rarity = new AtomicInteger();
	private final int tries;
	private final int spreadXZ;
	private final int spreadY;
	private final BiList<Block, Integer> flowers;
	private final AtomicBoolean noised = new AtomicBoolean();

	public CustomFlowerFeature(int tries, int spreadHorizontally, int spreadVertically, Block... noisedFlowers) {
		this.flowers = new BiArrayList<>();
		Arrays.stream(noisedFlowers).toList().forEach(block -> this.flowers.add(block, 0));
		this.tries = tries;
		this.spreadXZ = spreadHorizontally;
		this.spreadY = spreadVertically;
		this.noised.set(true);
	}

	public CustomFlowerFeature(int tries, int spreadHorizontally, int spreadVertically, BiList<Block, Integer> flowers) {
		this.tries = tries;
		this.spreadXZ = spreadHorizontally;
		this.spreadY = spreadVertically;
		this.flowers = flowers;
	}

	@Override
	public Feature<RandomPatchFeatureConfig> getFeature() {
		return Feature.FLOWER;
	}

	@Override
	public ConfiguredFeature<?, ?> getConfiguredFeature() {

		BlockStateProvider provider;

		if (this.noised.get()) {
			provider = new NoiseBlockStateProvider(
				2345L,
				new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0),
				0.05f,
				ListUtils.builder(flowerStates -> this.flowers.forEachFirst(block -> flowerStates.add(block.getDefaultState())))
			);
		}
		else {
			DataPool.Builder<BlockState> builder = DataPool.builder();
			this.flowers.forEach((block, integer) -> builder.add(block.getDefaultState(), integer));
			provider = new WeightedBlockStateProvider(builder);
		}

		return new ConfiguredFeature<>(this.getFeature(), new RandomPatchFeatureConfig(this.tries, this.spreadXZ, this.spreadY, PlacedFeatureUtil.onlyWhenEmpty(
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

	public PlacedFeature createPlacedFeature(int count, int rarity) {
		return new PlacedFeature(Holder.createDirect(this.getConfiguredFeature()), ListUtils.builder(placementModifiers -> {
			if (count != 0) placementModifiers.add(CountPlacementModifier.create(count));
			if (rarity != 0) placementModifiers.add(RarityFilterPlacementModifier.create(rarity));
			placementModifiers.add(InSquarePlacementModifier.getInstance());
			placementModifiers.add(PlacedFeatureUtil.MOTION_BLOCKING_HEIGHTMAP);
			placementModifiers.add(BiomePlacementModifier.getInstance());
		}));
	}

	@Override
	public PlacedFeature getDefaultPlacedFeature() {
		return this.createPlacedFeature(this.count.get(), this.rarity.get());
	}

	public CustomFlowerFeature addPlacedFeature(int count, int rarity, String idExt) {
		this.additionalPlacedFeatures.add(this.createPlacedFeature(count, rarity), idExt);
		return this;
	}

	@Override
	public BiList<PlacedFeature, String> getAdditionalPlacedFeatures() {
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
				ctx, GenerationStep.Feature.VEGETAL_DECORATION,
				RegistryKey.of(Registry.PLACED_FEATURE_KEY, IdentifierUtils.extend(this.identifier.get(), idExt))
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
