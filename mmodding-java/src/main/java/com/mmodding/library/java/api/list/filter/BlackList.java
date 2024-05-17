package com.mmodding.library.java.api.list.filter;

import java.util.List;

public class BlackList<E> implements FilterList<E> {

	private final List<E> elements;

	@SafeVarargs
	public BlackList(E... elements) {
		this.elements = List.of(elements);
	}

	@Override
	public boolean check(E element) {
		return !this.elements.contains(element);
	}
}
