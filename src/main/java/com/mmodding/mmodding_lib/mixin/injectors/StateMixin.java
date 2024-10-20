package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.interface_injections.ReverseCycleState;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(State.class)
@SuppressWarnings("unused")
public abstract class StateMixin<O, S> implements ReverseCycleState<S> {

	@Shadow
	public abstract <T extends Comparable<T>, V extends T> S with(Property<T> property, V value);

	@Shadow
	protected static <T> T getNext(Collection<T> values, T value) {
		throw new IllegalStateException();
	}

	@Shadow
	public abstract <T extends Comparable<T>> T get(Property<T> property);

	@Override
	public <T extends Comparable<T>> S reverseCycle(Property<T> property) {
		List<T> reversedValues = new ArrayList<>();
		property.getValues().forEach(element -> reversedValues.add(0, element));
		return this.with(property, StateMixin.getNext(reversedValues, this.get(property)));
	}
}
