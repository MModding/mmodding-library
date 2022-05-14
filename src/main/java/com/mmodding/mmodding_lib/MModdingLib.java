package com.mmodding.mmodding_lib;

import org.apache.commons.lang3.StringUtils;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MModdingLib implements ModInitializer {

	public static ModContainer mmoddingLib;

	public static final List<ModContainer> mmoddingMods = new ArrayList<>();

	public static final Logger LOGGER = LoggerFactory.getLogger("MModding Library");

	@Override
	public void onInitialize(ModContainer mod) {
		mmoddingLib = mod;

		LOGGER.info("Initialize {}", mod.metadata().name());

		String mods = "MModding Library Mods : ";
		for (ModContainer mmoddingMod: mmoddingMods) {
			mods = mods.concat(mmoddingMod.metadata().name() + " [" + mmoddingMod.metadata().id() + "],");
		}

		mods = StringUtils.chop(mods);
		LOGGER.info(mods);
	}
}
