package com.mmodding.library.config.impl.schema;

import com.mmodding.library.config.api.element.ConfigElementProperties;
import com.mmodding.library.config.api.element.builtin.FloatingRange;
import com.mmodding.library.config.api.element.builtin.IntegerRange;
import com.mmodding.library.config.api.schema.ConfigSchema;
import com.mmodding.library.config.impl.element.builtin.FloatingRangeProperties;
import com.mmodding.library.config.impl.element.builtin.IntegerRangeProperties;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.BiMap;

import java.util.Map;
import java.util.function.Consumer;

public class ConfigSchemaImpl implements ConfigSchema {

	final BiMap<String, Class<?>, Map<String, ?>> raw = BiMap.create();

	public static boolean isEmpty(ConfigSchema schema) {
		return schema instanceof ConfigSchemaImpl.EmptySchema;
	}

	@Override
	public ConfigSchema bool(String qualifier) {
		this.custom(qualifier, Boolean.class);
		return this;
	}

	@Override
	public ConfigSchema integer(String qualifier) {
		this.custom(qualifier, Integer.class);
		return this;
	}

	@Override
	public ConfigSchema floating(String qualifier) {
		this.custom(qualifier, Float.class);
		return this;
	}

	@Override
	public ConfigSchema string(String qualifier) {
		this.custom(qualifier, String.class);
		return this;
	}

	@Override
	public ConfigSchema color(String qualifier) {
		this.custom(qualifier, Color.class);
		return this;
	}

	@Override
	public ConfigSchema integerRange(String qualifier, int min, int max) {
		this.custom(qualifier, IntegerRange.class, new IntegerRangeProperties(min, max));
		return this;
	}

	@Override
	public ConfigSchema floatingRange(String qualifier, float min, float max) {
		this.custom(qualifier, FloatingRange.class, new FloatingRangeProperties(min, max));
		return this;
	}

	@Override
	public ConfigSchema list(String qualifier) {
		this.custom(qualifier, MixedList.class);
		return this;
	}

	@Override
	public ConfigSchema category(String qualifier, Consumer<ConfigSchema> category) {
		ConfigSchemaImpl schema = (ConfigSchemaImpl) ConfigSchema.create();
		this.raw.put(qualifier, ConfigSchema.class, schema.raw);
		return this;
	}

	@Override
	public <T> ConfigSchema custom(String qualifier, Class<T> type) {
		this.raw.put(qualifier, type, Map.of());
		return this;
	}

	@Override
	public <T, P extends ConfigElementProperties<T>> ConfigSchema custom(String qualifier, Class<T> type, P properties) {
		this.raw.put(qualifier, type, properties.getProperties());
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
		public ConfigSchema list(String qualifier) {
			return this;
		}

		@Override
		public ConfigSchema category(String qualifier, Consumer<ConfigSchema> category) {
			return this;
		}

		@Override
		public <T> ConfigSchema custom(String qualifier, Class<T> type) {
			return this;
		}

		@Override
		public <T, P extends ConfigElementProperties<T>> ConfigSchema custom(String qualifier, Class<T> type, P properties) {
			return this;
		}
	}
}
