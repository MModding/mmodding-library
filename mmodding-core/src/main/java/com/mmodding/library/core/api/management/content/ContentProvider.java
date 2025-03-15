package com.mmodding.library.core.api.management.content;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;

/**
 * A functional interface that allows registering elements.
 * @see ElementsManager
 */
@FunctionalInterface
public interface ContentProvider {

	void register(AdvancedContainer mod);
}
