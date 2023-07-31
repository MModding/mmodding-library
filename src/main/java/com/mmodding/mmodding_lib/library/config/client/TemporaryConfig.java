package com.mmodding.mmodding_lib.library.config.client;

import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.config.ConfigObject;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigScreenOptions;

public abstract class TemporaryConfig implements Config {

	private ConfigObject configObject = Config.super.getContent();
	@Override
	public void saveConfig(ConfigObject configObject) {
		this.configObject = configObject;
	}

	@Override
	public ConfigObject getContent() {
		return this.configObject;
	}

	public static TemporaryConfig fromConfig(Config config) {

		return new TemporaryConfig() {

			@Override
			public String getQualifier() {
				return config.getQualifier();
			}

			@Override
			public String getFilePath() {
				return config.getFilePath();
			}

			@Override
			public ConfigObject defaultConfig() {
				return config.defaultConfig();
			}

			@Override
			public ConfigScreenOptions getConfigOptions() {
				return config.getConfigOptions();
			}
		};
	}
}
