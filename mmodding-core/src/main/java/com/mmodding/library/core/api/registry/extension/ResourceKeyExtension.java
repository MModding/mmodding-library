package com.mmodding.library.core.api.registry.extension;

import com.mmodding.library.java.api.function.AutoMapper;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

/**
 * Few extensions provide to the {@link ResourceKey} class.
 * @param <T>
 */
public interface ResourceKeyExtension<T> {

	/**
	 * Maps the current key identifier to a new key of the same assigned registry.
	 * @param mapper the mapper
	 * @return the newly created resource key
	 */
	default ResourceKey<T> mapIdentifier(AutoMapper<Identifier> mapper) {
		throw new IllegalStateException();
	}
}
