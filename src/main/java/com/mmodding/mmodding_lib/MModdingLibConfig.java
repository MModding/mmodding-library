package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.config.*;
import com.mmodding.mmodding_lib.library.config.screen.ConfigScreen;
import com.mmodding.mmodding_lib.library.config.screen.ConfigScreenOptions;
import net.minecraft.text.Text;

public class MModdingLibConfig implements Config {

	@Override
	public String getConfigName() {
		return "mmodding_lib";
	}

	@Override
	public String getFileName() {
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
