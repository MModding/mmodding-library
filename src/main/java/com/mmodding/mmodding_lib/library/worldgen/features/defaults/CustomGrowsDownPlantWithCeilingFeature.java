package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import com.mmodding.mmodding_lib.library.blocks.CustomGrowsDownPlantBlock;
import com.mmodding.mmodding_lib.library.utils.BiArrayList;
import com.mmodding.mmodding_lib.library.utils.BiList;
import com.mmodding.mmodding_lib.library.utils.IdentifierUtils;
import com.mmodding.mmodding_lib.library.utils.ListUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.intprovider.WeightedListIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.RandomizedIntBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CustomGrowsDownPlantWithCeilingFeature implements CustomFeature, FeatureRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean();
	private final AtomicReference<Identifier> identifier = new AtomicReference<>();
	private final BiList<PlacedFeature, String> additionalPlacedFeatures = new BiArrayList<>();

	private final int count;
	private final int maxSteps;
	private final Supplier<CustomGrowsDownPlantBlock> plant;
	private final Supplier<Block> ceiling;
	private final TagKey<Block> ceilingReplaceableTag;
	private final IntProvider depth;
	private final float extraBottomBlockChance;
	private final int verticalRange;
	private final float vegetationChance;
	private final IntProvider horizontalRadius;
	private final float extraEdgeColumnChance;

	public CustomGrowsDownPlantWithCeilingFeature(int count, int maxSteps, Supplier<CustomGrowsDownPlantBlock> plant, Supplier<Block> ceiling, TagKey<Block> ceilingReplaceableTag, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider horizontalRadius, float extraEdgeColumnChance) {
		this.count = count;
		this.maxSteps = maxSteps;
		this.plant = plant;
		this.ceiling = ceiling;
		this.ceilingReplaceableTag = ceilingReplaceableTag;
		this.depth = depth;
		this.extraBottomBlockChance = extraBottomBlockChance;
		this.verticalRange = verticalRange;
		this.vegetationChance = vegetationChance;
		this.horizontalRadius = horizontalRadius;
		this.extraEdgeColumnChance = extraEdgeColumnChance;
	}

	@Override
	public Feature<VegetationPatchFeatureConfig> getFeature() {
		return Feature.VEGETATION_PATCH;
	}

	@Override
	public ConfiguredFeature<?, ?> getConfiguredFeature() {

		WeightedBlockStateProvider bodyProvider = new WeightedBlockStateProvider(
			DataPool.<BlockState>builder()
				.add(this.plant.get().getBody().getDefaultState(), 4)
				.add(this.plant.get().withFruits(this.plant.get().getBody().getDefaultState()), 1)
		);

		RandomizedIntBlockStateProvider headProvider = new RandomizedIntBlockStateProvider(
			new WeightedBlockStateProvider(
				DataPool.<BlockState>builder()
					.add(this.plant.get().getHead().getDefaultState(), 4)
					.add(this.plant.get().withFruits(this.plant.get().getHead().getDefaultState()), 1)
			),
			CustomGrowsDownPlantBlock.Head.AGE,
			UniformIntProvider.create(23, 25)
		);

		BlockColumnFeatureConfig plantProvider = new BlockColumnFeatureConfig(
			List.of(
				BlockColumnFeatureConfig.createLayer(
					new WeightedListIntProvider(DataPool.<IntProvider>builder().add(UniformIntProvider.create(0, 3), 5).add(UniformIntProvider.create(1, 7), 1).build()),
					bodyProvider
				),
				BlockColumnFeatureConfig.createLayer(ConstantIntProvider.create(1), headProvider)
			),
			Direction.DOWN,
			BlockPredicate.IS_AIR,
			true
		);

		ConfiguredFeature<BlockColumnFeatureConfig, ?> configuredFeature = new ConfiguredFeature<>(Feature.BLOCK_COLUMN, plantProvider);

		Identifier identifier = IdentifierUtils.extend(this.identifier.get(), "in_ceiling");

		if (!BuiltinRegistries.CONFIGURED_FEATURE.containsId(identifier)) {
			 Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, identifier, configuredFeature);
		}

		VegetationPatchFeatureConfig featureConfig = new VegetationPatchFeatureConfig(
			this.ceilingReplaceableTag,
			BlockStateProvider.of(this.ceiling.get()),
			PlacedFeatureUtil.placedInline(Holder.createDirect(configuredFeature)),
			VerticalSurfaceType.CEILING,
			this.depth,
			this.extraBottomBlockChance,
			this.verticalRange,
			this.vegetationChance,
			this.horizontalRadius,
			this.extraEdgeColumnChance
		);

		return new ConfiguredFeature<>(this.getFeature(), featureConfig);
	}

	public PlacedFeature createPlacedFeature(int count, int maxSteps) {
		return new PlacedFeature(Holder.createDirect(this.getConfiguredFeature()), ListUtils.builder(placementModifiers -> {
			placementModifiers.add(CountPlacementModifier.create(count));
			placementModifiers.add(InSquarePlacementModifier.getInstance());
			placementModifiers.add(PlacedFeatureUtil.BOTTOM_TO_MAX_TERRAIN_HEIGHT_RANGE);
			placementModifiers.add(EnvironmentScanPlacementModifier.create(Direction.UP, BlockPredicate.solid(), BlockPredicate.IS_AIR, maxSteps));
			placementModifiers.add(RandomOffsetPlacementModifier.vertical(ConstantIntProvider.create(-1)));
			placementModifiers.add(BiomePlacementModifier.getInstance());
		}));
	}

	@Override
	public PlacedFeature getDefaultPlacedFeature() {
		return this.createPlacedFeature(this.count, this.maxSteps);
	}

	public CustomGrowsDownPlantWithCeilingFeature addPlacedFeature(int count, int maxSteps, String idExt) {
		this.additionalPlacedFeatures.add(this.createPlacedFeature(count, maxSteps), idExt);
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
