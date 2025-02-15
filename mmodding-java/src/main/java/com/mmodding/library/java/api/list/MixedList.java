package com.mmodding.library.java.api.list;

import com.mmodding.library.java.api.container.Typed;
import com.mmodding.library.java.api.object.Copyable;
import com.mmodding.library.java.impl.list.MixedListImpl;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public interface MixedList extends List<Typed<?>>, Copyable<MixedList> {

	static MixedList create() {
		return new MixedListImpl();
	}

	static <E> Typed<E> emptyValue(Class<E> type) {
		return Typed.of(type, null);
	}

	<E> boolean contains(Class<E> type, E e);

	<E> E get(int index, Class<E> type);

	<E> boolean add(Class<E> type, E e);

	<E> boolean remove(Class<E> type, E e);

	<E> E set(int index, Class<E> type, E element);

	<E> void forEach(BiConsumer<? super Class<E>, ? super E> action);

	@SuppressWarnings("unchecked")
	static MixedList generateMixedListFromTrustedArray(Object... input) {
		assert input.getClass() == Object[].class;

		for (Object o : input) {
			Objects.requireNonNull(o);
		}

		MixedList mixedList = MixedList.create();

		for (int i = 0; i < input.length / 2; i++) {
			assert input[i * 2] instanceof Class && input[i * 2 + 1].getClass() == input[i * 2];
			mixedList.add((Class<Object>) input[i * 2], input[i * 2 + 1]);
		}

		return mixedList;
	}

	static <E0> MixedList of(Class<E0> t0, E0 e0) {
		return MixedList.generateMixedListFromTrustedArray(t0, e0);
	}

	static <E0, E1> MixedList of(Class<E0> t0, E0 e0, Class<E1> t1, E1 e1) {
		return MixedList.generateMixedListFromTrustedArray(t0, e0, t1, e1);
	}

	static <E0, E1, E2> MixedList of(Class<E0> t0, E0 e0, Class<E1> t1, E1 e1, Class<E2> t2, E2 e2) {
		return MixedList.generateMixedListFromTrustedArray(t0, e0, t1, e1, t2, e2);
	}

	static <E0, E1, E2, E3> MixedList of(Class<E0> t0, E0 e0, Class<E1> t1, E1 e1, Class<E2> t2, E2 e2, Class<E3> t3, E3 e3) {
		return MixedList.generateMixedListFromTrustedArray(t0, e0, t1, e1, t2, e2, t3, e3);
	}

	static <E0, E1, E2, E3, E4> MixedList of(Class<E0> t0, E0 e0, Class<E1> t1, E1 e1, Class<E2> t2, E2 e2, Class<E3> t3, E3 e3, Class<E4> t4, E4 e4) {
		return MixedList.generateMixedListFromTrustedArray(t0, e0, t1, e1, t2, e2, t3, e3, t4, e4);
	}

	static <E0, E1, E2, E3, E4, E5> MixedList of(Class<E0> t0, E0 e0, Class<E1> t1, E1 e1, Class<E2> t2, E2 e2, Class<E3> t3, E3 e3, Class<E4> t4, E4 e4, Class<E5> t5, E5 e5) {
		return MixedList.generateMixedListFromTrustedArray(t0, e0, t1, e1, t2, e2, t3, e3, t4, e4, t5, e5);
	}

	static <E0, E1, E2, E3, E4, E5, E6> MixedList of(Class<E0> t0, E0 e0, Class<E1> t1, E1 e1, Class<E2> t2, E2 e2, Class<E3> t3, E3 e3, Class<E4> t4, E4 e4, Class<E5> t5, E5 e5, Class<E6> t6, E6 e6) {
		return MixedList.generateMixedListFromTrustedArray(t0, e0, t1, e1, t2, e2, t3, e3, t4, e4, t5, e5, t6, e6);
	}

	static <E0, E1, E2, E3, E4, E5, E6, E7> MixedList of(Class<E0> t0, E0 e0, Class<E1> t1, E1 e1, Class<E2> t2, E2 e2, Class<E3> t3, E3 e3, Class<E4> t4, E4 e4, Class<E5> t5, E5 e5, Class<E6> t6, E6 e6, Class<E7> t7, E7 e7) {
		return MixedList.generateMixedListFromTrustedArray(t0, e0, t1, e1, t2, e2, t3, e3, t4, e4, t5, e5, t6, e6, t7, e7);
	}

	static <E0, E1, E2, E3, E4, E5, E6, E7, E8> MixedList of(Class<E0> t0, E0 e0, Class<E1> t1, E1 e1, Class<E2> t2, E2 e2, Class<E3> t3, E3 e3, Class<E4> t4, E4 e4, Class<E5> t5, E5 e5, Class<E6> t6, E6 e6, Class<E7> t7, E7 e7, Class<E8> t8, E8 e8) {
		return MixedList.generateMixedListFromTrustedArray(t0, e0, t1, e1, t2, e2, t3, e3, t4, e4, t5, e5, t6, e6, t7, e7, t8, e8);
	}

	static <E0, E1, E2, E3, E4, E5, E6, E7, E8, E9> MixedList of(Class<E0> t0, E0 e0, Class<E1> t1, E1 e1, Class<E2> t2, E2 e2, Class<E3> t3, E3 e3, Class<E4> t4, E4 e4, Class<E5> t5, E5 e5, Class<E6> t6, E6 e6, Class<E7> t7, E7 e7, Class<E8> t8, E8 e8, Class<E9> t9, E9 e9) {
		return MixedList.generateMixedListFromTrustedArray(t0, e0, t1, e1, t2, e2, t3, e3, t4, e4, t5, e5, t6, e6, t7, e7, t8, e8, t9, e9);
	}
}
