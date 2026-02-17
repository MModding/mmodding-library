package com.mmodding.library.config.test;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.ConfigLevel;
import com.mmodding.library.config.api.ConfigNetworkManagement;
import com.mmodding.library.config.api.schema.ConfigSchema;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.ExtendedModInitializer;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.object.ObjectUtil;

public class ConfigTests implements ExtendedModInitializer {

	public static final ConfigSchema SCHEMA = ConfigSchema.create()
		.bool("boolean")
		.color("color")
		.category("category", category -> category
			.string("string")
			.floatingRange("range", 1.0f, 3.0f)
			.list("list")
			.enumValue("enum", TestConfigEnum.class)
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
				.floatingRange("range", 1.0f)
				.list("list", MixedList.of(Integer.class, 10, String.class, "hi"))
				.enumValue("enum", TestConfigEnum.FIRST_FIELD)
			)
		)
		.build(MModdingLibrary.createId("config"));

	@Override
	public void setupManager(ElementsManager.Builder manager) {}

	@Override
	public void onInitialize(AdvancedContainer mod) {
		ObjectUtil.load(CONFIG);
	}

	public enum TestConfigEnum {
		FIRST_FIELD,
		SECOND_FIELD
	}
}
