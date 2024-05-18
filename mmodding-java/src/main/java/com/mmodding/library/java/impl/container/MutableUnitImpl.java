package com.mmodding.library.java.impl.container;

import com.mmodding.library.java.api.container.Unit;

import java.util.Objects;

public class MutableUnitImpl<E> implements Unit.Mutable<E> {

	private E value;

	public MutableUnitImpl(E value) {
		this.value = value;
	}

	@Override
	public E value() {
		return this.value;
	}

	@Override
	public void mutateValue(E value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Unit<?> unit) {
			return Objects.equals(this.value, unit.value()) && Objects.equals(this.value, unit.value());
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}

	@Override
	public String toString() {
		return "MutableUnitImpl[value=" + this.value.toString() + "]";
	}
}
