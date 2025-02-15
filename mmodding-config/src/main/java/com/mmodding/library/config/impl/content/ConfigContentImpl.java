package com.mmodding.library.config.impl.content;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.element.builtin.FloatingRange;
import com.mmodding.library.config.api.element.builtin.IntegerRange;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.MixedMap;
import com.mmodding.library.java.api.object.Copyable;
import com.mmodding.library.java.impl.map.linked.LinkedMixedMapImpl;

public class ConfigContentImpl implements ConfigContent {

	private final MixedMap<String> raw;

	public ConfigContentImpl() {
		this(null);
	}

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
		return this.raw.get(qualifier, Boolean.class);
	}

	@Override
	public int integer(String qualifier) {
		return this.raw.get(qualifier, Integer.class);
	}

	@Override
	public float floating(String qualifier) {
		return this.raw.get(qualifier, Float.class);
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
	public MixedList list(String qualifier) {
		return this.custom(qualifier, MixedList.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ConfigContent category(String qualifier) {
		return new ConfigContentImpl(this.raw.get(qualifier, MixedMap.class));
	}

	@Override
	public <T extends Copyable<T>> T custom(String qualifier, Class<T> type) {
		return this.raw.get(qualifier, type).copy();
	}

	public MixedMap<String> getRaw() {
		return this.raw;
	}
}
