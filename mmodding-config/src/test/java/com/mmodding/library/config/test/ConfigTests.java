package com.mmodding.library.config.test;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.ConfigLevel;
import com.mmodding.library.config.api.ConfigNetworkManagement;
import com.mmodding.library.config.api.schema.ConfigSchema;
import com.mmodding.library.core.api.MModdingLibrary;

public class ConfigTests {

	public static final ConfigSchema SCHEMA = ConfigSchema.create()
		.bool("boolean")
		.color("color")
		.category("category", category -> category
			.string("string")
			.floatingRange("range", 1.0f, 3.0f)
			.list("list")
		);

	public static final Config CONFIG = Config.builder("mmodding", "common/mmodding")
		.withLevel(ConfigLevel.WORLD_LOAD)
		.withNetworkManagement(ConfigNetworkManagement.LOCALLY_MANAGED)
		.withSchema(ConfigTests.SCHEMA)
		.build(MModdingLibrary.createId("config"));
}
