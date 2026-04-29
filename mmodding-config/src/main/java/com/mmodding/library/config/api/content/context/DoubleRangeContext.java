package com.mmodding.library.config.api.content.context;

import com.mmodding.library.config.api.content.ConfigSchemaNode;

public record DoubleRangeContext(double start, double end) implements ConfigSchemaNode.Context {

	@Override
	public boolean matches(Class<?> type) {
		return Double.class.isAssignableFrom(type);
	}
}
