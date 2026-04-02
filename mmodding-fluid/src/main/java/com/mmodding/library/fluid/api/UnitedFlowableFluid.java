package com.mmodding.library.fluid.api;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;

/**
 * Handles the still/flowing differences for you.
 */
public abstract class UnitedFlowableFluid extends FlowableFluid {

	private final IntProperty levels;
	private final int maxLevel;
	private final boolean still;

	public UnitedFlowableFluid(IntProperty levels, boolean still) {
		this.levels = levels;
		this.maxLevel = levels.getValues().stream().max(Integer::compare).orElseThrow();
		this.still = still;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
		super.appendProperties(builder);
		if (this.still) {
			builder.add(this.levels);
		}
	}

	@Override
	public int getLevel(FluidState state) {
		return this.still ? this.maxLevel : state.get(this.levels);
	}

	@Override
	public boolean isStill(FluidState state) {
		return this.still;
	}
}
