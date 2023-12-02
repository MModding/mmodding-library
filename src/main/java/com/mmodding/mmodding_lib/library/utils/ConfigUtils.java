package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.LocalCaches;
import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.client.ClientCaches;
import com.mmodding.mmodding_lib.client.MModdingLibClient;
import com.mmodding.mmodding_lib.library.config.Config;
import net.minecraft.util.Util;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class ConfigUtils {

	public static Config getConfig(String qualifier) {
		return switch (MModdingLib.CONFIGS.get(qualifier).getNetworkingSate()) {
			case LOCAL_CACHES -> {
				if (EnvironmentUtils.isClient()) {
					if (!EnvironmentUtils.isInSinglePlayer()) {
						yield ClientCaches.CONFIGS.get(qualifier);
					}
				}
				yield LocalCaches.CONFIGS.get(qualifier);
			}
			case CLIENT_CACHES -> {
				if (EnvironmentUtils.isClient()) {
					if (!EnvironmentUtils.isInSinglePlayer()) {
						yield ClientCaches.CONFIGS.get(qualifier);
					}
				}
				yield MModdingLib.CONFIGS.get(qualifier);
			}
			case WITHOUT_CACHES -> MModdingLib.CONFIGS.get(qualifier);
		};
	}

	@ClientOnly
	public static Config getClientConfig(String qualifier) {
		return MModdingLibClient.CLIENT_CONFIGS.get(qualifier);
	}

	public static String getSeparator() {
		return Util.getOperatingSystem() == Util.OperatingSystem.WINDOWS ? "\\" : "/";
	}
}
