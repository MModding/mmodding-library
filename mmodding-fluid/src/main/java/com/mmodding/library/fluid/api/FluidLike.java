package com.mmodding.library.fluid.api;

public interface FluidLike {

	default Type getType() {
		throw new IllegalStateException();
	}

	enum Type {
		VANILLA,
		MODDED
	}
}
