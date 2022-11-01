package com.mmodding.mmodding_lib.library.utils;

import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.loader.api.minecraft.MinecraftQuiltLoader;

public class EnvironmentUtils {

	public static EnvType getEnvType() {
		return MinecraftQuiltLoader.getEnvironmentType();
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

	@ClientOnly
	public static boolean isInSinglePlayer() {
		return MinecraftClient.getInstance().isInSingleplayer();
	}
}
