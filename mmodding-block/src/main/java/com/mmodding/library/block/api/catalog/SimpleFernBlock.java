package com.mmodding.library.block.api.catalog;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FernBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import java.util.function.Predicate;

public class SimpleFernBlock extends FernBlock {

	private final Predicate<BlockState> placementConditions;

	public SimpleFernBlock(Settings settings) {
		this(floor -> floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND), settings);
	}

	public SimpleFernBlock(Predicate<BlockState> placementConditions, Settings settings) {
		super(settings);
		this.placementConditions = placementConditions;
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isSideSolidFullSquare(world, pos, Direction.UP) && this.placementConditions.test(floor);
	}
}
