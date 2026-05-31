package com.mmodding.library.core.api.registry;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * An interface which enable accessing registry lookers.
 */
@FunctionalInterface
public interface RegistryLooker {

	/**
	 * Looks up for a specified registry.
	 * @param key the registry's resource key
	 * @return the returned holder getter
	 * @param <S> the registry element type
	 */
	<S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> key);
}
