package com.mmodding.mmodding_lib.client;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.base.AdvancedModContainer;
import com.mmodding.mmodding_lib.library.base.MModdingClientModInitializer;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.events.initialization.client.MModdingClientInitializationEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class MModdingLibClient implements ClientModInitializer {

	public static final List<AdvancedModContainer> MMODDING_CLIENT_MODS = MModdingLibClient.getMModdingClientMods();

	public static final Map<String, Config> CLIENT_CONFIGS = new HashMap<>();

	public static final AdvancedModContainer LIBRARY_CONTAINER = MModdingLib.LIBRARY_CONTAINER;

	public static final MModdingLibClientConfig LIBRARY_CLIENT_CONFIG = new MModdingLibClientConfig();

	@Override
	public void onInitializeClient() {

		MModdingClientInitializationEvents.START.invoker().onMModdingClientInitializationStart(LIBRARY_CONTAINER);

		LIBRARY_CLIENT_CONFIG.initializeConfig();

		LIBRARY_CONTAINER.getLogger().info("Initialize {} Client", LIBRARY_CONTAINER.getMetadata().getName());

		ClientEvents.register();
		ClientPacketReceivers.register();
		ClientModelPredicates.register();
		ClientGlintPacks.register();
		ClientGlintPackOverrides.register();

		if (LIBRARY_CLIENT_CONFIG.getContent().getBoolean("showMModdingLibraryClientMods")) {
			String mods = "MModding Library Client Mods :";
			for (AdvancedModContainer mmoddingClientMod : MMODDING_CLIENT_MODS) {
				mods = mods.concat(" " + mmoddingClientMod.getMetadata().getName() + " [" + mmoddingClientMod.getMetadata().getId() + "],");
			}

			mods = StringUtils.chop(mods);
			LIBRARY_CONTAINER.getLogger().info(mods);
		}

		MModdingClientInitializationEvents.END.invoker().onMModdingClientInitializationEnd(LIBRARY_CONTAINER);
	}

	private static List<AdvancedModContainer> getMModdingClientMods() {
		List<AdvancedModContainer> advancedContainers = new ArrayList<>();

		FabricLoader.getInstance().getEntrypointContainers("client", ClientModInitializer.class)
			.stream().filter(mod -> mod.getEntrypoint() instanceof MModdingClientModInitializer)
			.forEachOrdered(mod -> advancedContainers.add(AdvancedModContainer.of(mod.getProvider())));

		return List.copyOf(advancedContainers);
	}
}
