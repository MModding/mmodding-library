package com.mmodding.library.registry.api.context;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class MultipleRegistryContext extends RegistryContext {

	@SafeVarargs
	public MultipleRegistryContext(RegistryKey<? extends Registry<?>>... registryKeys) {
		super(registryKeys);
	}
}
