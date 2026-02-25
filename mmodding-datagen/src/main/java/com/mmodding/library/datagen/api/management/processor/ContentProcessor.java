package com.mmodding.library.datagen.api.management.processor;

import net.minecraft.registry.RegistryKey;

import java.util.Map;

public interface ContentProcessor<I, O> {

	static <I, O> ContentProcessor<I, O> wrapDedicatedBehavior(ContentProcessor<I, O> node, Map<RegistryKey<I>, O> dedicatedBehaviorElements) {
		return input -> dedicatedBehaviorElements.getOrDefault(input, node.process(input));
	}

	O process(RegistryKey<I> input);
}
