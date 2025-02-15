package com.mmodding.library.config.api.element;

import java.util.Map;

public class ConfigElementProperties<T> {

	private final Class<T> type;
	private final Map<String, ?> properties;

	protected ConfigElementProperties(Class<T> type, Map<String, ?> properties) {
		this.type = type;
		this.properties = properties;
	}

	public Class<T> getType() {
		return this.type;
	}

	public Map<String, ?> getProperties() {
		return this.properties;
	}
}
