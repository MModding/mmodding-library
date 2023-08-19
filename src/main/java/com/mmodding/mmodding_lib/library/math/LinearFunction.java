package com.mmodding.mmodding_lib.library.math;

public class LinearFunction implements MathFunction {

    private final double a;
    private final double b;

    LinearFunction(float a, float b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double getY(double x) {
        return x * this.a + this.b;
    }
}
