package com.mmodding.mmodding_lib.library.math;

@FunctionalInterface
public interface MathFunction {

    double getY(double x);

    static LinearFunction linear(float a) {
        return new LinearFunction(a, 0);
    }

    static LinearFunction linear(float a, float b) {
        return new LinearFunction(a, b);
    }
}
