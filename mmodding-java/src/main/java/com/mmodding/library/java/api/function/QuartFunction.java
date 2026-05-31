package com.mmodding.library.java.api.function;

@FunctionalInterface
public interface QuartFunction<T1, T2, T3, T4, R> {

	R apply(T1 t1, T2 t2, T3 t3, T4 t4);
}
