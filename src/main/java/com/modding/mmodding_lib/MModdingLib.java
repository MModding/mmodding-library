package com.modding.mmodding_lib;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MModdingLib implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("MModding Library");

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Initialize {}", mod.metadata().name());
	}

}
