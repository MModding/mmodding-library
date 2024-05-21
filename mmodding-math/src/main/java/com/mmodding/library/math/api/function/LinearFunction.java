package com.mmodding.library.math.api.function;

import com.mmodding.library.math.impl.function.LinearFunctionImpl;

public interface LinearFunction extends MathFunction {

	static LinearFunction linear(float a) {
		return LinearFunction.linear(a, 0);
	}

	static LinearFunction linear(float a, float b) {
		return new LinearFunctionImpl(a, b);
	}

	double a();

	double b();
}
