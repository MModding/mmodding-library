package com.mmodding.mmodding_lib.library.utils;

import net.fabricmc.loader.api.FabricLoader;

import java.util.function.Supplier;

public class CompatibilityUtils {

	public static <T> T getIfModLoadedOrElse(String modId, Supplier<T> ifLoaded, Supplier<T> orElse) {
		return FabricLoader.getInstance().isModLoaded(modId) ? ifLoaded.get() : orElse.get();
	}

	public static void executeIfModLoaded(String modId, Runnable runnable) {
		if (FabricLoader.getInstance().isModLoaded(modId)) {
			runnable.run();
		}
	}
}
