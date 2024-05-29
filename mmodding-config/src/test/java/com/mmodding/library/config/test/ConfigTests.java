package com.mmodding.library.config.test;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.ConfigLevel;
import com.mmodding.library.config.api.ConfigNetworkManagement;
import com.mmodding.library.config.api.schema.ConfigSchema;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.list.MixedList;

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
		.withDefaultContent(mutable -> mutable
			.bool("boolean", true)
			.color("color", Color.rgb(0, 0, 0))
			.category("category", category -> category
				.string("string", "value")
				.floatingRange("range", 2.0f)
				.list("list", MixedList.of(Integer.class, 10, String.class, "hi"))
			)
		)
		.build(MModdingLibrary.createId("config"));
}
