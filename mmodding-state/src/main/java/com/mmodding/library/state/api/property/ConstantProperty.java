package com.mmodding.library.state.api.property;

import net.minecraft.state.property.Property;

import java.util.Collection;
import java.util.Set;

public abstract class ConstantProperty<T extends Comparable<T>> extends Property<T> {

	private final T constant;
	private final Set<T> singleton;

	protected ConstantProperty(String qualifier, T constant, Class<T> type) {
		super(qualifier, type);
		this.constant = constant;
		this.singleton = Set.of(constant);
	}

	@Override
	public Collection<T> getValues() {
		return this.singleton;
	}

	@Override
	public String name(T comparable) {
		return comparable.toString();
	}

	public T getConstant() {
		return this.constant;
	}
}
