package com.mmodding.library.java.api.container;

public class Typed<T> {

	private final Class<T> type;
	private final T value;

	private Typed(Class<T> type, T value) {
		this.type = type;
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	public static <T> Typed<T> of(T value) {
		return Typed.of((Class<T>) value.getClass(), value);
	}

	public static <T> Typed<T> of(Class<T> type, T value) {
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

	public Class<T> getType() {
		return this.type;
	}

	public T getValue() {
		return this.value;
	}
}
