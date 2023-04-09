package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class CustomFlowerBlock extends FlowerBlock implements BlockRegistrable {

	private final Predicate<BlockState> placementConditions;

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomFlowerBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
		this(floor -> floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND), suspiciousStewEffect, effectDuration, settings);
	}

	public CustomFlowerBlock(Predicate<BlockState> placementConditions, StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
		super(suspiciousStewEffect, effectDuration, settings);
		this.placementConditions = placementConditions;
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return this.placementConditions.test(floor);
	}

	@Override
	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		this.registered.set(true);
	}
}
