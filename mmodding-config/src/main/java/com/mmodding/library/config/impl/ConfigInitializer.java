package com.mmodding.library.config.impl;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.ConfigLevel;
import com.mmodding.library.config.api.ConfigNetworkManagement;
import com.mmodding.library.config.api.content.ConfigSpec;
import com.mmodding.library.core.api.MModdingLibrary;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;

public class ConfigInitializer implements ModInitializer {

	public static final ConfigSpec COMMON_SPEC = ConfigSpec.create()
		.bool("strict_schema_mode", true);

	public static final Config COMMON_CONFIG = Config.builder("mmodding", "mmodding/common", COMMON_SPEC)
		.withLevel(ConfigLevel.IN_GAME_MODIFICATION)
		.withNetworkManagement(ConfigNetworkManagement.LOCALLY_MANAGED)
		.build(MModdingLibrary.createId("common_config"));

	@Override
	public void onInitialize() {
		ServerLevelEvents.LOAD.register((_, _) -> {
			ConfigsImpl.getAll().values().forEach((config) -> {
				if (config.getLevel().equals(ConfigLevel.LEVEL_LOAD)) {
					ConfigLoader.load(config);
				}
			});
		});
	}
}
