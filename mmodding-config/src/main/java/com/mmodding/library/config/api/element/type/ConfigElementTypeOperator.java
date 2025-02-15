package com.mmodding.library.config.api.element.type;

import com.mmodding.library.config.api.schema.ConfigSchema;
import com.mmodding.library.config.impl.element.ConfigElementTypeOperatorImpl;

public record ConfigElementTypeOperator(
	ConfigElementTypeResolver resolver,
	ConfigElementTypeWriter writer
) {

	/**
	 * Registers a specific {@link ConfigElementTypeOperator} which allows {@link ConfigSchema} to support the type.
	 * @param type the type
	 * @param operator the type resolver
	 */
	public static <T> void register(Class<T> type, ConfigElementTypeOperator operator) {
		ConfigElementTypeOperatorImpl.OPERATORS.put(type, operator);
	}
}
