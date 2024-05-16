package com.mmodding.library.java.api.filter;

public interface FilterList<E> {

	static <E> FilterList<E> always() {
		return element -> true;
	}

	static <E> FilterList<E> never() {
		return element -> false;
	}

	boolean check(E element);
}
