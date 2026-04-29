package com.mmodding.library.config.impl.content;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.ConfigSchema;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.java.api.map.MixedMap;
import com.mmodding.library.java.impl.map.linked.LinkedMixedMapImpl;

public class ConfigContentImpl implements ConfigContent {

	private final ConfigSchema schema;
	private final String path;
	private final MixedMap<String> raw;

	public ConfigContentImpl(ConfigSchema schema, String path, MixedMap<String> raw) {
		this.schema = schema;
		this.path = path;
		if (raw != null) {
			if (raw instanceof LinkedMixedMapImpl<String>) {
				this.raw = raw;
			}
			else {
				throw new IllegalArgumentException("MixedMap of ConfigContent object must be linked!");
			}
		}
		else {
			this.raw = MixedMap.linked();
		}
	}

	@Override
	public ConfigSchema schema() {
		return this.schema;
	}

	@Override
	public String path() {
		return this.path;
	}

	@Override
	public <T> T element(String property, Class<?> type) {
		return this.raw.get(property, this.schema.validate(ConfigContent.resolve(this.path, property), type));
	}

	@Override
	public ConfigContent category(String qualifier) {
		return this.raw.get(qualifier, ConfigContent.class);
	}

	@Override
	public BiList<String, Class<?>> getAllProperties() {
		BiList<String, Class<?>> properties = BiList.create();
		this.raw.forEach((key, value) -> properties.add(key, value.getType()));
		return properties;
	}
}
