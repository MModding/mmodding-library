package com.mmodding.library.core.impl.registry;

import net.minecraft.registry.Registry;
import org.jetbrains.annotations.ApiStatus;

public interface StaticElementImpl<T> {

	@ApiStatus.Internal
	default Registry<T> mmodding$getRegistry() {
		throw new IllegalStateException();
	}

	@ApiStatus.Internal
	default T mmodding$as() {
		throw new IllegalStateException();
	}
}
