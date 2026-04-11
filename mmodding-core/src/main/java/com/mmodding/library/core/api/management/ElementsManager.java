package com.mmodding.library.core.api.management;

import com.mmodding.library.core.api.management.content.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface ElementsManager {

	/**
	 * Adds a {@link ContentProvider}.
	 * @param provider the content provider
	 * @return the builder
	 */
	ElementsManager content(ContentProvider provider);

	/**
	 * Adds a {@link ResourceProvider} which allows generating json files of builtin registry elements.
	 * @param key the registry
	 * @param provider the bootstrap provider
	 * @return the builder
	 * @param <T> the element class
	 */
	<T> ElementsManager resource(ResourceKey<Registry<T>> key, ResourceProvider<T> provider);

	/**
	 * Adds a {@link ContentProvider} if a specific mod is loaded.
	 * @param modId the namespace of the mod
	 * @param provider the content provider
	 * @return the builder
	 */
	ElementsManager ifModLoaded(String modId, ContentProvider provider);
}
