package com.mmodding.library.core.api.registry;

import com.mmodding.library.core.api.registry.pushable.LiteRegistryPushable;
import com.mmodding.library.core.api.registry.pushable.RegistryPushable;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface Registrable<T> {

	default void register(Registry<T> registry, Identifier identifier) {
		if (this.getRegistrationStatus().equals(RegistrationStatus.REGISTERED)) {
			Registry.register(registry, identifier, this.as());
			this.postRegister();
		}
	}

	default void register(RegistryPushable<T> pushable) {
		this.register(pushable.getRegistry(), pushable.getIdentifier());
	}

	default void register(LiteRegistry<T> registry, Identifier identifier) {
		if (this.getRegistrationStatus().equals(RegistrationStatus.REGISTERED)) {
			registry.register(identifier, this.as());
			this.postRegister();
		}
	}

	default void register(LiteRegistryPushable<T> pushable) {
		this.register(pushable.getRegistry(), pushable.getIdentifier());
	}

	default void postRegister() {
		throw new IllegalStateException();
	}

	default RegistrationStatus getRegistrationStatus() {
		throw new IllegalStateException();
	}

	default T as() {
		throw new IllegalStateException();
	}
}
