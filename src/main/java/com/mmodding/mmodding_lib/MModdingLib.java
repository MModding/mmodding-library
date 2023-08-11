package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.base.MModdingModContainer;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.events.MModdingInitializationEvents;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MModdingLib implements ModInitializer {

	public static final MModdingLibConfig MMODDING_LIBRARY_CONFIG = new MModdingLibConfig();

	public static final List<MModdingModContainer> MMODDING_MODS = new ArrayList<>();

	public static final Map<String, Config> CONFIGS = new HashMap<>();

	public static MModdingModContainer container;

	@Override
	public void onInitialize(ModContainer mod) {

		MModdingLib.container = MModdingModContainer.from(mod);

		MModdingInitializationEvents.START.invoker().onMModdingInitializationStart(MModdingLib.container);

		MMODDING_LIBRARY_CONFIG.initializeConfig();

		MModdingLib.container.getLogger().info("Initialize {}", MModdingLib.container.getName());

		Events.register();

		if (MMODDING_LIBRARY_CONFIG.getContent().getBoolean("showMModdingLibraryMods")) {
			String mods = "MModding Library Mods :";
			for (MModdingModContainer mmoddingMod : MMODDING_MODS) {
				mods = mods.concat(" " + mmoddingMod.getName() + " [" + mmoddingMod.getIdentifier() + "],");
			}

			mods = StringUtils.chop(mods);
			MModdingLib.container.getLogger().info(mods);
		}

		MModdingInitializationEvents.END.invoker().onMModdingInitializationEnd(MModdingLib.container);
	}

	public static String id() {
		return "mmodding_lib";
	}

	public static Identifier createId(String path) {
		return new Identifier(MModdingLib.id(), path);
	}
}
