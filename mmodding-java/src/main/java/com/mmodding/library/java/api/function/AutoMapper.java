package com.mmodding.library.java.api.function;

public interface AutoMapper<T> extends Mapper<T, T> {

	static <T> AutoMapper<T> identity() {
		return object -> object;
	}
}
