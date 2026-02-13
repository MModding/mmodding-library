package com.mmodding.library.config.impl.schema;

import com.mmodding.library.config.api.element.ConfigElementTypeWrapper;
import com.mmodding.library.config.api.element.builtin.FloatingRange;
import com.mmodding.library.config.api.element.builtin.IntegerRange;
import com.mmodding.library.config.api.schema.ConfigSchema;
import com.mmodding.library.config.impl.ConfigsImpl;
import com.mmodding.library.config.impl.element.builtin.FloatingRangeWrapper;
import com.mmodding.library.config.impl.element.builtin.IntegerRangeWrapper;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.BiMap;

import java.util.Set;
import java.util.function.Consumer;

public class ConfigSchemaImpl implements ConfigSchema {

	private static final Set<Class<?>> PRIMITIVES = Set.of(Boolean.class, Integer.class, Float.class, String.class, MixedList.class, ConfigSchema.class);

	public final BiMap<String, Class<?>, ConfigElementTypeWrapper.Properties> raw = BiMap.create();

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
		this.custom(qualifier, Double.class);
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
		this.custom(qualifier, IntegerRange.class, new IntegerRangeWrapper.Properties(min, max));
		return this;
	}

	@Override
	public ConfigSchema floatingRange(String qualifier, float min, float max) {
		this.custom(qualifier, FloatingRange.class, new FloatingRangeWrapper.Properties(min, max));
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
		category.accept(schema);
		this.raw.put(qualifier, ConfigSchema.class, new InnerSchemaProperties(schema.raw));
		return this;
	}

	@Override
	public <T> ConfigSchema custom(String qualifier, Class<T> type) {
		this.raw.put(qualifier, type, null);
		return this;
	}

	@Override
	public <T, P extends ConfigElementTypeWrapper.Properties> ConfigSchema custom(String qualifier, Class<T> type, P properties) {
		if (!ConfigSchemaImpl.PRIMITIVES.contains(type) && !ConfigsImpl.WRAPPERS.containsKey(type)) {
			throw new IllegalArgumentException(type + " is not a registered type");
		}
		this.raw.put(qualifier, type, properties);
		return this;
	}

	public record InnerSchemaProperties(BiMap<String, Class<?>, ConfigElementTypeWrapper.Properties> raw) implements ConfigElementTypeWrapper.Properties {}

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
		public <T, P extends ConfigElementTypeWrapper.Properties> ConfigSchema custom(String qualifier, Class<T> type, P properties) {
			return this;
		}
	}
}
