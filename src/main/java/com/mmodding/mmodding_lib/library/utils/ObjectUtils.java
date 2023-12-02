package com.mmodding.mmodding_lib.library.utils;


import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class ObjectUtils {

	public static boolean checkSecondIfFirstIsValid(boolean first, Supplier<BooleanSupplier> second) {
		if (first) {
			return second.get().getAsBoolean();
		}
		else {
			return false;
		}
	}

	public static <T> T ifNullMakeDefault(T nullableObject, Supplier<T> makeDefault) {
		return nullableObject == null ? makeDefault.get() : nullableObject;
	}

	public static void load(Object ignored) {}
}
