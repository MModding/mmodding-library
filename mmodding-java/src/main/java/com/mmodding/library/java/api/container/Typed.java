package com.mmodding.library.java.api.container;

public class Typed<T> {

	private final Class<?> type;
	private final T value;

	private Typed(Class<?> type, T value) {
		this.type = type;
		this.value = value;
	}

	public static <T> Typed<T> of(T value) {
		return Typed.of(value.getClass(), value);
	}

	public static <T> Typed<T> of(Class<?> type, T value) {
		return new Typed<>(type, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Typed<?> typed) {
			return this.getType() == typed.getType() && this.getValue() == typed.getValue();
		}
		else {
			return super.equals(obj);
		}
	}

	@Override
	public String toString() {
		return "Typed[type=" + this.type.toString() + ", value=" + this.value + "]";
	}

	public Class<?> getType() {
		return this.type;
	}

	public T getValue() {
		return this.value;
	}
}
