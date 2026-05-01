package com.mmodding.library.fluid.api;

import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

/**
 * Handles the still/flowing differences for you.
 */
public abstract class UnitedFlowableFluid extends FlowingFluid {

	private final IntegerProperty levels;
	private final int maxLevel;
	private final boolean source;

	public UnitedFlowableFluid(IntegerProperty levels, boolean source) {
		this.levels = levels;
		this.maxLevel = levels.getPossibleValues().stream().max(Integer::compare).orElseThrow();
		this.source = source;
		super(); // Fields need to receive their values before super-call.
	}

	@Override
	protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
		super.createFluidStateDefinition(builder);
		if (!this.source) {
			builder.add(this.levels);
		}
	}

	@Override
	public int getAmount(FluidState state) {
		return this.source ? this.maxLevel : state.getValue(this.levels);
	}

	@Override
	public boolean isSource(FluidState state) {
		return this.source;
	}
}
