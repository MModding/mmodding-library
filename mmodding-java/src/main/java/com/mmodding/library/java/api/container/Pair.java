package com.mmodding.library.java.api.container;

import com.mmodding.library.java.impl.container.MutablePairImpl;
import com.mmodding.library.java.impl.container.PairImpl;

public interface Pair<E1, E2> {

	static <E1, E2> Pair<E1, E2> create(E1 first, E2 second) {
		return new PairImpl<>(first, second);
	}

	static <E1, E2> Pair.Mutable<E1, E2> mutable(E1 first, E2 second) {
		return new MutablePairImpl<>(first, second);
	}

	E1 first();

	E2 second();

	interface Mutable<E1, E2> extends Pair<E1, E2> {

		void mutateFirst(E1 value);

		void mutateSecond(E2 value);
	}
}
