package com.mmodding.library.math.impl.function;

import com.mmodding.library.math.api.function.LinearFunction;

public record LinearFunctionImpl(double a, double b) implements LinearFunction {

	@Override
	public double apply(double value) {
		return this.a * value + this.b;
	}
}
