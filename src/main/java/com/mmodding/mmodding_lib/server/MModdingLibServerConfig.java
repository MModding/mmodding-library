package com.mmodding.mmodding_lib.server;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigScreenOptions;

public class MModdingLibServerConfig implements Config {

	@Override
	public String getQualifier() {
		return "mmodding_lib_server";
	}

	@Override
	public String getFilePath() {
		return "mmodding/server";
	}

	@Override
	public ConfigObject defaultConfig() {
		return new ConfigObject.Builder()
			.addBooleanParameter("showMModdingLibraryServerMods", true)
			.build();
	}

	@Override
	public ConfigScreenOptions getConfigOptions() {
		return MModdingLib.LIBRARY_CONFIG.getConfigOptions();
	}
}
