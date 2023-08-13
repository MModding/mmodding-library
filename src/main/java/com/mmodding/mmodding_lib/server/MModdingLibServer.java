package com.mmodding.mmodding_lib.server;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.base.AdvancedModContainer;
import com.mmodding.mmodding_lib.library.base.MModdingServerModInitializer;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.events.server.MModdingServerInitializationEvents;
import org.apache.commons.lang3.StringUtils;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;
import org.quiltmc.qsl.base.api.entrypoint.server.DedicatedServerModInitializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DedicatedServerOnly
public class MModdingLibServer implements DedicatedServerModInitializer {

	public static final List<AdvancedModContainer> MMODDING_SERVER_MODS = MModdingLibServer.getMModdingServerMods();

	public static final Map<String, Config> SERVER_CONFIGS = new HashMap<>();

	public static final AdvancedModContainer LIBRARY_CONTAINER = MModdingLib.LIBRARY_CONTAINER;

	public static final MModdingLibServerConfig LIBRARY_SERVER_CONFIG = new MModdingLibServerConfig();

	@Override
	public void onInitializeServer(ModContainer mod) {

		MModdingServerInitializationEvents.START.invoker().onMModdingServerInitializationStart(LIBRARY_CONTAINER);

		LIBRARY_SERVER_CONFIG.initializeConfig();

		LIBRARY_CONTAINER.getLogger().info("Initialize {} Server", LIBRARY_CONTAINER.metadata().name());

		if (LIBRARY_SERVER_CONFIG.getContent().getBoolean("showMModdingLibraryServerMods")) {
			String mods = "MModding Library Server Mods :";
			for (AdvancedModContainer mmoddingServerMod : MMODDING_SERVER_MODS) {
				mods = mods.concat(" " + mmoddingServerMod.metadata().name() + " [" + mmoddingServerMod.metadata().id() + "],");
			}

			mods = StringUtils.chop(mods);
			LIBRARY_CONTAINER.getLogger().info(mods);
		}

		MModdingServerInitializationEvents.END.invoker().onMModdingServerInitializationEnd(LIBRARY_CONTAINER);
	}

	private static List<AdvancedModContainer> getMModdingServerMods() {
		List<AdvancedModContainer> advancedContainers = new ArrayList<>();

		QuiltLoader.getEntrypointContainers(DedicatedServerModInitializer.ENTRYPOINT_KEY, DedicatedServerModInitializer.class)
			.stream().filter(mod -> mod.getEntrypoint() instanceof MModdingServerModInitializer)
			.forEachOrdered(mod -> advancedContainers.add(AdvancedModContainer.of(mod.getProvider())));

		return List.copyOf(advancedContainers);
	}
}
