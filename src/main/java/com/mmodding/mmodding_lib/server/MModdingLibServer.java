package com.mmodding.mmodding_lib.server;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.base.AdvancedModContainer;
import com.mmodding.mmodding_lib.library.base.MModdingServerModInitializer;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.events.initialization.server.MModdingServerInitializationEvents;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.SERVER)
public class MModdingLibServer implements DedicatedServerModInitializer {

	public static final List<AdvancedModContainer> MMODDING_SERVER_MODS = MModdingLibServer.getMModdingServerMods();

	public static final Map<String, Config> SERVER_CONFIGS = new HashMap<>();

	public static final AdvancedModContainer LIBRARY_CONTAINER = MModdingLib.LIBRARY_CONTAINER;

	public static final MModdingLibServerConfig LIBRARY_SERVER_CONFIG = new MModdingLibServerConfig();

	@Override
	public void onInitializeServer() {

		MModdingServerInitializationEvents.START.invoker().onMModdingServerInitializationStart(LIBRARY_CONTAINER);

		LIBRARY_SERVER_CONFIG.initializeConfig();

		LIBRARY_CONTAINER.getLogger().info("Initialize {} Server", LIBRARY_CONTAINER.getMetadata().getName());

		if (LIBRARY_SERVER_CONFIG.getContent().getBoolean("showMModdingLibraryServerMods")) {
			String mods = "MModding Library Server Mods :";
			for (AdvancedModContainer mmoddingServerMod : MMODDING_SERVER_MODS) {
				mods = mods.concat(" " + mmoddingServerMod.getMetadata().getName() + " [" + mmoddingServerMod.getMetadata().getId() + "],");
			}

			mods = StringUtils.chop(mods);
			LIBRARY_CONTAINER.getLogger().info(mods);
		}

		MModdingServerInitializationEvents.END.invoker().onMModdingServerInitializationEnd(LIBRARY_CONTAINER);
	}

	private static List<AdvancedModContainer> getMModdingServerMods() {
		List<AdvancedModContainer> advancedContainers = new ArrayList<>();

		FabricLoader.getInstance().getEntrypointContainers("server", DedicatedServerModInitializer.class)
			.stream().filter(mod -> mod.getEntrypoint() instanceof MModdingServerModInitializer)
			.forEachOrdered(mod -> advancedContainers.add(AdvancedModContainer.of(mod.getProvider())));

		return List.copyOf(advancedContainers);
	}
}
