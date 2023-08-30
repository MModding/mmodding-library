package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.FlowableFluidDuckInterface;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.IntFunction;

@Mixin(FlowableFluid.class)
public class FlowableFluidMixin implements FlowableFluidDuckInterface {

	@Unique
	private FluidState stillState = null;

	@Unique
	private IntFunction<FluidState> flowingStates = null;

	@Override
	public void mmodding_lib$putCustomStates(FluidState stillState, IntFunction<FluidState> flowingStates) {
		this.stillState = stillState;
		this.flowingStates = flowingStates;
	}

	@Override
	public void mmodding_lib$removeCustomStates() {
		this.stillState = null;
		this.flowingStates = null;
	}

	@Override
	public boolean mmodding_lib$hasStillState() {
		return this.stillState != null;
	}

	@Override
	public FluidState mmodding_lib$getStillState() {
		return this.stillState;
	}

	@Override
	public boolean mmodding_lib$hasFlowingStates() {
		return this.flowingStates != null;
	}

	@Override
	public IntFunction<FluidState> mmodding_lib$getFlowingStates() {
		return this.flowingStates;
	}
}
