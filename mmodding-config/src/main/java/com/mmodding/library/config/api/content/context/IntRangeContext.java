package com.mmodding.library.config.api.content.context;

import com.mmodding.library.config.api.content.ConfigSchemaNode;

public record IntRangeContext(int start, int end) implements ConfigSchemaNode.Context {

	@Override
	public boolean matches(Class<?> type) {
		return Integer.class.isAssignableFrom(type);
	}
}
