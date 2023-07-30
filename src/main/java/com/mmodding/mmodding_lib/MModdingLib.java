package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.base.MModdingModContainer;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.events.MModdingInitializationEvents;
import com.mmodding.mmodding_lib.library.worldgen.MModdingDensityFunctions;
import com.mmodding.mmodding_lib.library.worldgen.MModdingNoiseParameters;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MModdingLib implements ModInitializer {

	public static MModdingModContainer mmoddingLib;

	public static final MModdingLibConfig MMODDING_LIBRARY_CONFIG = new MModdingLibConfig();

	public static final List<MModdingModContainer> MMODDING_MODS = new ArrayList<>();

	public static final Map<String, Config> CONFIGS = new HashMap<>();

	@Override
	public void onInitialize(ModContainer mod) {

		mmoddingLib = MModdingModContainer.from(mod);

		MModdingInitializationEvents.START.invoker().onMModdingInitializationStart(mmoddingLib);

		MMODDING_LIBRARY_CONFIG.initializeConfig();

		mmoddingLib.getLogger().info("Initialize {}", mmoddingLib.getName());

		Events.register();
		MModdingNoiseParameters.initialize();
		MModdingDensityFunctions.initialize();

		if (MMODDING_LIBRARY_CONFIG.getContent().getBoolean("showMModdingLibraryMods")) {
			String mods = "MModding Library Mods :";
			for (MModdingModContainer mmoddingMod : MMODDING_MODS) {
				mods = mods.concat(" " + mmoddingMod.getName() + " [" + mmoddingMod.getIdentifier() + "],");
			}

			mods = StringUtils.chop(mods);
			mmoddingLib.getLogger().info(mods);
		}

		MModdingInitializationEvents.END.invoker().onMModdingInitializationEnd(mmoddingLib);
	}

	public static String id() {
		return "mmodding_lib";
	}

	public static Identifier createId(String path) {
		return new Identifier(MModdingLib.id(), path);
	}
}
