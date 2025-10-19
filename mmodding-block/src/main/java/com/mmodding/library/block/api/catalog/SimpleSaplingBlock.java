package com.mmodding.library.block.api.catalog;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import java.util.function.Predicate;

public class SimpleSaplingBlock extends SaplingBlock {

	private final Predicate<BlockState> placementConditions;

	public SimpleSaplingBlock(SaplingGenerator generator, Settings settings) {
		this(generator, floor -> floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND), settings);
	}

	public SimpleSaplingBlock(SaplingGenerator generator, Predicate<BlockState> placementConditions, Settings settings) {
		super(generator, settings);
		this.placementConditions = placementConditions;
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isSideSolidFullSquare(world, pos, Direction.UP) && this.placementConditions.test(floor);
	}
}
