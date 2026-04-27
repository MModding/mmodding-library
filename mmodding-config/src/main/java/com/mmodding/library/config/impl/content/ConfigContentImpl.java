package com.mmodding.library.config.impl.content;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.builtin.FloatingRange;
import com.mmodding.library.config.api.content.builtin.IntegerRange;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.java.api.map.MixedMap;
import com.mmodding.library.java.impl.map.linked.LinkedMixedMapImpl;

import java.util.List;
import java.util.Map;

public class ConfigContentImpl implements ConfigContent {

	private final MixedMap<String> raw;

	public ConfigContentImpl(MixedMap<String> raw) {
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
		return this.element(qualifier, Boolean.class);
	}

	@Override
	public int intValue(String qualifier) {
		return this.element(qualifier, Integer.class);
	}

	@Override
	public double doubleValue(String qualifier) {
		return this.element(qualifier, Double.class);
	}

	@Override
	public String string(String qualifier) {
		return this.element(qualifier, String.class);
	}

	@Override
	public Color color(String qualifier) {
		return this.element(qualifier, Color.class);
	}

	@Override
	public IntegerRange integerRange(String qualifier) {
		return this.element(qualifier, IntegerRange.class);
	}

	@Override
	public FloatingRange floatingRange(String qualifier) {
		return this.element(qualifier, FloatingRange.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Enum<T>> Enum<T> enumValue(String qualifier) {
		return this.element(qualifier, (Class<Enum<T>>) (Class<?>) Enum.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> list(String qualifier) {
		return (List<T>) this.raw.get(qualifier, List.class);
	}

	@Override
	public <K, V> Map<K, V> map(String property) {
		return Map.of();
	}

	@Override
	public <T> T element(String property, Class<T> type) {
		return this.raw.get(property, type);
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
