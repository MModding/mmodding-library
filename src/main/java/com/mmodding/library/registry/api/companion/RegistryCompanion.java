package com.mmodding.library.registry.api.companion;

import com.mmodding.library.registry.api.LiteRegistry;
import com.mmodding.library.registry.impl.companion.RegistryCompanionImpl;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public interface RegistryCompanion<T, E> {

	static <T, E> RegistryCompanion<T, E> create(Registry<T> registry, Identifier identifier) {
		return new RegistryCompanionImpl<>(registry, identifier);
	}

	void addCompanion(T element);

	void addCompanion(T element, Consumer<LiteRegistry<E>> action);

	LiteRegistry<E> getCompanion(T element);
}
