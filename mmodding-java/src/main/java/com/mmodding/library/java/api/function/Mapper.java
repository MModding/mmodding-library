package com.mmodding.library.java.api.function;

@FunctionalInterface
public interface Mapper<I, O> {

	O map(I object);
}
