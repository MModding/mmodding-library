package com.mmodding.library.java.api.function;

import java.util.function.Consumer;

public interface AutoMapper<T> extends Mapper<T, T> {

	static <T> AutoMapper<T> identity() {
		return object -> object;
	}

	static <T> AutoMapper<T> ofConsumer(Consumer<T> consumer) {
		return object -> {
			consumer.accept(object);
			return object;
		};
	}
}
