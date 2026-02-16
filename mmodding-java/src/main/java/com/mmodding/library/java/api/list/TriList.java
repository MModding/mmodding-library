package com.mmodding.library.java.api.list;

import com.mmodding.library.java.api.container.Triple;
import com.mmodding.library.java.api.function.consumer.TriConsumer;
import com.mmodding.library.java.impl.list.TriListImpl;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public interface TriList<E1, E2, E3> extends List<Triple<E1, E2, E3>> {

	static <E1, E2, E3> TriList<E1, E2, E3> create() {
		return new TriListImpl<>();
	}

	static <E> boolean contains(TriList<E, E, E> triList, E first, E second, E third) {
		return (triList.contains(first, second, third) || triList.contains(first, third, second))
			|| (triList.contains(second, first, third) || triList.contains(second, third, first))
			|| (triList.contains(third, first, second) || triList.contains(third, second, first));
	}

	static <E1, E2, E3> Triple<E1, E2, E3> emptyValue() {
		return Triple.create(null, null, null);
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

	@SuppressWarnings("unchecked")
	static <E1, E2, E3> TriList<E1, E2, E3> generateTriListFromTrustedArray(Object... input) {
		if (input.length % 3 != 0) {
			throw new IllegalArgumentException("Input Object must be a multiplier of 3");
		}

		assert input.getClass() == Object[].class;

		for (Object o : input) {
			Objects.requireNonNull(o);
		}

		TriList<E1, E2, E3> biList = TriList.create();

		for (int i = 0; i < input.length / 3; i++) {
			biList.add((E1) input[i * 3], (E2) input[i * 3 + 1], (E3) input[i * 3 + 2]);
		}

		return biList;
	}

	@SafeVarargs
	static <E> TriList<E, E, E> of(E... e) {
		return TriList.generateTriListFromTrustedArray((Object) e);
	}

	static <E1, E2, E3> TriList<E1, E2, E3> of(E1 e01, E2 e02, E3 e03) {
		return TriList.generateTriListFromTrustedArray(e01, e02, e03);
	}

	static <E1, E2, E3> TriList<E1, E2, E3> of(E1 e01, E2 e02, E3 e03, E1 e11, E2 e12, E3 e13) {
		return TriList.generateTriListFromTrustedArray(e01, e02, e03, e11, e12, e13);
	}

	static <E1, E2, E3> TriList<E1, E2, E3> of(E1 e01, E2 e02, E3 e03, E1 e11, E2 e12, E3 e13, E1 e21, E2 e22, E3 e23) {
		return TriList.generateTriListFromTrustedArray(e01, e02, e03, e11, e12, e13, e21, e22, e23);
	}

	static <E1, E2, E3> TriList<E1, E2, E3> of(E1 e01, E2 e02, E3 e03, E1 e11, E2 e12, E3 e13, E1 e21, E2 e22, E3 e23, E1 e31, E2 e32, E3 e33) {
		return TriList.generateTriListFromTrustedArray(e01, e02, e03, e11, e12, e13, e21, e22, e23, e31, e32, e33);
	}

	static <E1, E2, E3> TriList<E1, E2, E3> of(E1 e01, E2 e02, E3 e03, E1 e11, E2 e12, E3 e13, E1 e21, E2 e22, E3 e23, E1 e31, E2 e32, E3 e33, E1 e41, E2 e42, E3 e43) {
		return TriList.generateTriListFromTrustedArray(e01, e02, e03, e11, e12, e13, e21, e22, e23, e31, e32, e33, e41, e42, e43);
	}

	static <E1, E2, E3> TriList<E1, E2, E3> of(E1 e01, E2 e02, E3 e03, E1 e11, E2 e12, E3 e13, E1 e21, E2 e22, E3 e23, E1 e31, E2 e32, E3 e33, E1 e41, E2 e42, E3 e43, E1 e51, E2 e52, E3 e53) {
		return TriList.generateTriListFromTrustedArray(e01, e02, e03, e11, e12, e13, e21, e22, e23, e31, e32, e33, e41, e42, e43, e51, e52, e53);
	}

	static <E1, E2, E3> TriList<E1, E2, E3> of(E1 e01, E2 e02, E3 e03, E1 e11, E2 e12, E3 e13, E1 e21, E2 e22, E3 e23, E1 e31, E2 e32, E3 e33, E1 e41, E2 e42, E3 e43, E1 e51, E2 e52, E3 e53, E1 e61, E2 e62, E3 e63) {
		return TriList.generateTriListFromTrustedArray(e01, e02, e03, e11, e12, e13, e21, e22, e23, e31, e32, e33, e41, e42, e43, e51, e52, e53, e61, e62, e63);
	}

	static <E1, E2, E3> TriList<E1, E2, E3> of(E1 e01, E2 e02, E3 e03, E1 e11, E2 e12, E3 e13, E1 e21, E2 e22, E3 e23, E1 e31, E2 e32, E3 e33, E1 e41, E2 e42, E3 e43, E1 e51, E2 e52, E3 e53, E1 e61, E2 e62, E3 e63, E1 e71, E2 e72, E3 e73) {
		return TriList.generateTriListFromTrustedArray(e01, e02, e03, e11, e12, e13, e21, e22, e23, e31, e32, e33, e41, e42, e43, e51, e52, e53, e61, e62, e63, e71, e72, e73);
	}

	static <E1, E2, E3> TriList<E1, E2, E3> of(E1 e01, E2 e02, E3 e03, E1 e11, E2 e12, E3 e13, E1 e21, E2 e22, E3 e23, E1 e31, E2 e32, E3 e33, E1 e41, E2 e42, E3 e43, E1 e51, E2 e52, E3 e53, E1 e61, E2 e62, E3 e63, E1 e71, E2 e72, E3 e73, E1 e81, E2 e82, E3 e83) {
		return TriList.generateTriListFromTrustedArray(e01, e02, e03, e11, e12, e13, e21, e22, e23, e31, e32, e33, e41, e42, e43, e51, e52, e53, e61, e62, e63, e71, e72, e73, e81, e82, e83);
	}

	static <E1, E2, E3> TriList<E1, E2, E3> of(E1 e01, E2 e02, E3 e03, E1 e11, E2 e12, E3 e13, E1 e21, E2 e22, E3 e23, E1 e31, E2 e32, E3 e33, E1 e41, E2 e42, E3 e43, E1 e51, E2 e52, E3 e53, E1 e61, E2 e62, E3 e63, E1 e71, E2 e72, E3 e73, E1 e81, E2 e82, E3 e83, E1 e91, E2 e92, E3 e93) {
		return TriList.generateTriListFromTrustedArray(e01, e02, e03, e11, e12, e13, e21, e22, e23, e31, e32, e33, e41, e42, e43, e51, e52, e53, e61, e62, e63, e71, e72, e73, e81, e82, e83, e91, e92, e93);
	}
}
