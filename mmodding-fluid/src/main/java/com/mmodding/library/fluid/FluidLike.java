package com.mmodding.library.fluid;

public interface FluidLike {

	Type getType();

	enum Type {
		VANILLA,
		MODDED
	}
}
