package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.library.fluids.FluidGroup;
import com.mmodding.mmodding_lib.library.utils.ClassExtension;
import net.minecraft.fluid.FluidState;

@ClassExtension(FluidState.class)
public interface FluidGroupComparable {

	default boolean isOf(FluidGroup group) {
		throw new AssertionError();
	}
}
