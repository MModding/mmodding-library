package com.mmodding.library.core.api.registry.extension;

import com.mmodding.library.java.api.function.SingleTypeFunction;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

/**
 * Few extensions provide to the {@link RegistryKey} class.
 * @param <T>
 */
public interface RegistryKeyExtension<T> {

	/**
	 * Maps the current key value to a new key of the same assigned registry.
	 * @param mapper the mapper
	 * @return the newly created registry key
	 */
	default RegistryKey<T> mapValue(SingleTypeFunction<Identifier> mapper) {
		throw new IllegalStateException();
	}
}
