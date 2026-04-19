package com.mmodding.library.core.api.management.content;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;

/**
 * A functional interface that allows generating resources filling dynamic registries, all thanks to data generation.
 * @see ElementsManager
 */
@FunctionalInterface
public interface ResourceProvider {

	void run(AdvancedContainer mod, FabricDynamicRegistryProvider.Entries registerable);
}
