package com.mmodding.mmodding_lib.library.utils;

public class TypedObject<T> {

	private final Class<T> type;
	private final T value;

	private TypedObject(Class<T> type, T value) {
		this.type = type;
		this.value = value;
	}

	public static <T> TypedObject<T> of(Class<T> type, T value) {
		return new TypedObject<>(type, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TypedObject<?> typed) {
			return this.getType() == typed.getType() && this.getValue() == typed.getValue();
		}
		else {
			return super.equals(obj);
		}
	}

	public Class<T> getType() {
		return this.type;
	}

	public T getValue() {
		return this.value;
	}
}
