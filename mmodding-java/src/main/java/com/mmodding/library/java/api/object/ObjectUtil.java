package com.mmodding.library.java.api.object;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class ObjectUtil {

	public static <T> T assumeNotNull(T value, Supplier<Exception> otherwise, BiConsumer<Exception, String> handler) {
		try {
			if (value == null) {
				throw otherwise.get();
			}
		}
		catch (Exception exception) {
			handler.accept(exception, "Wrong Null Value");
		}
		return value;
	}

	public static boolean checkIfPreconditionMatches(boolean first, Supplier<BooleanSupplier> second) {
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
