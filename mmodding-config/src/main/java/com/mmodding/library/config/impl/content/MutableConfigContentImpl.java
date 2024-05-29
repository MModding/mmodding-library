package com.mmodding.library.config.impl.content;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.MutableConfigContent;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.function.consumer.ReturnableConsumer;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.MixedMap;

import java.util.function.Consumer;

public class MutableConfigContentImpl implements MutableConfigContent {

	private final MixedMap<String> raw;

	public MutableConfigContentImpl() {
		this((MixedMap<String>) null);
	}

	public MutableConfigContentImpl(ConfigContent configContent) {
		this.raw = ((ConfigContentImpl) configContent).raw;
	}

	public MutableConfigContentImpl(MixedMap<String> raw) {
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
	public MutableConfigContent floating(String qualifier, float floating) {
		this.raw.put(qualifier, Float.class, floating);
		return this;
	}

	@Override
	public MutableConfigContent string(String qualifier, String string) {
		this.raw.put(qualifier, String.class, string);
		return this;
	}

	@Override
	public MutableConfigContent color(String qualifier, Color color) {
		this.raw.put(qualifier, Color.class, color);
		return this;
	}

	@Override
	public MutableConfigContent integerRange(String qualifier, int integer) {
		this.raw.put(qualifier, Integer.class, integer);
		return this;
	}

	@Override
	public MutableConfigContent floatingRange(String qualifier, float floating) {
		this.raw.put(qualifier, Float.class, floating);
		return this;
	}

	@Override
	public MutableConfigContent list(String qualifier, MixedList list) {
		this.raw.put(qualifier, MixedList.class, list);
		return this;
	}

	@Override
	public MutableConfigContent category(String qualifier, Consumer<MutableConfigContent> category) {
		this.raw.put(qualifier, MixedMap.class, ((MutableConfigContentImpl) ReturnableConsumer.of(category).acceptReturnable(new MutableConfigContentImpl())).raw);
		return this;
	}

	public ConfigContent immutable() {
		return new ConfigContentImpl(this.raw);
	}
}
