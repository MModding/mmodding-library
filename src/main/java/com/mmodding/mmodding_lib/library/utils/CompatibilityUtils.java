package com.mmodding.mmodding_lib.library.utils;

import org.quiltmc.loader.api.QuiltLoader;

import java.util.function.Supplier;

public class CompatibilityUtils {

	public static <T> T getIfModLoadedOrElse(String modId, Supplier<T> ifLoaded, Supplier<T> orElse) {
		return QuiltLoader.isModLoaded(modId) ? ifLoaded.get() : orElse.get();
	}

	public static void executeIfModLoaded(String modId, Runnable run) {
		if (QuiltLoader.isModLoaded(modId)) {
			run.run();
		}
	}
}
