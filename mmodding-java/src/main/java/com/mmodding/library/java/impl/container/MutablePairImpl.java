package com.mmodding.library.java.impl.container;

import com.mmodding.library.java.api.container.Pair;

import java.util.Objects;

public class MutablePairImpl<E0, E1> implements Pair.Mutable<E0, E1> {

	private E0 first;
	private E1 last;

	public MutablePairImpl(E0 first, E1 last) {
		this.first = first;
		this.last = last;
	}

	@Override
	public E0 first() {
		return this.first;
	}

	@Override
	public E1 last() {
		return this.last;
	}

	@Override
	public void mutateFirst(E0 value) {
		this.first = value;
	}

	@Override
	public void mutateLast(E1 value) {
		this.last = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pair<?,?> pair) {
			return Objects.equals(this.first, pair.first()) && Objects.equals(this.last, pair.last());
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.first, this.last);
	}

	@Override
	public String toString() {
		return "MutablePairImpl[first=" + this.first.toString() + ", last=" + this.last.toString() + "]";
	}
}
