package com.mmodding.library.java.impl.container;

import com.mmodding.library.java.api.container.Pair;

import java.util.Objects;

public class MutablePairImpl<E1, E2> implements Pair.Mutable<E1, E2> {

	private E1 first;
	private E2 second;

	public MutablePairImpl(E1 first, E2 second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public E1 first() {
		return this.first;
	}

	@Override
	public E2 second() {
		return this.second;
	}

	@Override
	public void mutateFirst(E1 value) {
		this.first = value;
	}

	@Override
	public void mutateSecond(E2 value) {
		this.second = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pair<?, ?> pair) {
			return Objects.equals(this.first, pair.first()) && Objects.equals(this.second, pair.second());
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.first, this.second);
	}

	@Override
	public String toString() {
		return "MutablePairImpl[first=" + this.first.toString() + ", second=" + this.second.toString() + "]";
	}
}
