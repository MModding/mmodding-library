package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.caches.Caches;
import com.mmodding.mmodding_lib.library.config.Config;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class LocalCaches {

	public static final Caches.Local<String, Config> CONFIGS = new Caches.Local<>("Configs", "Qualifier", "Config");
}
