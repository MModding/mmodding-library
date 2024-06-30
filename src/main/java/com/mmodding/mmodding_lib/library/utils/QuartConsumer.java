package com.mmodding.mmodding_lib.library.utils;

public interface QuartConsumer<K, V, S, T> {

	void accept(K k, V v, S s, T t);
}
