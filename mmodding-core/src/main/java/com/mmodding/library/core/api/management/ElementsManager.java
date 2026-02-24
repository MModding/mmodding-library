package com.mmodding.library.core.api.management;

import com.mmodding.library.core.api.management.content.*;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public interface ElementsManager {

	interface Builder {

		/**
		 * Adds a {@link ContentProvider}.
		 * @param provider the content provider
		 * @return the builder
		 */
		ElementsManager.Builder content(ContentProvider provider);

		/**
		 * Adds a {@link ResourceProvider} which allows generating json files of builtin registry elements.
		 * @param key the registry
		 * @param provider the bootstrap provider
		 * @return the builder
		 * @param <T> the element class
		 */
		<T> ElementsManager.Builder resource(RegistryKey<Registry<T>> key, ResourceProvider<T> provider);

		/**
		 * Adds a {@link ContentProvider} if a specific mod is loaded.
		 * @param modId the namespace of the mod
		 * @param provider the content provider
		 * @return the builder
		 */
		ElementsManager.Builder contentIfLoaded(String modId, ContentProvider provider);

		/**
		 * Adds a {@link ResourceProvider} if a specific mod is loaded.
		 * @param modId the namespace of the mod
		 * @param provider the bootstrap provider
		 * @return the builder
		 * @param <T> the element class
		 */
		<T> ElementsManager.Builder resourceIfLoaded(String modId, RegistryKey<Registry<T>> key, ResourceProvider<T> provider);
	}
}
