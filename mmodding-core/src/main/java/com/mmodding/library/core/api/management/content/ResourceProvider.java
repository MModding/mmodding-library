package com.mmodding.library.core.api.management.content;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import net.minecraft.data.worldgen.BootstapContext;

/**
 * A functional interface that allows generating resources filling dynamic registries, all thanks to data generation.
 * @see ElementsManager
 * @param <T> the type held in the registry
 */
@FunctionalInterface
public interface ResourceProvider<T> {

	void run(AdvancedContainer mod, BootstapContext<T> registerable);
}
