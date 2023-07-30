package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.client.MModdingLibClient;
import com.mmodding.mmodding_lib.library.config.Config;
import net.minecraft.util.Util;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class ConfigUtils {

	public static Config getConfig(String configName) {
		return MModdingLib.CONFIGS.get(configName);
	}

	@ClientOnly
	public static Config getClientConfig(String configName) {
		return MModdingLibClient.CLIENT_CONFIGS.get(configName);
	}

	public static String getSeparator() {
		return Util.getOperatingSystem() == Util.OperatingSystem.WINDOWS ? "\\" : "/";
	}
}
