package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.config.ConfigBuilder;
import com.mmodding.mmodding_lib.library.config.Config;

public class MModdingLibConfig implements Config {

	@Override
	public String getFileName() {
		return "mmodding/common";
	}

	@Override
	public ConfigBuilder defaultConfig() {
		return new ConfigBuilder();
	}
}
