package com.mmodding.library.java.impl.container;

import com.mmodding.library.java.api.container.Triple;

import java.util.Objects;

public class MutableTripleImpl<E1, E2, E3> implements Triple.Mutable<E1, E2, E3> {

	private E1 first;
	private E2 second;
	private E3 third;

	public MutableTripleImpl(E1 first, E2 second, E3 third) {
		this.first = first;
		this.second = second;
		this.third = third;
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
	public E3 third() {
		return this.third;
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
	public void mutateThird(E3 value) {
		this.third = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Triple<?, ?, ?> triple) {
			return Objects.equals(this.first, triple.first()) && Objects.equals(this.second, triple.second()) && Objects.equals(this.third, triple.third());
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.first, this.second, this.third);
	}

	@Override
	public String toString() {
		return "MutableTripleImpl[first=" + this.first.toString() + ", second=" + this.second.toString() + ", third=" + this.third.toString() + "]";
	}
}
