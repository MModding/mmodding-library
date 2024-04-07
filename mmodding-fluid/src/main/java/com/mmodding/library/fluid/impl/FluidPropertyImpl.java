package com.mmodding.library.fluid.impl;

import com.mmodding.library.fluid.api.property.FluidProperty;
import net.minecraft.util.Identifier;

public class FluidPropertyImpl<T> implements FluidProperty<T> {

	private final Identifier identifier;
	private final T fallback;

	public FluidPropertyImpl(Identifier identifier, T fallback) {
		this.identifier = identifier;
		this.fallback = fallback;
	}

	@Override
	public Identifier getIdentifier() {
		return this.identifier;
	}

	@Override
	public T getFallback() {
		return this.fallback;
	}
}
