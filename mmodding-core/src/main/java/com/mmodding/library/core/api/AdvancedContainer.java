package com.mmodding.library.core.api;

import com.mmodding.library.core.api.registry.factory.RegistrationFactory;
import com.mmodding.library.core.api.registry.factory.RegistryKeyFactory;
import com.mmodding.library.core.impl.AdvancedContainerImpl;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public interface AdvancedContainer extends ModContainer {

	/**
	 * Returns an advanced container of the provided mod container which provides more utility methods.
	 * @param mod the mod container
	 * @return the advanced mod container
	 */
	static AdvancedContainer of(ModContainer mod) {
		return new AdvancedContainerImpl(mod);
	}

	/**
	 * Provides a {@link Logger} instance for the mod.
	 * @return the logger
	 */
	default Logger logger() {
		return LoggerFactory.getLogger(this.getMetadata().getName());
	}

	/**
	 * Creates a new {@link Identifier} without the need to provide the mod namespace manually.
	 * @param path the path of the identifier you want to create
	 * @return the newly created identifier
	 */
	default Identifier createId(String path) {
		return new Identifier(this.getMetadata().getId(), path);
	}

	/**
	 * Creates a new {@link RegistryKey} without the need to provide the mod namespace manually.
	 * @param registry the registry that the key is targeting
	 * @param path the path of the identifier you want to create
	 * @return the newly created registry key
	 */
	default <T> RegistryKey<T> createKey(RegistryKey<? extends Registry<T>> registry, String path) {
		return RegistryKey.of(registry, this.createId(path));
	}

	/**
	 * Creates a {@link RegistryKeyFactory} of the specified {@link Registry}.
	 * @param registry the registry
	 */
	default <T> RegistryKeyFactory<T> keyFactory(Registry<T> registry) {
		return this.keyFactory(registry.getKey());
	}

	/**
	 * Creates a {@link RegistryKeyFactory} of the specified registry's {@link RegistryKey}.
	 * @param registry the registry key of the registry
	 */
	default <T> RegistryKeyFactory<T> keyFactory(RegistryKey<? extends Registry<T>> registry) {
		return RegistryKeyFactory.create(registry, this.getMetadata().getId());
	}

	/**
	 * Allows to make a bunch of registrations for a specified {@link Registry}.
	 * @param registry the registry
	 * @param consumer the registrations
	 */
	default <T> void register(Registry<T> registry, Consumer<RegistrationFactory<T>> consumer) {
		consumer.accept(RegistrationFactory.create(registry, this.getMetadata().getId()));
	}

	/**
	 * Allows to make a bunch of registrations for a specified {@link Registerable}.
	 * @param registry the registry key of the registry
	 * @param registerable the registrable
	 * @param consumer the registrations
	 */
	default <T> void register(RegistryKey<? extends Registry<T>> registry, Registerable<T> registerable, Consumer<RegistrationFactory<T>> consumer) {
		consumer.accept(RegistrationFactory.create(registry, registerable, this.getMetadata().getId()));
	}
}
