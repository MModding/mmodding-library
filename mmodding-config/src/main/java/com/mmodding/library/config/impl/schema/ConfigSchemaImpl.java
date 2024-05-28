package com.mmodding.library.config.impl.schema;

import com.mmodding.library.config.api.schema.ConfigSchema;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.map.BiMap;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class ConfigSchemaImpl implements ConfigSchema {

	private final BiMap<String, Class<?>, Map<String, ?>> raw = BiMap.create();

	public static boolean isEmpty(ConfigSchema schema) {
		return schema instanceof ConfigSchemaImpl.EmptySchema;
	}

	@Override
	public ConfigSchema bool(String qualifier) {
		this.raw.put(qualifier, Boolean.class, Map.of());
		return this;
	}

	@Override
	public ConfigSchema integer(String qualifier) {
		this.raw.put(qualifier, Integer.class, Map.of());
		return this;
	}

	@Override
	public ConfigSchema floating(String qualifier) {
		this.raw.put(qualifier, Float.class, Map.of());
		return this;
	}

	@Override
	public ConfigSchema string(String qualifier) {
		this.raw.put(qualifier, String.class, Map.of());
		return this;
	}

	@Override
	public ConfigSchema color(String qualifier) {
		this.raw.put(qualifier, Color.class, Map.of());
		return this;
	}

	@Override
	public ConfigSchema integerRange(String qualifier, int start, int end) {
		this.raw.put(qualifier, IntStream.class, Map.of("start", start, "end", end));
		return this;
	}

	@Override
	public ConfigSchema floatingRange(String qualifier, float start, float end) {
		this.raw.put(qualifier, DoubleStream.class, Map.of("start", start, "end", end));
		return this;
	}

	@Override
	public ConfigSchema list(String qualifier, Class<?> type) {
		this.raw.put(qualifier, List.class, Map.of("type", type));
		return this;
	}

	@Override
	public ConfigSchema category(String qualifier, Consumer<ConfigSchema> category) {
		ConfigSchemaImpl schema = (ConfigSchemaImpl) ConfigSchema.create();
		this.raw.put(qualifier, ConfigSchema.class, schema.raw);
		return this;
	}

	public static class EmptySchema implements ConfigSchema {

		@Override
		public ConfigSchema bool(String qualifier) {
			return this;
		}

		@Override
		public ConfigSchema integer(String qualifier) {
			return this;
		}

		@Override
		public ConfigSchema floating(String Qualifier) {
			return this;
		}

		@Override
		public ConfigSchema string(String qualifier) {
			return this;
		}

		@Override
		public ConfigSchema color(String qualifier) {
			return this;
		}

		@Override
		public ConfigSchema integerRange(String qualifier, int start, int end) {
			return this;
		}

		@Override
		public ConfigSchema floatingRange(String qualifier, float start, float end) {
			return this;
		}

		@Override
		public ConfigSchema list(String qualifier, Class<?> type) {
			return this;
		}

		@Override
		public ConfigSchema category(String qualifier, Consumer<ConfigSchema> category) {
			return this;
		}
	}
}
