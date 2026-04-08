package com.mmodding.library.fluid.test;

import com.mmodding.library.core.api.AdvancedContainer;
import net.minecraft.fluid.FlowableFluid;

public class FluidTests {

	public static final FlowableFluid COOL_FLUID = new CoolFluid(CoolFluid.STAGES, true);
	public static final FlowableFluid FLOWING_COOL_FLUID = new CoolFluid(CoolFluid.STAGES, false);

	public FluidTests(AdvancedContainer mod) {}
}
