package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

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
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class CustomLiquidVegetationPatchFeature implements CustomFeature, FeatureRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean();
	private final AtomicReference<Identifier> identifier = new AtomicReference<>();
	private final BiList<PlacedFeature, String> additionalPlacedFeatures = new BiArrayList<>();

	private final int count;
	private final Direction searchDirection;
	private final int maxSteps;
	private final TagKey<Block> replaceable;
	private final BlockState ground;
	private final BiList<BlockState, Integer> vegetationStates;
	private final VerticalSurfaceType verticalSurfaceType;
	private final IntProvider depth;
	private final float extraBottomBlockChance;
	private final int verticalRange;
	private final float vegetationChance;
	private final IntProvider horizontalRadius;
	private final float extraEdgeColumnChance;

	public CustomLiquidVegetationPatchFeature(int count, Direction searchDirection, int maxSteps, TagKey<Block> replaceable, BlockState ground, BiList<BlockState, Integer> vegetationStates, VerticalSurfaceType verticalSurfaceType, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider horizontalRadius, float extraEdgeColumnChance) {
		this.count = count;
		this.searchDirection = searchDirection;
		this.maxSteps = maxSteps;
		this.replaceable = replaceable;
		this.ground = ground;
		this.vegetationStates = vegetationStates;
		this.verticalSurfaceType = verticalSurfaceType;
		this.depth = depth;
		this.extraBottomBlockChance = extraBottomBlockChance;
		this.verticalRange = verticalRange;
		this.vegetationChance = vegetationChance;
		this.horizontalRadius = horizontalRadius;
		this.extraEdgeColumnChance = extraEdgeColumnChance;
	}

	@Override
	public Feature<VegetationPatchFeatureConfig> getFeature() {
		return Feature.WATERLOGGED_VEGETATION_PATCH;
	}

	@Override
	public ConfiguredFeature<?, ?> getConfiguredFeature() {

		DataPool.Builder<BlockState> builder = DataPool.builder();
		DataPool.of(builder);
		this.vegetationStates.forEach(builder::add);
		BlockStateProvider provider = new WeightedBlockStateProvider(builder);

		return new ConfiguredFeature<>(this.getFeature(), new VegetationPatchFeatureConfig(
			this.replaceable,
			BlockStateProvider.of(this.ground),
			PlacedFeatureUtil.placedInline(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(provider)),
			this.verticalSurfaceType,
			this.depth,
			this.extraBottomBlockChance,
			this.verticalRange,
			this.vegetationChance,
			this.horizontalRadius,
			this.extraEdgeColumnChance
		));
	}

	public PlacedFeature createPlacedFeature(int count, Direction searchDirection, int maxSteps) {
		return new PlacedFeature(Holder.createDirect(this.getConfiguredFeature()), ListUtils.builder(placementModifiers -> {
			placementModifiers.add(CountPlacementModifier.create(count));
			placementModifiers.add(InSquarePlacementModifier.getInstance());
			placementModifiers.add(PlacedFeatureUtil.BOTTOM_TO_MAX_TERRAIN_HEIGHT_RANGE);
			placementModifiers.add(EnvironmentScanPlacementModifier.create(searchDirection, BlockPredicate.solid(), BlockPredicate.IS_AIR, maxSteps));
			placementModifiers.add(RandomOffsetPlacementModifier.vertical(ConstantIntProvider.create(1)));
			placementModifiers.add(BiomePlacementModifier.getInstance());
		}));
	}

	@Override
	public PlacedFeature getDefaultPlacedFeature() {
		return this.createPlacedFeature(this.count, this.searchDirection, this.maxSteps);
	}

	public CustomLiquidVegetationPatchFeature addPlacedFeature(int count, Direction searchDirection, int maxSteps, String idExt) {
		this.additionalPlacedFeatures.add(this.createPlacedFeature(count, searchDirection, maxSteps), idExt);
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
