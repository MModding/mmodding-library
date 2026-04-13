package com.mmodding.library.block.api.catalog;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleSaplingBlock extends SaplingBlock {

	private final Predicate<BlockState> placementConditions;

	public SimpleSaplingBlock(TreeGrower grower, Properties settings) {
		this(grower, floor -> floor.is(BlockTags.DIRT) || floor.is(Blocks.FARMLAND), settings);
	}

	public SimpleSaplingBlock(TreeGrower grower, Predicate<BlockState> placementConditions, Properties settings) {
		super(grower, settings);
		this.placementConditions = placementConditions;
	}

	@Override
	protected boolean mayPlaceOn(BlockState floor, BlockGetter getter, BlockPos pos) {
		return floor.isFaceSturdy(getter, pos, Direction.UP) && this.placementConditions.test(floor);
	}
}
