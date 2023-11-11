package com.mmodding.mmodding_lib.library.utils;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface BiList<E1, E2> extends List<Pair<E1, E2>> {

	static <E> boolean contains(BiList<E, E> biList, E first, E second) {
		return biList.contains(first, second) || biList.contains(second, first);
	}

	static <E1, E2> Pair<E1, E2> emptyValue() {
		return new ImmutablePair<>(null, null);
	}

	boolean contains(E1 first, E2 second);

	E1 getFirst(int index);

	E2 getSecond(int index);

	boolean add(E1 first, E2 second);

	boolean remove(E1 first, E2 second);

	Pair<E1, E2> set(int index, E1 first, E2 second);

	void forEachFirst(Consumer<? super E1> action);

	void forEachSecond(Consumer<? super E2> action);

	void forEach(BiConsumer<? super E1, ? super E2> action);

	@SuppressWarnings("unchecked")
	static <E1, E2> BiList<E1, E2> generateBiListFromTrustedArray(Object... input) {
		if (input.length % 2 != 0) {
			throw new IllegalArgumentException("Input Object must be a multiplier of 2");
		}

		assert input.getClass() == Object[].class;

		for (Object o : input) {
			Objects.requireNonNull(o);
		}

		BiList<E1, E2> biList = new BiArrayList<>();

		for (int i = 0; i < input.length / 2; i++) {
			biList.add((E1) input[i * 2], (E2) input[i * 2 + 1]);
		}

		return biList;
	}

	@SafeVarargs
	static <E> BiList<E, E> of(E... e) {
		return BiList.generateBiListFromTrustedArray((Object) e);
	}

	static <E1, E2> BiList<E1, E2> of(E1 e01, E2 e02) {
		return BiList.generateBiListFromTrustedArray(e01, e02);
	}

	static <E1, E2> BiList<E1, E2> of(E1 e01, E2 e02, E1 e11, E2 e12) {
		return BiList.generateBiListFromTrustedArray(e01, e02, e11, e12);
	}

	static <E1, E2> BiList<E1, E2> of(E1 e01, E2 e02, E1 e11, E2 e12, E1 e21, E2 e22) {
		return BiList.generateBiListFromTrustedArray(e01, e02, e11, e12, e21, e22);
	}

	static <E1, E2> BiList<E1, E2> of(E1 e01, E2 e02, E1 e11, E2 e12, E1 e21, E2 e22, E1 e31, E2 e32) {
		return BiList.generateBiListFromTrustedArray(e01, e02, e11, e12, e21, e22, e31, e32);
	}

	static <E1, E2> BiList<E1, E2> of(E1 e01, E2 e02, E1 e11, E2 e12, E1 e21, E2 e22, E1 e31, E2 e32, E1 e41, E2 e42) {
		return BiList.generateBiListFromTrustedArray(e01, e02, e11, e12, e21, e22, e31, e32, e41, e42);
	}

	static <E1, E2> BiList<E1, E2> of(E1 e01, E2 e02, E1 e11, E2 e12, E1 e21, E2 e22, E1 e31, E2 e32, E1 e41, E2 e42, E1 e51, E2 e52) {
		return BiList.generateBiListFromTrustedArray(e01, e02, e11, e12, e21, e22, e31, e32, e41, e42, e51, e52);
	}

	static <E1, E2> BiList<E1, E2> of(E1 e01, E2 e02, E1 e11, E2 e12, E1 e21, E2 e22, E1 e31, E2 e32, E1 e41, E2 e42, E1 e51, E2 e52, E1 e61, E2 e62) {
		return BiList.generateBiListFromTrustedArray(e01, e02, e11, e12, e21, e22, e31, e32, e41, e42, e51, e52, e61, e62);
	}

	static <E1, E2> BiList<E1, E2> of(E1 e01, E2 e02, E1 e11, E2 e12, E1 e21, E2 e22, E1 e31, E2 e32, E1 e41, E2 e42, E1 e51, E2 e52, E1 e61, E2 e62, E1 e71, E2 e72) {
		return BiList.generateBiListFromTrustedArray(e01, e02, e11, e12, e21, e22, e31, e32, e41, e42, e51, e52, e61, e62, e71, e72);
	}

	static <E1, E2> BiList<E1, E2> of(E1 e01, E2 e02, E1 e11, E2 e12, E1 e21, E2 e22, E1 e31, E2 e32, E1 e41, E2 e42, E1 e51, E2 e52, E1 e61, E2 e62, E1 e71, E2 e72, E1 e81, E2 e82) {
		return BiList.generateBiListFromTrustedArray(e01, e02, e11, e12, e21, e22, e31, e32, e41, e42, e51, e52, e61, e62, e71, e72, e81, e82);
	}

	static <E1, E2> BiList<E1, E2> of(E1 e01, E2 e02, E1 e11, E2 e12, E1 e21, E2 e22, E1 e31, E2 e32, E1 e41, E2 e42, E1 e51, E2 e52, E1 e61, E2 e62, E1 e71, E2 e72, E1 e81, E2 e82, E1 e91, E2 e92) {
		return BiList.generateBiListFromTrustedArray(e01, e02, e11, e12, e21, e22, e31, e32, e41, e42, e51, e52, e61, e62, e71, e72, e81, e82, e91, e92);
	}
}
