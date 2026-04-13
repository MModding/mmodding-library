package com.mmodding.library.datagen.api.tag;

import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.TagKey;
import java.util.function.Function;

/**
 * Works by appending the element values to the tag appender.
 * @param <T> the element type
 */
public interface ValueTagProcessor<T> {

	/**
	 * Processes elements by appending them to preconfigured tags
	 * @param appenderProvider provider function to retrieve tag appender objects
	 * @param element the element to process
	 */
	void process(Function<TagKey<T>, TagAppender<T, T>> appenderProvider, T element);
}
