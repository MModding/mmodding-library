package com.mmodding.library.config.impl.content;

import com.mmodding.library.config.api.content.ConfigSchema;
import com.mmodding.library.config.api.content.ConfigSchemaNode;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;
import java.util.Optional;

public class ConfigSchemaImpl implements ConfigSchema {

	final Map<String, ConfigSchemaNode> raw = new Object2ObjectLinkedOpenHashMap<>();

	@Override
	public Optional<ConfigSchemaNode> findNode(String propertyPath) {
		return Optional.ofNullable(this.raw.get(propertyPath));
	}
}
