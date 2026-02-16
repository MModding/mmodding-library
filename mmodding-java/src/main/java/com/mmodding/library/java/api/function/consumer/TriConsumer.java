package com.mmodding.library.java.api.function.consumer;

@FunctionalInterface
public interface TriConsumer<K, V, S> {

	void accept(K k, V v, S s);
}
