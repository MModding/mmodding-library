package com.mmodding.library.state.api.property;

import java.util.Collection;
import java.util.Set;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class ConstantProperty<T extends Comparable<T>> extends Property<T> {

	private final T constant;
	private final Set<T> singleton;

	protected ConstantProperty(String qualifier, T constant, Class<T> type) {
		super(qualifier, type);
		this.constant = constant;
		this.singleton = Set.of(constant);
	}

	@Override
	public Collection<T> getPossibleValues() {
		return this.singleton;
	}

	@Override
	public String getName(T comparable) {
		return comparable.toString();
	}

	public T getConstant() {
		return this.constant;
	}
}
