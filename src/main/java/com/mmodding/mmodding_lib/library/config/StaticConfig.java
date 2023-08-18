package com.mmodding.mmodding_lib.library.config;

public abstract class StaticConfig implements Config {

	private ConfigObject configObject = Config.super.getContent();

	@Override
	public ConfigObject getContent() {
		return this.configObject;
	}

	@Override
	public void saveConfig(ConfigObject configObject) {
		this.configObject = configObject;
	}

	public static StaticConfig of(Config config) {

		return new StaticConfig() {

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
			public ConfigOptions getConfigOptions() {
				return config.getConfigOptions();
			}

			@Override
			public NetworkingState getNetworkingSate() {
				return config.getNetworkingSate();
			}
		};
	}
}
