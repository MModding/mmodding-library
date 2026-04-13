package com.mmodding.library.worldgen.api.feature.catalog;

import com.mmodding.library.worldgen.api.feature.catalog.configurations.AdvancedLiquidVegetationPatchConfiguration;
import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

public class AdvancedLiquidVegetationPatchFeature extends Feature<AdvancedLiquidVegetationPatchConfiguration> {

	public AdvancedLiquidVegetationPatchFeature(Codec<AdvancedLiquidVegetationPatchConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<AdvancedLiquidVegetationPatchConfiguration> context) {
		WorldGenLevel world = context.level();
		AdvancedLiquidVegetationPatchConfiguration config = context.config();
		RandomSource random = context.random();
		BlockPos pos = context.origin();
		Predicate<BlockState> predicate = state -> state.is(config.replaceable);
		Set<BlockPos> positions = this.placeGroundAndGetPositions(world, config, random, pos, predicate, config.xzRadius.sample(random) + 1, config.xzRadius.sample(random) + 1);
		this.generateVegetation(context, world, config, random, positions);
		return !positions.isEmpty();
	}

	protected Set<BlockPos> placeGroundAndGetPositions(WorldGenLevel level, AdvancedLiquidVegetationPatchConfiguration config, RandomSource random, BlockPos pos, Predicate<BlockState> replaceable, int radiusX, int radiusZ) {
		BlockPos.MutableBlockPos mutable = pos.mutable();
		BlockPos.MutableBlockPos result = mutable.mutable();
		Direction direction = config.surface.getDirection();
		Direction opposite = direction.getOpposite();
		Set<BlockPos> positions = new HashSet<>();

		for (int i = -radiusX; i <= radiusX; i++) {
			boolean xCheck = i == -radiusX || i == radiusX;

			for (int j = -radiusZ; j <= radiusZ; j++) {
				boolean zCheck = j == -radiusZ || j == radiusZ;
				boolean or = xCheck || zCheck;
				boolean equal = xCheck && zCheck;
				boolean xor = or && !equal;

				if (!equal && (!xor || config.extraEdgeColumnChance != 0.0f && !(random.nextFloat() > config.extraEdgeColumnChance))) {
					mutable.setWithOffset(pos, i, 0, j);

					for (int k = 0; level.isStateAtPosition(mutable, BlockBehaviour.BlockStateBase::isAir) && k < config.verticalRange; k++) {
						mutable.move(direction);
					}

					for (int l = 0; level.isStateAtPosition(mutable, state -> !state.isAir()) && l < config.verticalRange; l++) {
						mutable.move(opposite);
					}

					result.setWithOffset(mutable, config.surface.getDirection());
					BlockState blockState = level.getBlockState(result);
					if (level.isEmptyBlock(mutable) && blockState.isFaceSturdy(level, result, config.surface.getDirection().getOpposite())) {
						int depth = config.depth.sample(random) + (config.extraBottomBlockChance > 0.0f && random.nextFloat() < config.extraBottomBlockChance ? 1 : 0);
						BlockPos blockPos = result.immutable();
						if (this.placeGround(level, config, replaceable, random, result, depth)) {
							positions.add(blockPos);
						}
					}
				}
			}
		}

		Set<BlockPos> liquidPositions = new HashSet<>();
		BlockPos.MutableBlockPos liquidMutable = new BlockPos.MutableBlockPos();

		for(BlockPos blockPos : positions) {
			if (!isSolidBlockAroundPos(level, blockPos, liquidMutable)) {
				liquidPositions.add(blockPos);
			}
		}

		for(BlockPos blockPos : liquidPositions) {
			level.setBlock(blockPos, config.liquidState.getState(level, random, blockPos), Block.UPDATE_CLIENTS);
		}

		return liquidPositions.stream().map(BlockPos::above).filter(blockPos -> level.isStateAtPosition(blockPos, BlockState::isAir)).collect(Collectors.toSet());
	}

	protected void generateVegetation(FeaturePlaceContext<AdvancedLiquidVegetationPatchConfiguration> context, WorldGenLevel world, VegetationPatchConfiguration config, RandomSource random, Set<BlockPos> positions) {
		for(BlockPos pos : positions) {
			if (config.vegetationChance > 0.0f && random.nextFloat() < config.vegetationChance) {
				config.vegetationFeature.value().placeWithBiomeCheck(world, context.chunkGenerator(), random, pos.below().relative(config.surface.getDirection().getOpposite()));
			}
		}
	}

	protected boolean placeGround(WorldGenLevel level, AdvancedLiquidVegetationPatchConfiguration config, Predicate<BlockState> replaceable, RandomSource random, BlockPos.MutableBlockPos pos, int depth) {
		for (int i = 0; i < depth; ++i) {
			BlockState groundState = config.groundState.getState(level, random, pos);
			BlockState currentState = level.getBlockState(pos);
			if (!groundState.is(currentState.getBlock())) {
				if (!replaceable.test(currentState)) {
					return i != 0;
				}
				level.setBlock(pos, groundState, Block.UPDATE_CLIENTS);
				pos.move(config.surface.getDirection());
			}
		}
		return true;
	}

	private static boolean isSolidBlockAroundPos(WorldGenLevel world, BlockPos pos, BlockPos.MutableBlockPos mutablePos) {
		return isSolidBlockSide(world, pos, mutablePos, Direction.NORTH)
			|| isSolidBlockSide(world, pos, mutablePos, Direction.EAST)
			|| isSolidBlockSide(world, pos, mutablePos, Direction.SOUTH)
			|| isSolidBlockSide(world, pos, mutablePos, Direction.WEST)
			|| isSolidBlockSide(world, pos, mutablePos, Direction.DOWN);
	}

	private static boolean isSolidBlockSide(WorldGenLevel world, BlockPos pos, BlockPos.MutableBlockPos mutablePos, Direction direction) {
		mutablePos.setWithOffset(pos, direction);
		return !world.getBlockState(mutablePos).isFaceSturdy(world, mutablePos, direction.getOpposite());
	}
}
