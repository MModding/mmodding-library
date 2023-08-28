package com.mmodding.mmodding_lib.library.utils;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface BiList<E1, E2> extends List<Pair<E1, E2>> {

	static <E1, E2> Pair<E1, E2> emptyValue() {
		return new ImmutablePair<>(null, null);
	}

	E1 getFirst(int index);

	E2 getSecond(int index);

	boolean add(E1 first, E2 second);

	boolean remove(E1 first, E2 second);

	Pair<E1, E2> set(int index, E1 first, E2 second);

	void forEachFirst(Consumer<? super E1> action);

	void forEachSecond(Consumer<? super E2> action);

	void forEach(BiConsumer<? super E1, ? super E2> action);
}
