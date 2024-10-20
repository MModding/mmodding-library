package com.mmodding.mmodding_lib.interface_injections;

import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@InjectedInterface(State.class)
public interface ReverseCycleState<S> {

	<T extends Comparable<T>> S reverseCycle(Property<T> property);
}
