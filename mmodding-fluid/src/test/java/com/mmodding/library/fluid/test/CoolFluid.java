package com.mmodding.library.fluid.test;

import com.mmodding.library.fluid.api.UnitedFlowableFluid;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class CoolFluid extends UnitedFlowableFluid {

	public static final IntProperty STAGES = IntProperty.of("stage", 0, 16);

	public CoolFluid(IntProperty levels, boolean still) {
		super(levels, still);
	}

	@Override
	public Fluid getFlowing() {
		return FluidTests.FLOWING_COOL_FLUID;
	}

	@Override
	public Fluid getStill() {
		return FluidTests.COOL_FLUID;
	}

	@Override
	protected boolean isInfinite(World world) {
		return false;
	}

	@Override
	protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {

	}

	@Override
	protected int getFlowSpeed(WorldView world) {
		return 0;
	}

	@Override
	protected int getLevelDecreasePerBlock(WorldView world) {
		return 0;
	}

	@Override
	public Item getBucketItem() {
		return null;
	}

	@Override
	protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
		return false;
	}

	@Override
	public int getTickRate(WorldView world) {
		return 0;
	}

	@Override
	protected float getBlastResistance() {
		return 0;
	}

	@Override
	protected BlockState toBlockState(FluidState state) {
		return null;
	}
}
