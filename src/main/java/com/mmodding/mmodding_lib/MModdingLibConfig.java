package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.config.ConfigBuilder;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.config.ConfigScreen;
import com.mmodding.mmodding_lib.library.config.ConfigScreenOptions;
import net.minecraft.text.Text;

public class MModdingLibConfig implements Config {

	@Override
	public String getFileName() {
		return "mmodding/common";
	}

	@Override
	public ConfigBuilder defaultConfig() {
		return new ConfigBuilder()
				.addBooleanParameter("showMModdingLibraryMods", true);
	}

	@Override
	public ConfigScreenOptions getConfigOptions() {
		return new ConfigScreenOptions(Text.of("MModding Screen"), new ConfigScreen.BlockTextureLocation("blue_terracotta.png"));
	}
}
