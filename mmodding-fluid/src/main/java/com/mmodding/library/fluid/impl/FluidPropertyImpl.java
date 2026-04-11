package com.mmodding.library.fluid.impl;

import com.mmodding.library.fluid.api.property.FluidProperty;
import net.minecraft.resources.ResourceLocation;

public class FluidPropertyImpl<T> implements FluidProperty<T> {

	private final ResourceLocation identifier;
	private final T fallback;

	public FluidPropertyImpl(ResourceLocation identifier, T fallback) {
		this.identifier = identifier;
		this.fallback = fallback;
	}

	@Override
	public ResourceLocation getIdentifier() {
		return this.identifier;
	}

	@Override
	public T getFallback() {
		return this.fallback;
	}
}
