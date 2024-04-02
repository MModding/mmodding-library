package com.mmodding.library.core.api.registry;

import com.mmodding.library.core.api.registry.pushable.LiteRegistryPushable;
import com.mmodding.library.core.api.registry.pushable.RegistryPushable;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface Registrable<T> {

	default void register(Registry<T> registry, Identifier identifier) {
		switch (this.getRegistrationStatus()) {
			case UNREGISTERED -> {
				Registry.register(registry, identifier, this.as());
				this.postRegister(identifier, this.as());
			}
			case REGISTERED -> throw new RuntimeException("Attempted to register twice " + this.as().toString());
			case CANCELLED -> throw new RuntimeException("Attempted to register " + this.as() + " which is builtin");
		}
	}

	default void register(RegistryPushable<T> pushable) {
		this.register(pushable.getRegistry(), pushable.getIdentifier());
	}

	default void register(LiteRegistry<T> registry, Identifier identifier) {
		switch (this.getRegistrationStatus()) {
			case UNREGISTERED -> {
				registry.register(identifier, this.as());
				this.postRegister(identifier, this.as());
			}
			case REGISTERED -> throw new RuntimeException("Attempted to register twice " + this.as().toString());
			case CANCELLED -> throw new RuntimeException("Attempted to register " + this.as() + " which is builtin");
		}
	}

	default void register(LiteRegistryPushable<T> pushable) {
		this.register(pushable.getRegistry(), pushable.getIdentifier());
	}

	default void postRegister(Identifier identifier, T value) {
		throw new IllegalStateException();
	}

	default RegistrationStatus getRegistrationStatus() {
		throw new IllegalStateException();
	}

	default T as() {
		throw new IllegalStateException();
	}
}
