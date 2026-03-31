package com.mmodding.library.worldgen.api.feature.catalog;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LargeDripstoneFeatureConfig;
import net.minecraft.world.gen.feature.util.CaveSurface;
import net.minecraft.world.gen.feature.util.DripstoneHelper;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.Optional;

public class AdvancedLargeDripstoneFeature extends Feature<AdvancedLargeDripstoneFeature.Config> {

	public AdvancedLargeDripstoneFeature(Codec<AdvancedLargeDripstoneFeature.Config> codec) {
		super(codec);
	}

	@Override
	public boolean generate(FeatureContext<AdvancedLargeDripstoneFeature.Config> context) {

		Block dripstoneBlock = context.getConfig().stoneBlock.get(context.getRandom(), context.getOrigin()).getBlock();

		StructureWorldAccess structureWorldAccess = context.getWorld();
		BlockPos blockPos = context.getOrigin();
		AdvancedLargeDripstoneFeature.Config largeStoneSpikeFeatureConfig = context.getConfig();
		Random randomGenerator = context.getRandom();
		if (DripstoneHelper.canGenerate(structureWorldAccess, blockPos)) {
			Optional<CaveSurface> optional = CaveSurface.create(
				structureWorldAccess, blockPos, largeStoneSpikeFeatureConfig.floorToCeilingSearchRange, DripstoneHelper::canGenerate, DripstoneHelper::canReplaceOrLava
			);
			if (optional.isPresent() && optional.get() instanceof CaveSurface.Bounded bounded) {
				if (bounded.getHeight() < 4) {
					return false;
				} else {
					int i = (int)((float)bounded.getHeight() * largeStoneSpikeFeatureConfig.maxColumnRadiusToCaveHeightRatio);
					int j = MathHelper.clamp(i, largeStoneSpikeFeatureConfig.columnRadius.getMin(), largeStoneSpikeFeatureConfig.columnRadius.getMax());
					int k = MathHelper.nextBetween(randomGenerator, largeStoneSpikeFeatureConfig.columnRadius.getMin(), j);
					StoneSpikeGenerator ceilingSpikeGenerator = createGenerator(
						blockPos.withY(bounded.getCeiling() - 1),
						false,
						randomGenerator,
						k,
						largeStoneSpikeFeatureConfig.stalactiteBluntness,
						largeStoneSpikeFeatureConfig.heightScale
					);
					StoneSpikeGenerator floorSpikeGenerator = createGenerator(
						blockPos.withY(bounded.getFloor() + 1),
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
		BlockPos pos, boolean isStalagmite, Random random, int scale, FloatProvider bluntness, FloatProvider heightScale
	) {
		return new AdvancedLargeDripstoneFeature.StoneSpikeGenerator(pos, isStalagmite, scale, bluntness.get(random), heightScale.get(random));
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

		boolean canGenerate(StructureWorldAccess world, AdvancedLargeDripstoneFeature.WindModifier wind) {
			while(this.scale > 1) {
				BlockPos.Mutable mutable = this.pos.mutableCopy();
				int i = Math.min(10, this.getBaseScale());

				for(int j = 0; j < i; j++) {
					if (world.getBlockState(mutable).isOf(Blocks.LAVA)) {
						return false;
					}

					if (DripstoneHelper.canGenerateBase(world, wind.modify(mutable), this.scale)) {
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
			return (int) DripstoneHelper.scaleHeightFromRadius(height, this.scale, this.heightScale, this.bluntness);
		}

		void generate(Block stoneBlock, StructureWorldAccess world, Random random, AdvancedLargeDripstoneFeature.WindModifier wind) {
			for(int i = -this.scale; i <= this.scale; i++) {
				for(int j = -this.scale; j <= this.scale; j++) {
					float f = MathHelper.sqrt((float)(i * i + j * j));
					if (!(f > (float) this.scale)) {
						int k = this.scale(f);
						if (k > 0) {
							if ((double) random.nextFloat() < 0.2) {
								k = (int) ((float) k * MathHelper.nextBetween(random, 0.8f, 1.0f));
							}

							BlockPos.Mutable mutable = this.pos.add(i, 0, j).mutableCopy();
							boolean bl = false;
							int l = this.isStalagmite ? world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, mutable.getX(), mutable.getZ()) : Integer.MAX_VALUE;

							for(int m = 0; m < k && mutable.getY() < l; m++) {
								BlockPos blockPos = wind.modify(mutable);
								if (DripstoneHelper.canGenerateOrLava(world, blockPos)) {
									bl = true;
									world.setBlockState(blockPos, stoneBlock.getDefaultState(), Block.NOTIFY_LISTENERS);
								} else if (bl && world.getBlockState(blockPos).isIn(BlockTags.BASE_STONE_OVERWORLD)) {
									break;
								}

								mutable.move(this.isStalagmite ? Direction.UP : Direction.DOWN);
							}
						}
					}
				}
			}
		}

		boolean generateWind(AdvancedLargeDripstoneFeature.Config config) {
			return this.scale >= config.minRadiusForWind && this.bluntness >= (double) config.minBluntnessForWind;
		}
	}

	public static final class WindModifier {

		private final int y;
		private final Vec3d wind;

		WindModifier(int y, Random random, FloatProvider windSpeed) {
			this.y = y;
			float f = windSpeed.get(random);
			float g = MathHelper.nextBetween(random, 0.0F, (float) Math.PI);
			this.wind = new Vec3d(MathHelper.cos(g) * f, 0.0, MathHelper.sin(g) * f);
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
				Vec3d vec3d = this.wind.multiply(i);
				return pos.add(BlockPos.ofFloored(vec3d.x, 0.0, vec3d.z));
			}
		}
	}

	public static class Config extends LargeDripstoneFeatureConfig {

		public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			BlockStateProvider.TYPE_CODEC.fieldOf("dripstone_block").forGetter(config -> config.stoneBlock),
			Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").orElse(30).forGetter(config -> config.floorToCeilingSearchRange),
			IntProvider.createValidatingCodec(1, 60).fieldOf("column_radius").forGetter(config -> config.columnRadius),
			FloatProvider.createValidatedCodec(0.0F, 20.0F).fieldOf("height_scale").forGetter(config -> config.heightScale),
			Codec.floatRange(0.1F, 1.0F).fieldOf("max_column_radius_to_cave_height_ratio").forGetter(config -> config.maxColumnRadiusToCaveHeightRatio),
			FloatProvider.createValidatedCodec(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter(config -> config.stalactiteBluntness),
			FloatProvider.createValidatedCodec(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter(config -> config.stalagmiteBluntness),
			FloatProvider.createValidatedCodec(0.0F, 2.0F).fieldOf("wind_speed").forGetter(config -> config.windSpeed),
			Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter(config -> config.minRadiusForWind),
			Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter(config -> config.minBluntnessForWind)
		).apply(instance, Config::new));

		private final BlockStateProvider stoneBlock;

		public Config(BlockStateProvider stoneBlock, int floorToCeilingSearchRange, IntProvider columnRadius, FloatProvider heightScale, float maxColumnRadiusToCaveHeightRatio, FloatProvider stalactiteBluntness, FloatProvider stalagmiteBluntness, FloatProvider windSpeed, int minRadiusForWind, float minBluntnessForWind) {
			super(floorToCeilingSearchRange, columnRadius, heightScale, maxColumnRadiusToCaveHeightRatio, stalactiteBluntness, stalagmiteBluntness, windSpeed, minRadiusForWind, minBluntnessForWind);
			this.stoneBlock = stoneBlock;
		}
	}
}