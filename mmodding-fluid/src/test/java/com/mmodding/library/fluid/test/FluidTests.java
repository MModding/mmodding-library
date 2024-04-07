package com.mmodding.library.fluid.test;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.content.DefaultContentHolder;
import com.mmodding.library.fluid.api.FluidBehavior;

public class FluidTests implements DefaultContentHolder {

	public static final FluidBehavior COOL_FLUID_BEHAVIOR = new CoolFluidBehavior();

	public FluidTests() {
	}

	@Override
	public void register(AdvancedContainer mod) {
	}
}
