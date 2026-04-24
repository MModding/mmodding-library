package com.mmodding.library.core.api.management.content;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import net.minecraft.data.worldgen.BootstrapContext;

/**
 * A functional interface that allows generating resources filling dynamic registries, all thanks to data generation.
 * @see ElementsManager
 */
@FunctionalInterface
public interface ResourceProvider<T> {

	void configure(AdvancedContainer mod, BootstrapContext<T> context);
}
