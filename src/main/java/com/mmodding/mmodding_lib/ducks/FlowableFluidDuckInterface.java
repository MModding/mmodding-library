package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.blocks.CustomFluidBlock;
import com.mmodding.mmodding_lib.library.utils.InternalOf;
import net.minecraft.fluid.FluidState;

import java.util.function.IntFunction;

@InternalOf(targets = CustomFluidBlock.class)
public interface FlowableFluidDuckInterface {

	void mmodding_lib$putCustomStates(FluidState stillState, IntFunction<FluidState> flowingStates);

	void mmodding_lib$removeCustomStates();

	boolean mmodding_lib$hasStillState();

	FluidState mmodding_lib$getStillState();

	boolean mmodding_lib$hasFlowingStates();

	IntFunction<FluidState> mmodding_lib$getFlowingStates();
}
