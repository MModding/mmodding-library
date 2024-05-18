package com.mmodding.library.java.api.container;

import com.mmodding.library.java.impl.container.MutableTripleImpl;
import com.mmodding.library.java.impl.container.TripleImpl;

public interface Triple<E1, E2, E3> {

	static <E1, E2, E3> Triple<E1, E2, E3> create(E1 first, E2 second, E3 third) {
		return new TripleImpl<>(first, second, third);
	}

	static <E1, E2, E3> Triple.Mutable<E1, E2, E3> mutable(E1 first, E2 second, E3 third) {
		return new MutableTripleImpl<>(first, second, third);
	}

	E1 first();

	E2 second();

	E3 third();

	interface Mutable<E1, E2, E3> extends Triple<E1, E2, E3> {

		void mutateFirst(E1 value);

		void mutateSecond(E2 value);

		void mutateThird(E3 value);
	}
}
