package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.config.*;
import com.mmodding.mmodding_lib.library.config.ConfigOptions;
import com.mmodding.mmodding_lib.library.utils.TextureLocation;
import net.minecraft.text.Text;

public class MModdingLibConfig implements Config {

	@Override
	public String getQualifier() {
		return "mmodding_lib";
	}

	@Override
	public String getFilePath() {
		return "mmodding/common";
	}

	@Override
	public ConfigObject defaultConfig() {
		return new ConfigObject.Builder()
			.addBooleanParameter("showMModdingLibraryMods", true)
			.addBooleanParameter("showMModdingLibraryLocalCaches", false)
			.build();
	}

	@Override
	public ConfigOptions getConfigOptions() {
		return new ConfigOptions(Text.of("MModding Screen"), new TextureLocation.Block("blue_terracotta"));
	}
}
