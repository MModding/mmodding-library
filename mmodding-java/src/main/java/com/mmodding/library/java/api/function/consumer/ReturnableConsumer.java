package com.mmodding.library.java.api.function.consumer;

import java.util.function.Consumer;

@FunctionalInterface
public interface ReturnableConsumer<T> extends Consumer<T> {

	static <T> ReturnableConsumer<T> of(Consumer<T> consumer) {
		return consumer::accept;
	}

	default T acceptReturnable(T t) {
		this.accept(t);
		return t;
	}
}
