package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.*;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class CustomGrowsDownPlantHeadBlock extends AbstractPlantStemBlock implements BlockRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private final int growLength;
	private final Block bodyBlock;
	private final Predicate<BlockState> chooseStemState;

	public CustomGrowsDownPlantHeadBlock(AbstractBlock.Settings settings, boolean tickWater, float growthChance, int growLength, Block bodyBlock, Predicate<BlockState> chooseStemState) {
		super(settings, Direction.DOWN, Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), tickWater, growthChance);
		this.growLength = growLength;
		this.bodyBlock = bodyBlock;
		this.chooseStemState = chooseStemState;
	}

	@Override
	protected int getGrowthLength(RandomGenerator random) {
		return this.growLength;
	}

	@Override
	protected boolean chooseStemState(BlockState state) {
		return this.chooseStemState.test(state);
	}

	@Override
	protected Block getPlant() {
		return this.bodyBlock;
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
