package com.mmodding.library.java.api.container;

import com.mmodding.library.java.impl.container.MutablePairImpl;
import com.mmodding.library.java.impl.container.PairImpl;

public interface Pair<E0, E1> {

	static <E0, E1> Pair<E0, E1> create(E0 first, E1 last) {
		return new PairImpl<>(first, last);
	}

	static <E0, E1> Pair.Mutable<E0, E1> mutable(E0 first, E1 last) {
		return new MutablePairImpl<>(first, last);
	}

	E0 first();

	E1 last();

	interface Mutable<E0, E1> extends Pair<E0, E1> {

		void mutateFirst(E0 value);

		void mutateLast(E1 value);
	}
}
