package com.mmodding.library.worldgen.api.feature.catalog;

import com.mmodding.library.worldgen.api.feature.catalog.configurations.AdvancedDripstoneClusterConfiguration;
import com.mmodding.library.worldgen.impl.feature.helper.AdvancedDripstoneUtils;
import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;

public class AdvancedDripstoneClusterFeature extends Feature<AdvancedDripstoneClusterConfiguration> {

	public AdvancedDripstoneClusterFeature(Codec<AdvancedDripstoneClusterConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<AdvancedDripstoneClusterConfiguration> context) {

		Block pointedDripstoneBlock = context.config().pointedDripstoneBlock.getState(context.level(), context.random(), context.origin()).getBlock();
		Block dripstoneBlock = context.config().dripstoneBlock.getState(context.level(), context.random(), context.origin()).getBlock();

		WorldGenLevel level = context.level();
		BlockPos blockPos = context.origin();
		DripstoneClusterConfiguration dripstoneClusterFeatureConfig = context.config();
		RandomSource random = context.random();
		if (DripstoneUtils.isEmptyOrWater(level, blockPos)) {
			int i = dripstoneClusterFeatureConfig.height.sample(random);
			float f = dripstoneClusterFeatureConfig.wetness.sample(random);
			float g = dripstoneClusterFeatureConfig.density.sample(random);
			int j = dripstoneClusterFeatureConfig.radius.sample(random);
			int k = dripstoneClusterFeatureConfig.radius.sample(random);

			for(int l = -j; l <= j; l++) {
				for(int m = -k; m <= k; m++) {
					double d = this.dripstoneChance(j, k, l, m, dripstoneClusterFeatureConfig);
					BlockPos blockPos2 = blockPos.offset(l, 0, m);
					this.generate(pointedDripstoneBlock, dripstoneBlock, level, random, blockPos2, l, m, f, d, i, g, dripstoneClusterFeatureConfig);
				}
			}

			return true;
		}
		else {
			return false;
		}
	}

	private void generate(
		Block pointedDripstoneBlock,
		Block dripstoneBlock,
		WorldGenLevel level,
		RandomSource random,
		BlockPos pos,
		int localX,
		int localZ,
		float wetness,
		double dripstoneChance,
		int height,
		float density,
		DripstoneClusterConfiguration config
	) {
		Optional<Column> optional = Column.scan(
			level, pos, config.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isNeitherEmptyNorWater
		);
		if (optional.isPresent()) {
			OptionalInt optionalInt = optional.get().getCeiling();
			OptionalInt optionalInt2 = optional.get().getFloor();
			if (optionalInt.isPresent() || optionalInt2.isPresent()) {
				boolean bl = random.nextFloat() < wetness;
				Column caveSurface;
				if (bl && optionalInt2.isPresent() && this.canWaterSpawn(pointedDripstoneBlock, dripstoneBlock, level, pos.atY(optionalInt2.getAsInt()))) {
					int i = optionalInt2.getAsInt();
					caveSurface = optional.get().withFloor(OptionalInt.of(i - 1));
					level.setBlock(pos.atY(i), Blocks.WATER.defaultBlockState(), Block.UPDATE_CLIENTS);
				} else {
					caveSurface = optional.get();
				}

				OptionalInt optionalInt3 = caveSurface.getFloor();
				boolean bl2 = random.nextDouble() < dripstoneChance;
				int l;
				if (optionalInt.isPresent() && bl2 && this.isNotLava(level, pos.atY(optionalInt.getAsInt()))) {
					int j = config.dripstoneBlockLayerThickness.sample(random);
					this.placeDripstoneBlocks(dripstoneBlock, level, pos.atY(optionalInt.getAsInt()), j, Direction.UP);
					int k;
					if (optionalInt3.isPresent()) {
						k = Math.min(height, optionalInt.getAsInt() - optionalInt3.getAsInt());
					} else {
						k = height;
					}

					l = this.getHeight(random, localX, localZ, density, k, config);
				} else {
					l = 0;
				}

				boolean bl3 = random.nextDouble() < dripstoneChance;
				int j;
				if (optionalInt3.isPresent() && bl3 && this.isNotLava(level, pos.atY(optionalInt3.getAsInt()))) {
					int m = config.dripstoneBlockLayerThickness.sample(random);
					this.placeDripstoneBlocks(dripstoneBlock, level, pos.atY(optionalInt3.getAsInt()), m, Direction.DOWN);
					if (optionalInt.isPresent()) {
						j = Math.max(0, l + Mth.randomBetweenInclusive(random, -config.maxStalagmiteStalactiteHeightDiff, config.maxStalagmiteStalactiteHeightDiff));
					} else {
						j = this.getHeight(random, localX, localZ, density, height, config);
					}
				} else {
					j = 0;
				}

				int t;
				int m;
				if (optionalInt.isPresent() && optionalInt3.isPresent() && optionalInt.getAsInt() - l <= optionalInt3.getAsInt() + j) {
					int n = optionalInt3.getAsInt();
					int o = optionalInt.getAsInt();
					int p = Math.max(o - l, n + 1);
					int q = Math.min(n + j, o - 1);
					int r = Mth.randomBetweenInclusive(random, p, q + 1);
					int s = r - 1;
					m = o - r;
					t = s - n;
				} else {
					m = l;
					t = j;
				}

				boolean bl4 = random.nextBoolean() && m > 0 && t > 0 && caveSurface.getHeight().isPresent() && m + t == caveSurface.getHeight().getAsInt();
				if (optionalInt.isPresent()) {
					AdvancedDripstoneUtils.generatePointedDripstone(pointedDripstoneBlock, level, pos.atY(optionalInt.getAsInt() - 1), Direction.DOWN, m, bl4);
				}

				if (optionalInt3.isPresent()) {
					AdvancedDripstoneUtils.generatePointedDripstone(pointedDripstoneBlock, level, pos.atY(optionalInt3.getAsInt() + 1), Direction.UP, t, bl4);
				}
			}
		}
	}

	private boolean isNotLava(LevelReader level, BlockPos pos) {
		return !level.getBlockState(pos).is(Blocks.LAVA);
	}

	private int getHeight(RandomSource random, int localX, int localZ, float density, int height, DripstoneClusterConfiguration config) {
		if (random.nextFloat() > density) {
			return 0;
		} else {
			int i = Math.abs(localX) + Math.abs(localZ);
			float f = (float)Mth.clampedMap(i, 0.0, config.maxDistanceFromCenterAffectingHeightBias, (double)height / 2.0, 0.0);
			return (int) clampedGaussian(random, 0.0F, (float) height, f, (float) config.heightDeviation);
		}
	}

	private boolean canWaterSpawn(Block pointedDripstoneBlock, Block dripstoneBlock, WorldGenLevel world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		if (!blockState.is(Blocks.WATER) && !blockState.is(pointedDripstoneBlock) && !blockState.is(dripstoneBlock)) {
			if (!world.getBlockState(pos.above()).getFluidState().is(FluidTags.WATER)) {
				for(Direction direction : Direction.Plane.HORIZONTAL) {
					if (!this.isStoneOrWater(world, pos.relative(direction))) {
						return false;
					}
				}
				return this.isStoneOrWater(world, pos.below());
			}
		}
		return false;
	}

	private boolean isStoneOrWater(LevelAccessor world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		return blockState.is(BlockTags.BASE_STONE_OVERWORLD) || blockState.getFluidState().is(FluidTags.WATER);
	}

	private void placeDripstoneBlocks(Block dripstoneBlock, WorldGenLevel world, BlockPos pos, int height, Direction direction) {
		BlockPos.MutableBlockPos mutable = pos.mutable();

		for(int i = 0; i < height; i++) {
			if (!AdvancedDripstoneUtils.generateDripstoneBlock(dripstoneBlock, world, mutable)) {
				return;
			}

			mutable.move(direction);
		}
	}

	private double dripstoneChance(int radiusX, int radiusZ, int localX, int localZ, DripstoneClusterConfiguration config) {
		int a = radiusX - Math.abs(localX);
		int b = radiusZ - Math.abs(localZ);
		int c = Math.min(a, b);
		return Mth.clampedMap(
			(float) c, 0.0F, (float) config.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn, config.chanceOfDripstoneColumnAtMaxDistanceFromCenter, 1.0f
		);
	}

	private static float clampedGaussian(RandomSource random, float min, float max, float mean, float deviation) {
		return ClampedNormalFloat.sample(random, mean, deviation, min, max);
	}
}
