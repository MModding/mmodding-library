package com.mmodding.mmodding_lib.ducks;

import net.minecraft.fluid.FluidState;

import java.util.function.IntFunction;

public interface FlowableFluidDuckInterface {

	void mmodding_lib$putCustomStates(FluidState stillState, IntFunction<FluidState> flowingStates);

	void mmodding_lib$removeCustomStates();

	boolean mmodding_lib$hasStillState();

	FluidState mmodding_lib$getStillState();

	boolean mmodding_lib$hasFlowingStates();

	IntFunction<FluidState> mmodding_lib$getFlowingStates();
}
