package com.mmodding.library.fluid.test;

import com.mmodding.library.core.api.AdvancedContainer;
import net.minecraft.world.level.material.FlowingFluid;

public class FluidTests {

	public static final FlowingFluid COOL_FLUID = new CoolFluid(CoolFluid.STAGES, true);
	public static final FlowingFluid FLOWING_COOL_FLUID = new CoolFluid(CoolFluid.STAGES, false);

	public FluidTests(AdvancedContainer mod) {}
}
