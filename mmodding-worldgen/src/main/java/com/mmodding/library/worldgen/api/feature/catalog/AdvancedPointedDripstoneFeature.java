package com.mmodding.library.worldgen.api.feature.catalog;

import com.mmodding.library.worldgen.api.feature.catalog.configurations.AdvancedPointedDripstoneConfiguration;
import com.mmodding.library.worldgen.impl.feature.helper.AdvancedDripstoneUtils;
import com.mojang.serialization.Codec;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class AdvancedPointedDripstoneFeature extends Feature<AdvancedPointedDripstoneConfiguration> {

	public AdvancedPointedDripstoneFeature(Codec<AdvancedPointedDripstoneConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<AdvancedPointedDripstoneConfiguration> context) {
		Block pointedDripstoneBlock = context.config().pointedDripstoneBlock.getState(context.level(), context.random(), context.origin()).getBlock();
		Block dripstoneBlock = context.config().dripstoneBlock.getState(context.level(), context.random(), context.origin()).getBlock();

		LevelAccessor worldAccess = context.level();
		BlockPos blockPos = context.origin();
		RandomSource randomGenerator = context.random();
		AdvancedPointedDripstoneConfiguration pointedDripstoneFeatureConfig = context.config();
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

	private Optional<Direction> getDirection(LevelAccessor level, BlockPos pos, RandomSource random) {
		boolean bl = DripstoneUtils.isDripstoneBase(level.getBlockState(pos.above()));
		boolean bl2 = DripstoneUtils.isDripstoneBase(level.getBlockState(pos.below()));
		if (bl && bl2) {
			return Optional.of(random.nextBoolean() ? Direction.DOWN : Direction.UP);
		} else if (bl) {
			return Optional.of(Direction.DOWN);
		} else {
			return bl2 ? Optional.of(Direction.UP) : Optional.empty();
		}
	}

	private void generateDripstoneBlocksPatch(Block dripstoneBlock, LevelAccessor world, RandomSource random, BlockPos pos, AdvancedPointedDripstoneConfiguration config) {
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
}
