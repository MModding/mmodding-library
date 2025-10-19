package com.mmodding.library.block.api.catalog;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import java.util.function.Predicate;

public class SimpleFlowerBlock extends FlowerBlock {

	private final Predicate<BlockState> placementConditions;

	public SimpleFlowerBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
		this(floor -> floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND), suspiciousStewEffect, effectDuration, settings);
	}

	public SimpleFlowerBlock(Predicate<BlockState> placementConditions, StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
		super(suspiciousStewEffect, effectDuration, settings);
		this.placementConditions = placementConditions;
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isSideSolidFullSquare(world, pos, Direction.UP) && this.placementConditions.test(floor);
	}
}
