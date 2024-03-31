package com.mmodding.library.core.api.registry;

import com.mmodding.library.core.api.registry.pushable.LiteRegistryPushable;
import com.mmodding.library.core.api.registry.pushable.RegistryPushable;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface Registrable<T> {

	default void register(Registry<T> registry, Identifier identifier) {
		Registry.register(registry, identifier, this.as());
	}

	default void register(RegistryPushable<T> pushable) {
		this.register(pushable.getRegistry(), pushable.getIdentifier());
	}

	default void register(LiteRegistry<T> registry, Identifier identifier) {
		registry.register(identifier, this.as());
	}

	default void register(LiteRegistryPushable<T> pushable) {
		this.register(pushable.getRegistry(), pushable.getIdentifier());
	}

	default RegistrationStatus getRegistrationStatus() {
		throw new AssertionError();
	}

	default T as() {
		throw new IllegalStateException();
	}
}
