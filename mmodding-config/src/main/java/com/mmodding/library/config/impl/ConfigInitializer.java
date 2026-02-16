package com.mmodding.library.config.impl;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.ConfigLevel;
import com.mmodding.library.config.api.ConfigNetworkManagement;
import com.mmodding.library.config.api.schema.ConfigSchema;
import com.mmodding.library.core.api.MModdingLibrary;
import net.fabricmc.api.ModInitializer;

public class ConfigInitializer implements ModInitializer {

	public static final ConfigSchema COMMON_SCHEMA = ConfigSchema.create()
		.bool("strict_schema_mode");

	public static final Config COMMON_CONFIG = Config.builder("mmodding", "mmodding/common")
		.withLevel(ConfigLevel.IN_GAME_MODIFICATION)
		.withNetworkManagement(ConfigNetworkManagement.LOCALLY_MANAGED)
		.withSchema(ConfigInitializer.COMMON_SCHEMA)
		.withDefaultContent(mutable -> mutable
			.bool("strict_schema_mode", false)
		)
		.build(MModdingLibrary.createId("common_config"));

	@Override
	public void onInitialize() {}
}
