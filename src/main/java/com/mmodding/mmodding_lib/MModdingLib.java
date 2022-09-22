package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.base.MModdingModContainer;
import com.mmodding.mmodding_lib.library.config.Config;
import org.apache.commons.lang3.StringUtils;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MModdingLib implements ModInitializer {

	public static MModdingModContainer mmoddingLib;

	public static MModdingLibConfig config = new MModdingLibConfig();

	public static final List<MModdingModContainer> mmoddingMods = new ArrayList<>();

	public static final Map<String, Config> configs = new HashMap<>();

	@Override
	public void onInitialize(ModContainer mod) {
		mmoddingLib = MModdingModContainer.from(mod);

		config.initializeConfig();

		mmoddingLib.getLogger().info("Initialize {}", mmoddingLib.getName());

		Events.register();

		if (config.getContent().getBoolean("showMModdingLibraryMods")) {
			String mods = "MModding Library Mods :";
			for (MModdingModContainer mmoddingMod : mmoddingMods) {
				mods = mods.concat(" " + mmoddingMod.getName() + " [" + mmoddingMod.getIdentifier() + "],");
			}

			mods = StringUtils.chop(mods);
			mmoddingLib.getLogger().info(mods);
		}

	}
}
