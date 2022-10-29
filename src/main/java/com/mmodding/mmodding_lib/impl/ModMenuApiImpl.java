package com.mmodding.mmodding_lib.impl;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.config.ConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import java.util.HashMap;
import java.util.Map;

public class ModMenuApiImpl implements ModMenuApi {

	@Override
	public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
		Map<String, ConfigScreenFactory<?>> configScreens = new HashMap<>();
		MModdingLib.configs.forEach(((modId, config) -> {
			if (config.getConfigOptions() != null) {
				configScreens.put(modId, screen -> new ConfigScreen(modId, config, screen));
			}
		}));
		return configScreens;
	}
}
