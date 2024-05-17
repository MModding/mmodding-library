package com.mmodding.library.java.impl.container;

import com.mmodding.library.java.api.container.Pair;

import java.util.Objects;

public class MutablePairImpl<E0, E1> implements Pair.Mutable<E0, E1> {

	private E0 first;
	private E1 second;

	public MutablePairImpl(E0 first, E1 second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public E0 first() {
		return this.first;
	}

	@Override
	public E1 second() {
		return this.second;
	}

	@Override
	public void mutateFirst(E0 value) {
		this.first = value;
	}

	@Override
	public void mutateSecond(E1 value) {
		this.second = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pair<?,?> pair) {
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
