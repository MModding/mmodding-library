package com.mmodding.mmodding_lib.library.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;

public class EnvironmentUtils {

	public static EnvType getEnvType() {
		return FabricLoader.getInstance().getEnvironmentType();
	}

	public static boolean isEnvironment(EnvType envType) {
		return EnvironmentUtils.getEnvType() == envType;
	}

	public static boolean isClient() {
		return EnvironmentUtils.isEnvironment(EnvType.CLIENT);
	}

	public static boolean isServer() {
		return EnvironmentUtils.isEnvironment(EnvType.SERVER);
	}

	@Environment(EnvType.CLIENT)
	public static boolean isInSinglePlayer() {
		return MinecraftClient.getInstance().isInSingleplayer();
	}
}
