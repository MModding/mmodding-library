package com.mmodding.library.config.api.schema;

import com.mmodding.library.config.impl.schema.ConfigSchemaImpl;

import java.util.function.Consumer;

/**
 * Represents the schema of the config, which values are part of it, their types etc...
 */
public interface ConfigSchema {

	static ConfigSchema create() {
		return new ConfigSchemaImpl();
	}

	/**
	 * The EMPTY configuration schema means that your configuration will not
	 * use a schema to rule its content, meaning all values will be displayed
	 * in the screen without any restriction and as the first type the parser
	 * will understand (in example, rgb colors will be displayed as lists,
	 * unknown qualifiers will still be displayed, and qualifier types can
	 * be changed). It is very recommended to not use the EMPTY configuration
	 * schema and define yours for your configuration to avoid issues.
	 */
	static ConfigSchema empty() {
		return new ConfigSchemaImpl.EmptySchema();
	}

	ConfigSchema bool(String qualifier);

	ConfigSchema integer(String qualifier);

	ConfigSchema floating(String qualifier);

	ConfigSchema string(String qualifier);

	ConfigSchema color(String qualifier);

	ConfigSchema integerRange(String qualifier, int start, int end);

	ConfigSchema floatingRange(String qualifier, float start, float end);

	ConfigSchema list(String qualifier, Class<?> type);

	ConfigSchema category(String qualifier, Consumer<ConfigSchema> category);
}
