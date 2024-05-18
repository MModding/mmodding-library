package com.mmodding.library.java.api.function;

@FunctionalInterface
public interface SingleTypeFunction<T> {

	T apply(T t);
}
