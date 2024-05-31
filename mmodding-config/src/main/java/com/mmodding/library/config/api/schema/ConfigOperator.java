package com.mmodding.library.config.api.schema;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.content.ConfigContent;
import com.mmodding.library.config.impl.schema.ConfigOperatorImpl;

public class ConfigOperator {

	public static ConfigContent applySchemaOnDefault(Config config) {
		return ConfigOperator.applySchema(config.getSchema(), config.getDefaultContent());
	}

	public static ConfigContent applySchema(ConfigSchema schema, ConfigContent content) {
		return ConfigOperatorImpl.applySchema(schema, content);
	}
}
