package com.mmodding.library.block.api.catalog;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleTallPlantBlock extends DoublePlantBlock {

	private final Predicate<BlockState> placementConditions;

	public SimpleTallPlantBlock(Properties settings) {
		this(floor -> floor.is(BlockTags.DIRT) || floor.is(Blocks.FARMLAND), settings);
	}

	public SimpleTallPlantBlock(Predicate<BlockState> placementConditions, Properties settings) {
		super(settings);
		this.placementConditions = placementConditions;
	}

	@Override
	protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
		return floor.isFaceSturdy(world, pos, Direction.UP) && this.placementConditions.test(floor);
	}
}
