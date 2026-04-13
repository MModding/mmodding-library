package com.mmodding.library.state.api.property;

import java.util.List;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class ConstantProperty<T extends Comparable<T>> extends Property<T> {

	private final T constant;
	private final List<T> singleton;

	protected ConstantProperty(String qualifier, T constant, Class<T> type) {
		super(qualifier, type);
		this.constant = constant;
		this.singleton = List.of(constant);
	}

	@Override
	public List<T> getPossibleValues() {
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
