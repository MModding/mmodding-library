package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.config.*;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigScreenOptions;

public class MModdingLibClientConfig implements Config {

	@Override
	public String getQualifier() {
		return "mmodding_lib_client";
	}

	@Override
	public String getFilePath() {
		return "mmodding/client";
	}

	@Override
	public ConfigObject defaultConfig() {
		return new ConfigObject.Builder()
			.addBooleanParameter("showMModdingLibraryClientCaches", false)
			.build();
	}

	@Override
	public ConfigScreenOptions getConfigOptions() {
		return MModdingLib.MMODDING_LIBRARY_CONFIG.getConfigOptions();
	}
}
