package com.mmodding.mmodding_lib.impl;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.client.MModdingScreen;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigScreen;
import com.mmodding.mmodding_lib.library.utils.InternalOf;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import java.util.HashMap;
import java.util.Map;

@InternalOf(ConfigScreen.class)
public class ModMenuApiImpl implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return MModdingScreen::new;
	}

	@Override
	public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
		Map<String, ConfigScreenFactory<?>> configScreens = new HashMap<>();
		MModdingLib.CONFIGS.forEach(((modId, config) -> {
			if (config.getConfigOptions() != null) {
				configScreens.put(modId, screen -> new ConfigScreen(config.getQualifier(), config, screen));
			}
		}));
		return configScreens;
	}
}
