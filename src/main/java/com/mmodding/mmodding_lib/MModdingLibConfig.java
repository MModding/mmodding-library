package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.config.*;
import net.minecraft.text.Text;

public class MModdingLibConfig implements Config {

	@Override
	public String getFileName() {
		return "mmodding/common";
	}

	@Override
	public ConfigObject.Builder defaultConfig() {
		return new ConfigObject.Builder()
				.addBooleanParameter("showMModdingLibraryMods", true);
	}

	@Override
	public ConfigScreenOptions getConfigOptions() {
		return new ConfigScreenOptions(Text.of("MModding Screen"), new ConfigScreen.BlockTextureLocation("blue_terracotta.png"));
	}
}
