package com.mmodding.library.fluid.test;

import com.mmodding.library.fluid.api.FluidBehavior;
import com.mmodding.library.fluid.api.FluidStateInfo;
import com.mmodding.library.fluid.api.property.CommonFluidProperties;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class CoolFluidBehavior extends FluidBehavior {

	public static final IntProperty STAGES = IntProperty.of("stage", 0, 16);

	public CoolFluidBehavior() {
		super(CoolFluidBehavior.STAGES, properties -> {
			properties.withFluidProperty(CommonFluidProperties.HYDRATABLE, true);
			properties.withFluidProperty(CommonFluidProperties.DENSITY, 500);
		});
	}

	@Override
	public Direction getFlowDirection(FluidStateInfo state, World world, BlockPos pos, Random random) {
		return null;
	}

	@Override
	public int getFlowSpeedFactor(FluidStateInfo state, World world, BlockPos pos, Random random) {
		return 0;
	}

	@Override
	public int getMovementFactor(FluidStateInfo state, World world, BlockPos pos, Random random) {
		return 0;
	}

	@Override
	public int getStrengthFactor(FluidStateInfo state, World world, BlockPos pos, Random random) {
		return 0;
	}

	@Override
	public int getPressureFactor(FluidStateInfo state, World world, BlockPos pos, Random random) {
		return 0;
	}

	@Override
	public boolean canBoatsBePlacedUpon(FluidStateInfo state, World world, BlockPos pos, Random random) {
		return false;
	}

	@Override
	public int randomDisplayTick(FluidStateInfo state, World world, BlockPos pos, Random random) {
		return 0;
	}
}
