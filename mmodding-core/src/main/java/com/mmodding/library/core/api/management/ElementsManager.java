package com.mmodding.library.core.api.management;

import com.mmodding.library.core.api.management.content.ContentProvider;

public interface ElementsManager {

	/**
	 * Adds a {@link ContentProvider}.
	 * @param provider the content provider
	 * @return the builder
	 */
	ElementsManager content(ContentProvider provider);

	/**
	 * Adds a {@link ContentProvider} if a specific mod is loaded.
	 * @param modId the namespace of the mod
	 * @param provider the content provider
	 * @return the builder
	 */
	ElementsManager ifModLoaded(String modId, ContentProvider provider);
}
