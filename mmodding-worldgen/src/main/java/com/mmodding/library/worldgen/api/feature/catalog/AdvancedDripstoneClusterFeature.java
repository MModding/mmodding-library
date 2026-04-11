package com.mmodding.library.worldgen.api.feature.catalog;

import com.mmodding.library.worldgen.impl.feature.helper.AdvancedDripstoneUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
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
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AdvancedDripstoneClusterFeature extends Feature<AdvancedDripstoneClusterFeature.Config> {

	public AdvancedDripstoneClusterFeature(Codec<AdvancedDripstoneClusterFeature.Config> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<AdvancedDripstoneClusterFeature.Config> context) {

		Block pointedDripstoneBlock = context.config().pointedDripstoneBlock.getState(context.random(), context.origin()).getBlock();
		Block dripstoneBlock = context.config().dripstoneBlock.getState(context.random(), context.origin()).getBlock();

		WorldGenLevel structureWorldAccess = context.level();
		BlockPos blockPos = context.origin();
		DripstoneClusterConfiguration dripstoneClusterFeatureConfig = context.config();
		RandomSource random = context.random();
		if (DripstoneUtils.isEmptyOrWater(structureWorldAccess, blockPos)) {
			int i = dripstoneClusterFeatureConfig.height.sample(random);
			float f = dripstoneClusterFeatureConfig.wetness.sample(random);
			float g = dripstoneClusterFeatureConfig.density.sample(random);
			int j = dripstoneClusterFeatureConfig.radius.sample(random);
			int k = dripstoneClusterFeatureConfig.radius.sample(random);

			for(int l = -j; l <= j; l++) {
				for(int m = -k; m <= k; m++) {
					double d = this.dripstoneChance(j, k, l, m, dripstoneClusterFeatureConfig);
					BlockPos blockPos2 = blockPos.offset(l, 0, m);
					this.generate(pointedDripstoneBlock, dripstoneBlock, structureWorldAccess, random, blockPos2, l, m, f, d, i, g, dripstoneClusterFeatureConfig);
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
		WorldGenLevel world,
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
			world, pos, config.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isNeitherEmptyNorWater
		);
		if (optional.isPresent()) {
			OptionalInt optionalInt = optional.get().getCeiling();
			OptionalInt optionalInt2 = optional.get().getFloor();
			if (optionalInt.isPresent() || optionalInt2.isPresent()) {
				boolean bl = random.nextFloat() < wetness;
				Column caveSurface;
				if (bl && optionalInt2.isPresent() && this.canWaterSpawn(pointedDripstoneBlock, dripstoneBlock, world, pos.atY(optionalInt2.getAsInt()))) {
					int i = optionalInt2.getAsInt();
					caveSurface = optional.get().withFloor(OptionalInt.of(i - 1));
					world.setBlock(pos.atY(i), Blocks.WATER.defaultBlockState(), Block.UPDATE_CLIENTS);
				} else {
					caveSurface = optional.get();
				}

				OptionalInt optionalInt3 = caveSurface.getFloor();
				boolean bl2 = random.nextDouble() < dripstoneChance;
				int l;
				if (optionalInt.isPresent() && bl2 && this.isNotLava(world, pos.atY(optionalInt.getAsInt()))) {
					int j = config.dripstoneBlockLayerThickness.sample(random);
					this.placeDripstoneBlocks(dripstoneBlock, world, pos.atY(optionalInt.getAsInt()), j, Direction.UP);
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
				if (optionalInt3.isPresent() && bl3 && this.isNotLava(world, pos.atY(optionalInt3.getAsInt()))) {
					int m = config.dripstoneBlockLayerThickness.sample(random);
					this.placeDripstoneBlocks(dripstoneBlock, world, pos.atY(optionalInt3.getAsInt()), m, Direction.DOWN);
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
					AdvancedDripstoneUtils.generatePointedDripstone(pointedDripstoneBlock, world, pos.atY(optionalInt.getAsInt() - 1), Direction.DOWN, m, bl4);
				}

				if (optionalInt3.isPresent()) {
					AdvancedDripstoneUtils.generatePointedDripstone(pointedDripstoneBlock, world, pos.atY(optionalInt3.getAsInt() + 1), Direction.UP, t, bl4);
				}
			}
		}
	}

	private boolean isNotLava(LevelReader world, BlockPos pos) {
		return !world.getBlockState(pos).is(Blocks.LAVA);
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

	public static class Config extends DripstoneClusterConfiguration {

		public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			BlockStateProvider.CODEC.fieldOf("pointed_dripstone_block").forGetter(config -> config.pointedDripstoneBlock),
			BlockStateProvider.CODEC.fieldOf("dripstone_block").forGetter(config -> config.dripstoneBlock),
			Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").forGetter(config -> config.floorToCeilingSearchRange),
			IntProvider.codec(1, 128).fieldOf("height").forGetter(config -> config.height),
			IntProvider.codec(1, 128).fieldOf("radius").forGetter(config -> config.radius),
			Codec.intRange(0, 64).fieldOf("max_stalagmite_stalactite_height_diff").forGetter(config -> config.maxStalagmiteStalactiteHeightDiff),
			Codec.intRange(1, 64).fieldOf("height_deviation").forGetter(config -> config.heightDeviation),
			IntProvider.codec(0, 128).fieldOf("dripstone_block_layer_thickness").forGetter(config -> config.dripstoneBlockLayerThickness),
			FloatProvider.codec(0.0F, 2.0F).fieldOf("density").forGetter(config -> config.density),
			FloatProvider.codec(0.0F, 2.0F).fieldOf("wetness").forGetter(config -> config.wetness),
			Codec.floatRange(0.0F, 1.0F)
				.fieldOf("chance_of_dripstone_column_at_max_distance_from_center")
				.forGetter(config -> config.chanceOfDripstoneColumnAtMaxDistanceFromCenter),
			Codec.intRange(1, 64)
				.fieldOf("max_distance_from_edge_affecting_chance_of_dripstone_column")
				.forGetter(config -> config.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn),
			Codec.intRange(1, 64).fieldOf("max_distance_from_center_affecting_height_bias").forGetter(config -> config.maxDistanceFromCenterAffectingHeightBias)
		).apply(instance, Config::new));

		private final BlockStateProvider pointedDripstoneBlock;
		private final BlockStateProvider dripstoneBlock;

		public Config(BlockStateProvider pointedDripstoneBlock, BlockStateProvider dripstoneBlock, int floorToCeilingSearchRange, IntProvider height, IntProvider radius, int maxStalagmiteStalactiteHeightDiff, int heightDeviation, IntProvider dripstoneBlockLayerThickness, FloatProvider density, FloatProvider wetness, float wetnessMean, int maxDistanceFromCenterAffectingChanceOfDripstoneColumn, int maxDistanceFromCenterAffectingHeightBias) {
			super(floorToCeilingSearchRange, height, radius, maxStalagmiteStalactiteHeightDiff, heightDeviation, dripstoneBlockLayerThickness, density, wetness, wetnessMean, maxDistanceFromCenterAffectingChanceOfDripstoneColumn, maxDistanceFromCenterAffectingHeightBias);
			this.pointedDripstoneBlock = pointedDripstoneBlock;
			this.dripstoneBlock = dripstoneBlock;
		}
	}
}
