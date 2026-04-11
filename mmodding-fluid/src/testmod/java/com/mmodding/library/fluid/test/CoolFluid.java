package com.mmodding.library.fluid.test;

import com.mmodding.library.fluid.api.UnitedFlowableFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

public class CoolFluid extends UnitedFlowableFluid {

	public static final IntegerProperty STAGES = IntegerProperty.create("stage", 0, 16);

	public CoolFluid(IntegerProperty levels, boolean still) {
		super(levels, still);
	}

	@Override
	public Fluid getFlowing() {
		return FluidTests.FLOWING_COOL_FLUID;
	}

	@Override
	public Fluid getSource() {
		return FluidTests.COOL_FLUID;
	}

	@Override
	protected boolean canConvertToSource(Level level) {
		return false;
	}

	@Override
	protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {

	}

	@Override
	protected int getSlopeFindDistance(LevelReader levelReader) {
		return 0;
	}

	@Override
	protected int getDropOff(LevelReader levelReader) {
		return 0;
	}

	@Override
	public Item getBucket() {
		return null;
	}


	@Override
	protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
		return false;
	}

	@Override
	public int getTickDelay(LevelReader levelReader) {
		return 0;
	}

	@Override
	protected float getExplosionResistance() {
		return 0;
	}

	@Override
	@NotNull
	protected BlockState createLegacyBlock(FluidState fluidState) {
		return null;
	}
}
