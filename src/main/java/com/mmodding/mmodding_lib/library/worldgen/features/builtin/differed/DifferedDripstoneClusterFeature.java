package com.mmodding.mmodding_lib.library.worldgen.features.builtin.differed;

import com.mmodding.mmodding_lib.library.helpers.CustomDripstoneHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.floatprovider.ClampedNormalFloatProvider;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.DripstoneClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.CaveSurface;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.Optional;
import java.util.OptionalInt;

public class DifferedDripstoneClusterFeature extends Feature<DifferedDripstoneClusterFeature.Config> {

	public DifferedDripstoneClusterFeature(Codec<DifferedDripstoneClusterFeature.Config> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeatureContext<DifferedDripstoneClusterFeature.Config> context) {

		Block pointedDripstoneBlock = context.getConfig().pointedDripstoneBlock.getBlockState(context.getRandom(), context.getOrigin()).getBlock();
		Block dripstoneBlock = context.getConfig().dripstoneBlock.getBlockState(context.getRandom(), context.getOrigin()).getBlock();

		StructureWorldAccess structureWorldAccess = context.getWorld();
		BlockPos blockPos = context.getOrigin();
		DripstoneClusterFeatureConfig dripstoneClusterFeatureConfig = context.getConfig();
		RandomGenerator random = context.getRandom();
		if (CustomDripstoneHelper.canGenerate(structureWorldAccess, blockPos)) {
			int i = dripstoneClusterFeatureConfig.height.get(random);
			float f = dripstoneClusterFeatureConfig.wetness.get(random);
			float g = dripstoneClusterFeatureConfig.density.get(random);
			int j = dripstoneClusterFeatureConfig.radius.get(random);
			int k = dripstoneClusterFeatureConfig.radius.get(random);

			for(int l = -j; l <= j; l++) {
				for(int m = -k; m <= k; m++) {
					double d = this.dripstoneChance(j, k, l, m, dripstoneClusterFeatureConfig);
					BlockPos blockPos2 = blockPos.add(l, 0, m);
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
		StructureWorldAccess world,
		RandomGenerator random,
		BlockPos pos,
		int localX,
		int localZ,
		float wetness,
		double dripstoneChance,
		int height,
		float density,
		DripstoneClusterFeatureConfig config
	) {
		Optional<CaveSurface> optional = CaveSurface.create(
			world, pos, config.floorToCeilingSearchRange, CustomDripstoneHelper::canGenerate, CustomDripstoneHelper::isNeitherEmptyNorWater
		);
		if (optional.isPresent()) {
			OptionalInt optionalInt = optional.get().getCeilingHeight();
			OptionalInt optionalInt2 = optional.get().getFloorHeight();
			if (optionalInt.isPresent() || optionalInt2.isPresent()) {
				boolean bl = random.nextFloat() < wetness;
				CaveSurface caveSurface;
				if (bl && optionalInt2.isPresent() && this.canWaterSpawn(pointedDripstoneBlock, dripstoneBlock, world, pos.withY(optionalInt2.getAsInt()))) {
					int i = optionalInt2.getAsInt();
					caveSurface = optional.get().withFloor(OptionalInt.of(i - 1));
					world.setBlockState(pos.withY(i), Blocks.WATER.getDefaultState(), Block.NOTIFY_LISTENERS);
				} else {
					caveSurface = optional.get();
				}

				OptionalInt optionalInt3 = caveSurface.getFloorHeight();
				boolean bl2 = random.nextDouble() < dripstoneChance;
				int l;
				if (optionalInt.isPresent() && bl2 && this.isNotLava(world, pos.withY(optionalInt.getAsInt()))) {
					int j = config.dripstoneBlockLayerThickness.get(random);
					this.placeDripstoneBlocks(dripstoneBlock, world, pos.withY(optionalInt.getAsInt()), j, Direction.UP);
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
				if (optionalInt3.isPresent() && bl3 && this.isNotLava(world, pos.withY(optionalInt3.getAsInt()))) {
					int m = config.dripstoneBlockLayerThickness.get(random);
					this.placeDripstoneBlocks(dripstoneBlock, world, pos.withY(optionalInt3.getAsInt()), m, Direction.DOWN);
					if (optionalInt.isPresent()) {
						j = Math.max(0, l + MathHelper.nextBetween(random, -config.maxStalagmiteStalactiteHeightDiff, config.maxStalagmiteStalactiteHeightDiff));
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
					int r = MathHelper.nextBetween(random, p, q + 1);
					int s = r - 1;
					m = o - r;
					t = s - n;
				} else {
					m = l;
					t = j;
				}

				boolean bl4 = random.nextBoolean() && m > 0 && t > 0 && caveSurface.getOptionalHeight().isPresent() && m + t == caveSurface.getOptionalHeight().getAsInt();
				if (optionalInt.isPresent()) {
					CustomDripstoneHelper.generatePointedDripstone(pointedDripstoneBlock, world, pos.withY(optionalInt.getAsInt() - 1), Direction.DOWN, m, bl4);
				}

				if (optionalInt3.isPresent()) {
					CustomDripstoneHelper.generatePointedDripstone(pointedDripstoneBlock, world, pos.withY(optionalInt3.getAsInt() + 1), Direction.UP, t, bl4);
				}
			}
		}
	}

	private boolean isNotLava(WorldView world, BlockPos pos) {
		return !world.getBlockState(pos).isOf(Blocks.LAVA);
	}

	private int getHeight(RandomGenerator random, int localX, int localZ, float density, int height, DripstoneClusterFeatureConfig config) {
		if (random.nextFloat() > density) {
			return 0;
		} else {
			int i = Math.abs(localX) + Math.abs(localZ);
			float f = (float)MathHelper.clampedMap(i, 0.0, config.maxDistanceFromCenterAffectingHeightBias, (double)height / 2.0, 0.0);
			return (int) clampedGaussian(random, 0.0F, (float) height, f, (float) config.heightDeviation);
		}
	}

	private boolean canWaterSpawn(Block pointedDripstoneBlock, Block dripstoneBlock, StructureWorldAccess world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		if (!blockState.isOf(Blocks.WATER) && !blockState.isOf(pointedDripstoneBlock) && !blockState.isOf(dripstoneBlock)) {
			if (!world.getBlockState(pos.up()).getFluidState().isIn(FluidTags.WATER)) {
				for(Direction direction : Direction.Type.HORIZONTAL) {
					if (!this.isStoneOrWater(world, pos.offset(direction))) {
						return false;
					}
				}
				return this.isStoneOrWater(world, pos.down());
			}
		}
		return false;
	}

	private boolean isStoneOrWater(WorldAccess world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		return blockState.isIn(BlockTags.BASE_STONE_OVERWORLD) || blockState.getFluidState().isIn(FluidTags.WATER);
	}

	private void placeDripstoneBlocks(Block dripstoneBlock, StructureWorldAccess world, BlockPos pos, int height, Direction direction) {
		BlockPos.Mutable mutable = pos.mutableCopy();

		for(int i = 0; i < height; i++) {
			if (!CustomDripstoneHelper.generateDripstoneBlock(dripstoneBlock, world, mutable)) {
				return;
			}

			mutable.move(direction);
		}
	}

	private double dripstoneChance(int radiusX, int radiusZ, int localX, int localZ, DripstoneClusterFeatureConfig config) {
		int a = radiusX - Math.abs(localX);
		int b = radiusZ - Math.abs(localZ);
		int c = Math.min(a, b);
		return MathHelper.method_37958(
			(float) c, 0.0F, (float) config.maxDistanceFromCenterAffectingChanceOfDripstoneColumn, config.chanceOfDripstoneColumnAtMaxDistanceFromCenter, 1.0F
		);
	}

	private static float clampedGaussian(RandomGenerator random, float min, float max, float mean, float deviation) {
		return ClampedNormalFloatProvider.method_33903(random, mean, deviation, min, max);
	}

	public static class Config extends DripstoneClusterFeatureConfig {

		public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			BlockStateProvider.TYPE_CODEC.fieldOf("pointed_dripstone_block").forGetter(config -> config.pointedDripstoneBlock),
			BlockStateProvider.TYPE_CODEC.fieldOf("dripstone_block").forGetter(config -> config.dripstoneBlock),
			Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").forGetter(config -> config.floorToCeilingSearchRange),
			IntProvider.createValidatingCodec(1, 128).fieldOf("height").forGetter(config -> config.height),
			IntProvider.createValidatingCodec(1, 128).fieldOf("radius").forGetter(config -> config.radius),
			Codec.intRange(0, 64).fieldOf("max_stalagmite_stalactite_height_diff").forGetter(config -> config.maxStalagmiteStalactiteHeightDiff),
			Codec.intRange(1, 64).fieldOf("height_deviation").forGetter(config -> config.heightDeviation),
			IntProvider.createValidatingCodec(0, 128).fieldOf("dripstone_block_layer_thickness").forGetter(config -> config.dripstoneBlockLayerThickness),
			FloatProvider.createValidatedCodec(0.0F, 2.0F).fieldOf("density").forGetter(config -> config.density),
			FloatProvider.createValidatedCodec(0.0F, 2.0F).fieldOf("wetness").forGetter(config -> config.wetness),
			Codec.floatRange(0.0F, 1.0F)
				.fieldOf("chance_of_dripstone_column_at_max_distance_from_center")
				.forGetter(config -> config.chanceOfDripstoneColumnAtMaxDistanceFromCenter),
			Codec.intRange(1, 64)
				.fieldOf("max_distance_from_edge_affecting_chance_of_dripstone_column")
				.forGetter(config -> config.maxDistanceFromCenterAffectingChanceOfDripstoneColumn),
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
