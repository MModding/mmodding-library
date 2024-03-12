package com.mmodding.library.registry.api.companion;

import com.mmodding.library.registry.impl.companion.RegistryKeyAttachmentImpl;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public interface RegistryKeyAttachment<T, E> {

	static <T> BiFunction<DynamicRegistryManager, T, RegistryKey<T>> classic(Registry<T> registry) {
		return (manager, object) -> registry.getKey(object).orElseThrow();
	}

	static <T> BiFunction<DynamicRegistryManager, T, RegistryKey<T>> dynamic(RegistryKey<? extends Registry<T>> registryKey) {
		return (manager, object) -> manager.get(registryKey).getKey(object).orElseThrow();
	}

	/**
	 * Allows to create a new RegistryKeyAttachment
	 * @param retriever a BiFunction that allows to retrieve a RegistryKey from the initial object and (optionally) a dynamic registry
	 * @return the new RegistryKeyAttachment
	 * @param <T> the type of the RegistryKey object
	 * @param <E> the type of the attached value
	 */
	static <T, E> RegistryKeyAttachment<T, E> create(BiFunction<DynamicRegistryManager, T, RegistryKey<T>> retriever) {
		return new RegistryKeyAttachmentImpl<>(retriever);
	}

	/**
	 * Allows to attach a value to an object by retrieving its RegistryKey
	 * @param manager the dynamic registry manager
	 * @param object the object
	 * @param value the attached value
	 */
	void put(@Nullable DynamicRegistryManager manager, T object, E value);

	/**
	 * Allows to attach a value to a RegistryKey
	 * @param key the registry key
	 * @param value the attached value
	 */
	void put(RegistryKey<T> key, E value);

	/**
	 * Allows to retrieve an attached value of an object using object's RegistryKey
	 * @param manager the dynamic registry manager
	 * @param object the object
	 * @return the attached value
	 */
	E get(@Nullable DynamicRegistryManager manager, T object);

	E get(RegistryKey<T> key);
}
