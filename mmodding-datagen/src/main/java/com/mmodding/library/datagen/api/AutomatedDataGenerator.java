package com.mmodding.library.datagen.api;

import com.mmodding.library.datagen.impl.AutomatedDataGeneratorImpl;

import java.util.Set;

public class AutomatedDataGenerator {

	public AutomatedDataGenerator() {
		throw new IllegalStateException("AutomatedDatagen class only contains static definitions");
	}

	public static void applyBehavior(DataBehavior behavior, Class<?>... classes) {
		AutomatedDataGeneratorImpl.REGISTRY.put(behavior, Set.of(classes));
	}
}
