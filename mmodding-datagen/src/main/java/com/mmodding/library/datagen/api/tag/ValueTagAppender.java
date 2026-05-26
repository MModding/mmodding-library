package com.mmodding.library.datagen.api.tag;

import net.minecraft.tags.TagKey;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public interface ValueTagAppender<T> {

	ValueTagAppender<T> add(T element);

	default ValueTagAppender<T> add(final T... elements) {
		return this.addAll(Arrays.stream(elements));
	}

	default ValueTagAppender<T> addAll(final Collection<T> elements) {
		elements.forEach(this::add);
		return this;
	}

	default ValueTagAppender<T> addAll(final Stream<T> elements) {
		elements.forEach(this::add);
		return this;
	}

	ValueTagAppender<T> addOptional(T element);

	ValueTagAppender<T> addTag(TagKey<T> tag);

	ValueTagAppender<T> addOptionalTag(TagKey<T> tag);
}
