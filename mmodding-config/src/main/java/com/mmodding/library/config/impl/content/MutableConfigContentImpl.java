package com.mmodding.library.config.impl.content;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.MutableConfigContent;
import com.mmodding.library.config.api.element.ConfigElementTypeWrapper;
import com.mmodding.library.config.api.element.builtin.FloatingRange;
import com.mmodding.library.config.api.element.builtin.IntegerRange;
import com.mmodding.library.config.impl.ConfigsImpl;
import com.mmodding.library.config.impl.schema.ConfigSchemaImpl;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.function.consumer.ReturnableConsumer;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.BiMap;
import com.mmodding.library.java.api.map.MixedMap;

import java.util.function.Consumer;

public class MutableConfigContentImpl implements MutableConfigContent {

	private final BiMap<String, Class<?>, ConfigElementTypeWrapper.Properties> schema;
	private final MixedMap<String> raw;

	public MutableConfigContentImpl(BiMap<String, Class<?>, ConfigElementTypeWrapper.Properties> schema) {
		this(schema, null);
	}

	public MutableConfigContentImpl(BiMap<String, Class<?>, ConfigElementTypeWrapper.Properties> schema, MixedMap<String> raw) {
		this.schema = schema;
		this.raw = raw != null ? raw : MixedMap.linked();
	}

	@Override
	public MutableConfigContent bool(String qualifier, boolean bool) {
		this.raw.put(qualifier, Boolean.class, bool);
		return this;
	}

	@Override
	public MutableConfigContent integer(String qualifier, int integer) {
		this.raw.put(qualifier, Integer.class, integer);
		return this;
	}

	@Override
	public MutableConfigContent floating(String qualifier, double floating) {
		this.raw.put(qualifier, Double.class, floating);
		return this;
	}

	@Override
	public MutableConfigContent string(String qualifier, String string) {
		this.raw.put(qualifier, String.class, string);
		return this;
	}

	@Override
	public MutableConfigContent color(String qualifier, Color color) {
		this.custom(qualifier, Color.class, color);
		return this;
	}

	@Override
	public MutableConfigContent integerRange(String qualifier, int integer) {
		this.custom(qualifier, IntegerRange.class, integer);
		return this;
	}

	@Override
	public MutableConfigContent floatingRange(String qualifier, double floating) {
		this.custom(qualifier, FloatingRange.class, floating);
		return this;
	}

	@Override
	public MutableConfigContent list(String qualifier, MixedList list) {
		this.raw.put(qualifier, MixedList.class, list);
		return this;
	}

	@Override
	public MutableConfigContent category(String qualifier, Consumer<MutableConfigContent> category) {
		ConfigSchemaImpl.InnerSchemaProperties properties = (ConfigSchemaImpl.InnerSchemaProperties) this.schema.getSecondValue(qualifier);
		this.raw.put(qualifier, MixedMap.class, ((MutableConfigContentImpl) ReturnableConsumer.of(category).acceptReturnable(new MutableConfigContentImpl(properties.raw()))).raw);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, V> MutableConfigContent custom(String qualifier, Class<T> type, V value) {
		var wrapper = (ConfigElementTypeWrapper<T, V, ConfigElementTypeWrapper.Properties>) ConfigsImpl.WRAPPERS.get(type);
		if (wrapper == null) {
			throw new IllegalArgumentException(type + " is not a registered type");
		}
		else {
			wrapper.modify(this, qualifier, this.schema.getSecondValue(qualifier), value);
		}
		return this;
	}

	public ConfigContent immutable() {
		return new ConfigContentImpl(this.schema, this.raw);
	}
}
