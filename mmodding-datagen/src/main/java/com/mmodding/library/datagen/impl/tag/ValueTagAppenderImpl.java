package com.mmodding.library.datagen.impl.tag;

import com.mmodding.library.datagen.api.tag.ValueTagAppender;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public class ValueTagAppenderImpl<T> implements ValueTagAppender<T> {

	private final Function<T, ResourceKey<T>> keyFinder;
	private final TagAppender<T> appender;

	public ValueTagAppenderImpl(Function<T, ResourceKey<T>> keyFinder, TagAppender<T> appender) {
		this.keyFinder = keyFinder;
		this.appender = appender;
	}

	@Override
	public ValueTagAppender<T> add(T element) {
		this.appender.add(this.keyFinder.apply(element));
		return this;
	}

	@Override
	public ValueTagAppender<T> addOptional(T element) {
		this.appender.addOptional(this.keyFinder.apply(element));
		return this;
	}

	@Override
	public ValueTagAppender<T> addTag(TagKey<T> tag) {
		this.appender.addTag(tag);
		return this;
	}

	@Override
	public ValueTagAppender<T> addOptionalTag(TagKey<T> tag) {
		this.appender.addOptionalTag(tag);
		return this;
	}

	@Override
	public ValueTagAppender<T> setReplace(boolean replace) {
		this.appender.setReplace(replace);
		return this;
	}

	@Override
	public ValueTagAppender<T> forceAddTag(TagKey<T> tag) {
		this.appender.forceAddTag(tag);
		return this;
	}

	@Override
	public ValueTagAppender<T> remove(T element) {
		this.appender.remove(this.keyFinder.apply(element));
		return this;
	}

	@Override
	public ValueTagAppender<T> removeAll(Collection<T> elements) {
		return this.removeAll(elements.stream());
	}

	@Override
	public ValueTagAppender<T> removeAll(Stream<T> elements) {
		this.appender.removeAll(elements.map(this.keyFinder));
		return this;
	}

	@Override
	public ValueTagAppender<T> removeTag(TagKey<T> tag) {
		this.appender.removeTag(tag);
		return this;
	}

	@Override
	public TagBuilder getBuilder() {
		return this.appender.getBuilder();
	}
}
