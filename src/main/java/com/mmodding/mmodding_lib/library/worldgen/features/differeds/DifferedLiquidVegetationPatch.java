package com.mmodding.mmodding_lib.library.worldgen.features.differeds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Holder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPatchFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DifferedLiquidVegetationPatch extends Feature<DifferedLiquidVegetationPatch.Config> {

	public DifferedLiquidVegetationPatch(Codec<Config> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean place(FeatureContext<Config> context) {
		StructureWorldAccess world = context.getWorld();
		Config config = context.getConfig();
		RandomGenerator random = context.getRandom();
		BlockPos pos = context.getOrigin();
		Predicate<BlockState> predicate = state -> state.isIn(config.replaceable);
		Set<BlockPos> positions = this.placeGroundAndGetPositions(world, config, random, pos, predicate, config.horizontalRadius.get(random) + 1, config.horizontalRadius.get(random) + 1);
		this.generateVegetation(context, world, config, random, positions);
		return !positions.isEmpty();
	}

	protected Set<BlockPos> placeGroundAndGetPositions(StructureWorldAccess world, Config config, RandomGenerator random, BlockPos pos, Predicate<BlockState> replaceable, int radiusX, int radiusZ) {
		BlockPos.Mutable mutablePos = pos.mutableCopy();
		BlockPos.Mutable resultPos = mutablePos.mutableCopy();
		Direction direction = config.surface.getDirection();
		Direction opposite = direction.getOpposite();
		Set<BlockPos> positions = new HashSet<>();
		for (int i = -radiusX; i <= radiusX; i++) {
			boolean xValid = i == -radiusX || i == radiusX;
			for (int j = -radiusZ; j <= radiusZ; j++) {
				boolean zValid = j == -radiusZ || j == radiusZ;
				boolean or = xValid || zValid;
				boolean equal = xValid && zValid;
				boolean xor = or && !equal;
				if (!equal && (!xor || config.extraEdgeColumnChance != 0.0f && !(random.nextFloat() > config.extraEdgeColumnChance))) {
					mutablePos.set(pos, i, 0, j);
					for (int k = 0; world.testBlockState(mutablePos, AbstractBlock.AbstractBlockState::isAir) && k < config.verticalRange; k++) {
						mutablePos.move(direction);
					}
					for (int l = 0; world.testBlockState(mutablePos, state -> !state.isAir()) && l < config.verticalRange; l++) {
						mutablePos.move(opposite);
					}
					resultPos.set(mutablePos, config.surface.getDirection());
					BlockState blockState = world.getBlockState(resultPos);
					if (world.isAir(mutablePos) && blockState.isSideSolidFullSquare(world, resultPos, config.surface.getDirection().getOpposite())) {
						int depth = config.depth.get(random) + (config.extraBottomBlockChance > 0.0f && random.nextFloat() < config.extraBottomBlockChance ? 1 : 0);
						if (this.placeGround(world, config, replaceable, random, resultPos, depth)) {
							positions.add(resultPos.toImmutable());
						}
					}
				}
			}
		}

		Set<BlockPos> result = new HashSet<>();
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for(BlockPos blockPos : positions) {
			if (!DifferedLiquidVegetationPatch.canRetainLiquid(world, blockPos, mutable)) {
				result.add(blockPos);
			}
		}

		for(BlockPos blockPos : result) {
			world.setBlockState(blockPos, config.liquidState.getBlockState(random, pos), Block.NOTIFY_LISTENERS);
		}

		return result.stream().map(BlockPos::up).filter(blockPos -> world.testBlockState(blockPos, BlockState::isAir)).collect(Collectors.toSet());
	}

	private static boolean canRetainLiquid(StructureWorldAccess world, BlockPos pos, BlockPos.Mutable mutablePos) {
		return DifferedLiquidVegetationPatch.isSideSolid(world, pos, mutablePos, Direction.NORTH)
			|| DifferedLiquidVegetationPatch.isSideSolid(world, pos, mutablePos, Direction.EAST)
			|| DifferedLiquidVegetationPatch.isSideSolid(world, pos, mutablePos, Direction.SOUTH)
			|| DifferedLiquidVegetationPatch.isSideSolid(world, pos, mutablePos, Direction.WEST)
			|| DifferedLiquidVegetationPatch.isSideSolid(world, pos, mutablePos, Direction.DOWN);
	}

	private static boolean isSideSolid(StructureWorldAccess world, BlockPos pos, BlockPos.Mutable mutablePos, Direction direction) {
		mutablePos.set(pos, direction);
		return !world.getBlockState(mutablePos).isSideSolidFullSquare(world, mutablePos, direction.getOpposite());
	}

	protected void generateVegetation(FeatureContext<Config> context, StructureWorldAccess world, VegetationPatchFeatureConfig config, RandomGenerator random, Set<BlockPos> positions) {
		for (BlockPos pos : positions) {
			if (config.vegetationChance > 0.0f && random.nextFloat() < config.vegetationChance) {
				config.vegetationFeature.value().place(world, context.getGenerator(), random, pos.down().offset(config.surface.getDirection().getOpposite()));
			}
		}
	}

	protected boolean placeGround(StructureWorldAccess world, VegetationPatchFeatureConfig config, Predicate<BlockState> replaceable, RandomGenerator random, BlockPos.Mutable pos, int depth) {
		for (int i = 0; i < depth; ++i) {
			BlockState groundState = config.groundState.getBlockState(random, pos);
			BlockState currentState = world.getBlockState(pos);
			if (!groundState.isOf(currentState.getBlock())) {
				if (!replaceable.test(currentState)) {
					return i != 0;
				}
				world.setBlockState(pos, groundState, Block.NOTIFY_LISTENERS);
				pos.move(config.surface.getDirection());
			}
		}
		return true;
	}

	public static class Config extends VegetationPatchFeatureConfig {

		public static final Codec<Config> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					TagKey.createHashedCodec(Registry.BLOCK_KEY).fieldOf("replaceable").forGetter(config -> config.replaceable),
					BlockStateProvider.TYPE_CODEC.fieldOf("ground_state").forGetter(config -> config.groundState),
					BlockStateProvider.TYPE_CODEC.fieldOf("liquid_state").forGetter(config -> config.liquidState),
					PlacedFeature.REGISTRY_CODEC.fieldOf("vegetation_feature").forGetter(config -> config.vegetationFeature),
					VerticalSurfaceType.CODEC.fieldOf("surface").forGetter(config -> config.surface),
					IntProvider.createValidatingCodec(1, 128).fieldOf("depth").forGetter(config -> config.depth),
					Codec.floatRange(0.0f, 1.0f).fieldOf("extra_bottom_block_chance").forGetter(config -> config.extraBottomBlockChance),
					Codec.intRange(1, 256).fieldOf("vertical_range").forGetter(config -> config.verticalRange),
					Codec.floatRange(0.0f, 1.0f).fieldOf("vegetation_chance").forGetter(config -> config.vegetationChance),
					IntProvider.VALUE_CODEC.fieldOf("xz_radius").forGetter(config -> config.horizontalRadius),
					Codec.floatRange(0.0f, 1.0f).fieldOf("extra_edge_column_chance").forGetter(config -> config.extraEdgeColumnChance)
				)
				.apply(instance, Config::new)
		);

		private final BlockStateProvider liquidState;

		public Config(TagKey<Block> replaceable, BlockStateProvider groundState, BlockStateProvider liquidState, Holder<PlacedFeature> vegetationFeature, VerticalSurfaceType surface, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider horizontalRadius, float extraEdgeColumnChance) {
			super(replaceable, groundState, vegetationFeature, surface, depth, extraBottomBlockChance, verticalRange, vegetationChance, horizontalRadius, extraEdgeColumnChance);
			this.liquidState = liquidState;
		}
	}
}
