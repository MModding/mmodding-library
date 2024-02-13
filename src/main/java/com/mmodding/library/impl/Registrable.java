package com.mmodding.library.impl;

import com.mmodding.library.registry.RegistrationStatus;
import com.mmodding.library.registry.RegistryPushable;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface Registrable<T> {

	@SuppressWarnings("unchecked")
	default void register(Registry<T> registry, Identifier identifier) {
		Registry.register(registry, identifier, (T) this);
	}

	default void register(RegistryPushable<T> pushable) {
		this.register(pushable.getRegistry(), pushable.getIdentifier());
	}

	default RegistrationStatus getRegistrationStatus() {
		throw new AssertionError();
	}
}
