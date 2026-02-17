package com.mmodding.library.config.impl.content;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.element.ConfigElementTypeWrapper;
import com.mmodding.library.config.api.element.builtin.FloatingRange;
import com.mmodding.library.config.api.element.builtin.IntegerRange;
import com.mmodding.library.config.impl.ConfigsImpl;
import com.mmodding.library.config.impl.schema.ConfigSchemaImpl;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.BiMap;
import com.mmodding.library.java.api.map.MixedMap;
import com.mmodding.library.java.api.object.Copyable;
import com.mmodding.library.java.impl.map.linked.LinkedMixedMapImpl;

public class ConfigContentImpl implements ConfigContent {

	private final BiMap<String, Class<?>, ConfigElementTypeWrapper.Properties> schema;
	private final MixedMap<String> raw;

	public ConfigContentImpl(BiMap<String, Class<?>, ConfigElementTypeWrapper.Properties> schema, MixedMap<String> raw) {
		this.schema = schema;
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
	public boolean bool(String qualifier) {
		return this.raw.get(qualifier, Boolean.class);
	}

	@Override
	public int integer(String qualifier) {
		return this.raw.get(qualifier, Integer.class);
	}

	@Override
	public double floating(String qualifier) {
		return this.raw.get(qualifier, Double.class);
	}

	@Override
	public String string(String qualifier) {
		return this.raw.get(qualifier, String.class);
	}

	@Override
	public Color color(String qualifier) {
		return this.custom(qualifier, Color.class);
	}

	@Override
	public IntegerRange integerRange(String qualifier) {
		return this.custom(qualifier, IntegerRange.class);
	}

	@Override
	public FloatingRange floatingRange(String qualifier) {
		return this.custom(qualifier, FloatingRange.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Enum<T>> Enum<T> enumValue(String qualifier) {
		return this.custom(qualifier, (Class<Enum<T>>) (Class<?>) Enum.class);
	}

	@Override
	public MixedList list(String qualifier) {
		return this.raw.get(qualifier, MixedList.class).copy();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ConfigContent category(String qualifier) {
		ConfigSchemaImpl.InnerSchemaProperties properties = (ConfigSchemaImpl.InnerSchemaProperties) this.schema.getSecondValue(qualifier);
		return new ConfigContentImpl(properties.raw(), this.raw.get(qualifier, MixedMap.class));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T custom(String qualifier, Class<T> type) {
		var wrapper = (ConfigElementTypeWrapper<T, ?, ConfigElementTypeWrapper.Properties>) ConfigsImpl.WRAPPERS.get(type);
		if (wrapper == null) {
			throw new IllegalArgumentException(type + " is not a registered type");
		}
		else {
			return wrapper.resolve(this, qualifier, this.schema.getSecondValue(qualifier));
		}
	}

	public MixedMap<String> getRaw() {
		return this.raw;
	}
}
