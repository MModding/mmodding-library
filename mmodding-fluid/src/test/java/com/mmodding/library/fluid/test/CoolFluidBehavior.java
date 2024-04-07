package com.mmodding.library.fluid.test;

import com.mmodding.library.fluid.api.FluidBehavior;
import com.mmodding.library.fluid.api.property.CommonFluidProperties;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;

public class CoolFluidBehavior extends FluidBehavior {

	public static final IntProperty STAGES = IntProperty.of("stage", 0, 16);

	public CoolFluidBehavior() {
		super(CoolFluidBehavior.STAGES, properties -> properties.withFluidProperty(CommonFluidProperties.DENSITY, 500));
	}

	@Override
	public Direction getFlowDirection(BlockState state, World world, BlockPos pos, RandomGenerator random) {
		return null;
	}

	@Override
	public int getFlowSpeedFactor(BlockState state, World world, BlockPos pos, RandomGenerator random) {
		return 0;
	}

	@Override
	public int getMovementFactor(BlockState state, World world, BlockPos pos, RandomGenerator random) {
		return 0;
	}

	@Override
	public int getStrengthFactor(BlockState state, World world, BlockPos pos, RandomGenerator random) {
		return 0;
	}

	@Override
	public int getPressureFactor(BlockState state, World world, BlockPos pos, RandomGenerator random) {
		return 0;
	}

	@Override
	public boolean canBoatsBePlacedUpon(BlockState state, World world, BlockPos pos, RandomGenerator random) {
		return false;
	}

	@Override
	public int randomDisplayTick(BlockState state, World world, BlockPos pos, RandomGenerator random) {
		return 0;
	}
}
