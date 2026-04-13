package com.mmodding.library.worldgen.api.feature.catalog;

import com.mmodding.library.worldgen.api.feature.catalog.configurations.AdvancedLargeDripstoneConfiguration;
import com.mojang.serialization.Codec;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

public class AdvancedLargeDripstoneFeature extends Feature<AdvancedLargeDripstoneConfiguration> {

	public AdvancedLargeDripstoneFeature(Codec<AdvancedLargeDripstoneConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<AdvancedLargeDripstoneConfiguration> context) {

		Block dripstoneBlock = context.config().dripstoneBlock.getState(context.level(), context.random(), context.origin()).getBlock();

		WorldGenLevel structureWorldAccess = context.level();
		BlockPos blockPos = context.origin();
		AdvancedLargeDripstoneConfiguration largeStoneSpikeFeatureConfig = context.config();
		RandomSource randomGenerator = context.random();
		if (DripstoneUtils.isEmptyOrWater(structureWorldAccess, blockPos)) {
			Optional<Column> optional = Column.scan(
				structureWorldAccess, blockPos, largeStoneSpikeFeatureConfig.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isDripstoneBaseOrLava
			);
			if (optional.isPresent() && optional.get() instanceof Column.Range bounded) {
				if (bounded.height() < 4) {
					return false;
				} else {
					int i = (int)((float)bounded.height() * largeStoneSpikeFeatureConfig.maxColumnRadiusToCaveHeightRatio);
					int j = Mth.clamp(i, largeStoneSpikeFeatureConfig.columnRadius.minInclusive(), largeStoneSpikeFeatureConfig.columnRadius.maxInclusive());
					int k = Mth.randomBetweenInclusive(randomGenerator, largeStoneSpikeFeatureConfig.columnRadius.minInclusive(), j);
					StoneSpikeGenerator ceilingSpikeGenerator = createGenerator(
						blockPos.atY(bounded.ceiling() - 1),
						false,
						randomGenerator,
						k,
						largeStoneSpikeFeatureConfig.stalactiteBluntness,
						largeStoneSpikeFeatureConfig.heightScale
					);
					StoneSpikeGenerator floorSpikeGenerator = createGenerator(
						blockPos.atY(bounded.floor() + 1),
						true,
						randomGenerator,
						k,
						largeStoneSpikeFeatureConfig.stalagmiteBluntness,
						largeStoneSpikeFeatureConfig.heightScale
					);
					WindModifier windModifier;
					if (ceilingSpikeGenerator.generateWind(largeStoneSpikeFeatureConfig) && floorSpikeGenerator.generateWind(largeStoneSpikeFeatureConfig)) {
						windModifier = new WindModifier(blockPos.getY(), randomGenerator, largeStoneSpikeFeatureConfig.windSpeed);
					} else {
						windModifier = WindModifier.create();
					}

					boolean bl = ceilingSpikeGenerator.canGenerate(structureWorldAccess, windModifier);
					boolean bl2 = floorSpikeGenerator.canGenerate(structureWorldAccess, windModifier);
					if (bl) {
						ceilingSpikeGenerator.generate(dripstoneBlock, structureWorldAccess, randomGenerator, windModifier);
					}

					if (bl2) {
						floorSpikeGenerator.generate(dripstoneBlock, structureWorldAccess, randomGenerator, windModifier);
					}

					return true;
				}
			} else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	private static StoneSpikeGenerator createGenerator(
		BlockPos pos, boolean isStalagmite, RandomSource random, int scale, FloatProvider bluntness, FloatProvider heightScale
	) {
		return new AdvancedLargeDripstoneFeature.StoneSpikeGenerator(pos, isStalagmite, scale, bluntness.sample(random), heightScale.sample(random));
	}

	public static final class StoneSpikeGenerator {

		private BlockPos pos;
		private final boolean isStalagmite;
		private int scale;
		private final double bluntness;
		private final double heightScale;

		StoneSpikeGenerator(BlockPos pos, boolean isStalagmite, int scale, double bluntness, double heightScale) {
			this.pos = pos;
			this.isStalagmite = isStalagmite;
			this.scale = scale;
			this.bluntness = bluntness;
			this.heightScale = heightScale;
		}

		private int getBaseScale() {
			return this.scale(0.0F);
		}

		boolean canGenerate(WorldGenLevel world, AdvancedLargeDripstoneFeature.WindModifier wind) {
			while(this.scale > 1) {
				BlockPos.MutableBlockPos mutable = this.pos.mutable();
				int i = Math.min(10, this.getBaseScale());

				for(int j = 0; j < i; j++) {
					if (world.getBlockState(mutable).is(Blocks.LAVA)) {
						return false;
					}

					if (DripstoneUtils.isCircleMostlyEmbeddedInStone(world, wind.modify(mutable), this.scale)) {
						this.pos = mutable;
						return true;
					}

					mutable.move(this.isStalagmite ? Direction.DOWN : Direction.UP);
				}

				this.scale /= 2;
			}

			return false;
		}

		private int scale(float height) {
			return (int) DripstoneUtils.getDripstoneHeight(height, this.scale, this.heightScale, this.bluntness);
		}

		void generate(Block stoneBlock, WorldGenLevel world, RandomSource random, AdvancedLargeDripstoneFeature.WindModifier wind) {
			for(int i = -this.scale; i <= this.scale; i++) {
				for(int j = -this.scale; j <= this.scale; j++) {
					float f = Mth.sqrt((float)(i * i + j * j));
					if (!(f > (float) this.scale)) {
						int k = this.scale(f);
						if (k > 0) {
							if ((double) random.nextFloat() < 0.2) {
								k = (int) ((float) k * Mth.randomBetween(random, 0.8f, 1.0f));
							}

							BlockPos.MutableBlockPos mutable = this.pos.offset(i, 0, j).mutable();
							boolean bl = false;
							int l = this.isStalagmite ? world.getHeight(Heightmap.Types.WORLD_SURFACE_WG, mutable.getX(), mutable.getZ()) : Integer.MAX_VALUE;

							for(int m = 0; m < k && mutable.getY() < l; m++) {
								BlockPos blockPos = wind.modify(mutable);
								if (DripstoneUtils.isEmptyOrWaterOrLava(world, blockPos)) {
									bl = true;
									world.setBlock(blockPos, stoneBlock.defaultBlockState(), Block.UPDATE_CLIENTS);
								} else if (bl && world.getBlockState(blockPos).is(BlockTags.BASE_STONE_OVERWORLD)) {
									break;
								}

								mutable.move(this.isStalagmite ? Direction.UP : Direction.DOWN);
							}
						}
					}
				}
			}
		}

		boolean generateWind(AdvancedLargeDripstoneConfiguration config) {
			return this.scale >= config.minRadiusForWind && this.bluntness >= (double) config.minBluntnessForWind;
		}
	}

	public static final class WindModifier {

		private final int y;
		private final Vec3 wind;

		WindModifier(int y, RandomSource random, FloatProvider windSpeed) {
			this.y = y;
			float f = windSpeed.sample(random);
			float g = Mth.randomBetween(random, 0.0F, (float) Math.PI);
			this.wind = new Vec3(Mth.cos(g) * f, 0.0, Mth.sin(g) * f);
		}

		private WindModifier() {
			this.y = 0;
			this.wind = null;
		}

		static WindModifier create() {
			return new WindModifier();
		}

		BlockPos modify(BlockPos pos) {
			if (this.wind == null) {
				return pos;
			} else {
				int i = this.y - pos.getY();
				Vec3 vec3d = this.wind.scale(i);
				return pos.offset(BlockPos.containing(vec3d.x, 0.0, vec3d.z));
			}
		}
	}
}
