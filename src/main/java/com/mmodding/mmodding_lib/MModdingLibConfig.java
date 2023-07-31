package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.config.*;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigScreen;
import com.mmodding.mmodding_lib.library.config.client.screen.ConfigScreenOptions;
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
				.build();
	}

	@Override
	public ConfigScreenOptions getConfigOptions() {
		return new ConfigScreenOptions(Text.of("MModding Screen"), new ConfigScreen.BlockTextureLocation("blue_terracotta.png"));
	}
}
