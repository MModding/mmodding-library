package com.mmodding.library.fluid.api;

import net.minecraft.block.BlockState;

public class FluidStateInfo {

	private final FluidBehavior behavior;
	private final int flowStage;

	public static FluidStateInfo of(BlockState state, FluidBehavior behavior) {
		return new FluidStateInfo(behavior, state.contains(behavior.getProperty()) ? state.get(behavior.getProperty()) : -1);
	}

	private FluidStateInfo(FluidBehavior behavior, int flowStage) {
		this.behavior = behavior;
		this.flowStage = flowStage;
	}

	public int getFlowStage() {
		return this.flowStage;
	}

	public boolean isSource() {
		return this.flowStage == this.behavior.getMaxFlowStage();
	}
}
