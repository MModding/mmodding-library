package com.mmodding.library.config.impl.content;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.MixedMap;

public class ConfigContentImpl implements ConfigContent {

	final MixedMap<String> raw;

	public ConfigContentImpl() {
		this(null);
	}

	public ConfigContentImpl(MixedMap<String> raw) {
		this.raw = raw != null ? raw : MixedMap.linked();
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
		return this.raw.get(qualifier, Color.class);
	}

	@Override
	public int integerRange(String qualifier) {
		return this.raw.get(qualifier, Integer.class);
	}

	@Override
	public float floatingRange(String qualifier) {
		return this.raw.get(qualifier, Float.class);
	}

	@Override
	public MixedList list(String qualifier) {
		return this.raw.get(qualifier, MixedList.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ConfigContent category(String qualifier) {
		return new ConfigContentImpl(this.raw.get(qualifier, MixedMap.class));
	}
}
