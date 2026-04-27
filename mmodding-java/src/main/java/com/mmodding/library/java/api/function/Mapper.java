package com.mmodding.library.java.api.function;

@FunctionalInterface
public interface Mapper<I, O> {

	default <T> Mapper<I, T> chain(Mapper<O, T> chain) {
		return object -> chain.map(this.map(object));
	}

	O map(I object);
}
