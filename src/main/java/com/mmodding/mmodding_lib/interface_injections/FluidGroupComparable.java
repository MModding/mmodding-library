package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.library.fluids.FluidGroup;
import net.minecraft.fluid.FluidState;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@InjectedInterface(FluidState.class)
public interface FluidGroupComparable {

	default boolean isOf(FluidGroup group) {
		throw new AssertionError();
	}
}
