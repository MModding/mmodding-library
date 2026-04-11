package com.mmodding.library.core.api.registry.extension;

import com.mmodding.library.java.api.function.AutoMapper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Few extensions provide to the {@link ResourceKey} class.
 * @param <T>
 */
public interface ResourceKeyExtension<T> {

	/**
	 * Maps the current key value to a new key of the same assigned registry.
	 * @param mapper the mapper
	 * @return the newly created registry key
	 */
	default ResourceKey<T> mapValue(AutoMapper<ResourceLocation> mapper) {
		throw new IllegalStateException();
	}
}
