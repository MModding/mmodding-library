package com.mmodding.library.core.api;

import com.mmodding.library.core.api.registry.factory.RegistryKeyFactory;
import com.mmodding.library.core.impl.AdvancedContainerImpl;
import net.fabricmc.loader.api.ModContainer;
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
	 * Allows to register content for a specific registry in a more efficient way.
	 * It makes use of the {@link  RegistryKeyFactory} object.
	 * @param registry the registry to register content into
	 * @param consumer the registrations made by the mod
	 */
	default <T> void withRegistry(Registry<T> registry, Consumer<RegistryKeyFactory<T>> consumer) {
		this.withRegistry(registry.getKey(), consumer);
	}

	/**
	 * Allows to register content for a specific registry in a more efficient way.
	 * It makes use of the {@link  RegistryKeyFactory} object.
	 * @param registry the registry to register content into
	 * @param consumer the registrations made by the mod
	 */
	default <T> void withRegistry(RegistryKey<? extends Registry<T>> registry, Consumer<RegistryKeyFactory<T>> consumer) {
		consumer.accept(RegistryKeyFactory.create(registry, this.getMetadata().getId()));
	}
}
