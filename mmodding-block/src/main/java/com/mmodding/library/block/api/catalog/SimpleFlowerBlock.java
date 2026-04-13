package com.mmodding.library.block.api.catalog;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleFlowerBlock extends FlowerBlock {

	private final Predicate<BlockState> placementConditions;

	public SimpleFlowerBlock(Holder<MobEffect> suspiciousStewEffect, int effectDuration, Properties settings) {
		this(floor -> floor.is(BlockTags.DIRT) || floor.is(Blocks.FARMLAND), suspiciousStewEffect, effectDuration, settings);
	}

	public SimpleFlowerBlock(Predicate<BlockState> placementConditions, Holder<MobEffect> suspiciousStewEffect, int effectDuration, Properties settings) {
		super(suspiciousStewEffect, effectDuration, settings);
		this.placementConditions = placementConditions;
	}

	@Override
	protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
		return floor.isFaceSturdy(world, pos, Direction.UP) && this.placementConditions.test(floor);
	}
}
