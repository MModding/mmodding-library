package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.library.utils.ClassExtension;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;

@ClassExtension(State.class)
public interface ReverseCycleState<S> {

	default <T extends Comparable<T>> S reverseCycle(Property<T> property) {
		throw new IllegalArgumentException();
	}
}
