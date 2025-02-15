package com.mmodding.library.config.api.element.type;

import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.api.content.MutableConfigContent;
import com.mmodding.library.config.api.schema.ConfigSchema;
import com.mmodding.library.java.api.color.Color;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ConfigElementTypeResolver {

	/**
	 * Resolves a specific, custom type, from non-operated {@link ConfigContent} (which means that the {@link ConfigSchema}
	 * was not applied on it). In example, {@link Color} objects are supported by {@link ConfigSchema} by resolving
	 * an integer value to a {@link Color} object.
	 * @param category the current category where the object being resolved is
	 * @param properties the custom properties passed to the schema for that type
	 * @return a factory of the mutable operation brought to the mutable content
	 */
	BiConsumer<String, MutableConfigContent> resolve(ConfigContent category, ResolverProperties properties);

	interface ResolverProperties {

		<T> T get(String property);
	}
}
