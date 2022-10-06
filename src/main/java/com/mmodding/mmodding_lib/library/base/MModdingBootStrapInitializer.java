package com.mmodding.mmodding_lib.library.base;

import org.quiltmc.loader.api.ModContainer;

public interface MModdingBootStrapInitializer {

	String ENTRYPOINT_KEY = "mmodding_bootstrap_init";

	void onInitializeBootStrap(ModContainer mod);
}
