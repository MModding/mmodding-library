package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.lib.base.MModdingModContainer;
import org.apache.commons.lang3.StringUtils;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import java.util.ArrayList;
import java.util.List;

public class MModdingLib implements ModInitializer {

	public static MModdingModContainer mmoddingLib;

	public static final List<MModdingModContainer> mmoddingMods = new ArrayList<>();

	@Override
	public void onInitialize(ModContainer mod) {
		mmoddingLib = MModdingModContainer.from(mod);

		mmoddingLib.getLogger().info("Initialize {}", mmoddingLib.getName());

		String mods = "MModding Library Mods :";
		for (MModdingModContainer mmoddingMod: mmoddingMods) {
			mods = mods.concat(" " + mmoddingMod.getName() + " [" + mmoddingMod.getIdentifier() + "],");
		}

		mods = StringUtils.chop(mods);
		mmoddingLib.getLogger().info(mods);
	}
}
