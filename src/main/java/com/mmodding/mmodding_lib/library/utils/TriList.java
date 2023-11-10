package com.mmodding.mmodding_lib.library.utils;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.List;
import java.util.function.Consumer;

public interface TriList<E1, E2, E3> extends List<Triple<E1, E2, E3>> {

	static <E> boolean contains(TriList<E, E, E> triList, E first, E second, E third) {
		return (triList.contains(first, second, third) || triList.contains(first, third, second))
			|| (triList.contains(second, first, third) || triList.contains(second, third, first))
			|| (triList.contains(third, first, second) || triList.contains(third, second, first));
	}

	static <E1, E2, E3> Triple<E1, E2, E3> emptyValue() {
		return new ImmutableTriple<>(null, null, null);
	}

	boolean contains(E1 first, E2 second, E3 third);

	E1 getFirst(int index);

	E2 getSecond(int index);

	E3 getThird(int index);

	boolean add(E1 first, E2 second, E3 third);

	boolean remove(E1 first, E2 second, E3 third);

	Triple<E1, E2, E3> set(int index, E1 first, E2 second, E3 third);

	void forEachFirst(Consumer<? super E1> action);

	void forEachSecond(Consumer<? super E2> action);

	void forEachThird(Consumer<? super E3> action);

	void forEach(TriConsumer<? super E1, ? super E2, ? super E3> action);
}
