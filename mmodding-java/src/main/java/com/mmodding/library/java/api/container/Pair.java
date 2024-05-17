package com.mmodding.library.java.api.container;

import com.mmodding.library.java.impl.container.MutablePairImpl;
import com.mmodding.library.java.impl.container.PairImpl;

public interface Pair<E0, E1> {

	static <E0, E1> Pair<E0, E1> create(E0 first, E1 second) {
		return new PairImpl<>(first, second);
	}

	static <E0, E1> Pair.Mutable<E0, E1> mutable(E0 first, E1 second) {
		return new MutablePairImpl<>(first, second);
	}

	E0 first();

	E1 second();

	interface Mutable<E0, E1> extends Pair<E0, E1> {

		void mutateFirst(E0 value);

		void mutateSecond(E1 value);
	}
}
