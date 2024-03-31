package com.mmodding.library.core.api.management.context;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class MultipleRegistryContext extends RegistryContext {

	@SafeVarargs
	public MultipleRegistryContext(RegistryKey<? extends Registry<?>>... registryKeys) {
		super(registryKeys);
	}
}
