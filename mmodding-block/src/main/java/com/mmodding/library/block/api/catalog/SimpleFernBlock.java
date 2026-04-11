package com.mmodding.library.block.api.catalog;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleFernBlock extends TallGrassBlock {

	private final Predicate<BlockState> placementConditions;

	public SimpleFernBlock(Properties settings) {
		this(floor -> floor.is(BlockTags.DIRT) || floor.is(Blocks.FARMLAND), settings);
	}

	public SimpleFernBlock(Predicate<BlockState> placementConditions, Properties settings) {
		super(settings);
		this.placementConditions = placementConditions;
	}

	@Override
	protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
		return floor.isFaceSturdy(world, pos, Direction.UP) && this.placementConditions.test(floor);
	}
}
