package com.mmodding.mmodding_lib.library.utils;


import net.minecraft.util.crash.CrashReport;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class ObjectUtils {

	public static <T> T assumeNotNull(T value, Supplier<Exception> otherwise) {
		try {
			if (value == null) {
				throw otherwise.get();
			}
		}
		catch (Exception exception) {
			CrashReport.create(exception, "Wrong Null Value");
		}
		return value;
	}

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
