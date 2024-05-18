package com.mmodding.library.java.api.container;

import com.mmodding.library.java.impl.container.MutableUnitImpl;
import com.mmodding.library.java.impl.container.UnitImpl;

public interface Unit<E> {

	static <E> Unit<E> create(E value) {
		return new UnitImpl<>(value);
	}

	static <E> Unit.Mutable<E> mutable(E value) {
		return new MutableUnitImpl<>(value);
	}

	E value();

	interface Mutable<E> extends Unit<E> {

		void mutateValue(E value);
	}
}
