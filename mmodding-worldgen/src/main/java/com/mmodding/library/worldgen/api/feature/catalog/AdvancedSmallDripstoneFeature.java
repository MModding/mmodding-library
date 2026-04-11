package com.mmodding.library.worldgen.api.feature.catalog;

import com.mmodding.library.worldgen.impl.feature.helper.AdvancedDripstoneUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AdvancedSmallDripstoneFeature extends Feature<AdvancedSmallDripstoneFeature.Config> {

	public AdvancedSmallDripstoneFeature(Codec<Config> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<Config> context) {
		Block pointedDripstoneBlock = context.config().pointedDripstoneBlock.getState(context.random(), context.origin()).getBlock();
		Block dripstoneBlock = context.config().dripstoneBlock.getState(context.random(), context.origin()).getBlock();

		LevelAccessor worldAccess = context.level();
		BlockPos blockPos = context.origin();
		RandomSource randomGenerator = context.random();
		Config pointedDripstoneFeatureConfig = context.config();
		Optional<Direction> optional = this.getDirection(worldAccess, blockPos, randomGenerator);
		if (optional.isEmpty()) {
			return false;
		} else {
			BlockPos patchPos = blockPos.relative(optional.get().getOpposite());
			this.generateDripstoneBlocksPatch(dripstoneBlock, worldAccess, randomGenerator, patchPos, pointedDripstoneFeatureConfig);
			int i = randomGenerator.nextFloat() < pointedDripstoneFeatureConfig.chanceOfTallerDripstone
				&& DripstoneUtils.isEmptyOrWater(worldAccess.getBlockState(blockPos.relative(optional.get())))
				? 2
				: 1;
			AdvancedDripstoneUtils.generatePointedDripstone(pointedDripstoneBlock, worldAccess, blockPos, optional.get(), i, false);
			return true;
		}
	}

	private Optional<Direction> getDirection(LevelAccessor world, BlockPos pos, RandomSource random) {
		boolean bl = DripstoneUtils.isDripstoneBase(world.getBlockState(pos.above()));
		boolean bl2 = DripstoneUtils.isDripstoneBase(world.getBlockState(pos.below()));
		if (bl && bl2) {
			return Optional.of(random.nextBoolean() ? Direction.DOWN : Direction.UP);
		} else if (bl) {
			return Optional.of(Direction.DOWN);
		} else {
			return bl2 ? Optional.of(Direction.UP) : Optional.empty();
		}
	}

	private void generateDripstoneBlocksPatch(Block dripstoneBlock, LevelAccessor world, RandomSource random, BlockPos pos, Config config) {
		AdvancedDripstoneUtils.generateDripstoneBlock(dripstoneBlock, world, pos);

		for(Direction direction : Direction.Plane.HORIZONTAL) {
			if (!(random.nextFloat() > config.chanceOfDirectionalSpread)) {
				BlockPos blockPos = pos.relative(direction);
				AdvancedDripstoneUtils.generateDripstoneBlock(dripstoneBlock, world, blockPos);
				if (!(random.nextFloat() > config.chanceOfSpreadRadius2)) {
					BlockPos blockPos2 = blockPos.relative(Direction.getRandom(random));
					AdvancedDripstoneUtils.generateDripstoneBlock(dripstoneBlock, world, blockPos2);
					if (!(random.nextFloat() > config.chanceOfSpreadRadius3)) {
						BlockPos blockPos3 = blockPos2.relative(Direction.getRandom(random));
						AdvancedDripstoneUtils.generateDripstoneBlock(dripstoneBlock, world, blockPos3);
					}
				}
			}
		}
	}

	public static class Config extends PointedDripstoneConfiguration {

		public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			BlockStateProvider.CODEC.fieldOf("pointed_dripstone_block").forGetter(config -> config.pointedDripstoneBlock),
			BlockStateProvider.CODEC.fieldOf("dripstone_block").forGetter(config -> config.dripstoneBlock),
			Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_taller_dripstone").orElse(0.2F).forGetter(config -> config.chanceOfTallerDripstone),
			Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_directional_spread").orElse(0.7F).forGetter(config -> config.chanceOfDirectionalSpread),
			Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius2").orElse(0.5F).forGetter(config -> config.chanceOfSpreadRadius2),
			Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius3").orElse(0.5F).forGetter(config -> config.chanceOfSpreadRadius3)
		).apply(instance, Config::new));

		private final BlockStateProvider pointedDripstoneBlock;
		private final BlockStateProvider dripstoneBlock;

		public Config(BlockStateProvider pointedDripstoneBlock, BlockStateProvider dripstoneBlock, float tallerDripstoneChance, float directionalSpreadChance, float spreadRadius2Chance, float spreadRadius3Chance) {
			super(tallerDripstoneChance, directionalSpreadChance, spreadRadius2Chance, spreadRadius3Chance);
			this.pointedDripstoneBlock = pointedDripstoneBlock;
			this.dripstoneBlock = dripstoneBlock;
		}
	}
}
