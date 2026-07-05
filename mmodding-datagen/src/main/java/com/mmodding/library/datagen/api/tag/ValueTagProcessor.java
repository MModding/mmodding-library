package com.mmodding.library.datagen.api.tag;

import net.minecraft.tags.TagKey;
import java.util.function.Function;

/**
 * Works by appending the element values to the tag appender.
 * @param <T> the element type
 */
public interface ValueTagProcessor<T> {

	/**
	 * Processes the elements by generating them for the given tag.
	 * @param tag the given tag
	 * @return the value tag processor
	 * @param <T> the element type
	 */
	static <T> ValueTagProcessor<T> forTag(TagKey<T> tag) {
		return (provider, element) -> provider.apply(tag).add(element);
	}

	/**
	 * Processes the elements by generating them for the given tags.
	 * @param tags the given tags
	 * @return the value tag processor
	 * @param <T> the element type
	 */
	@SafeVarargs
	static <T> ValueTagProcessor<T> forTags(TagKey<T>... tags) {
		return (provider, element) -> {
			for (TagKey<T> tag : tags) {
				provider.apply(tag).add(element);
			}
		};
	}

	/**
	 * Processes elements by appending them to preconfigured tags
	 * @param appenderProvider provider function to retrieve tag appender objects
	 * @param element the element to process
	 */
	void process(Function<TagKey<T>, ValueTagAppender<T>> appenderProvider, T element);
}
