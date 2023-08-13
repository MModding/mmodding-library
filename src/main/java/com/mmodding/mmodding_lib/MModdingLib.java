package com.mmodding.mmodding_lib;

import com.mmodding.mmodding_lib.library.base.AdvancedModContainer;
import com.mmodding.mmodding_lib.library.base.MModdingModInitializer;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.events.MModdingInitializationEvents;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MModdingLib implements ModInitializer {

	public static final List<AdvancedModContainer> MMODDING_MODS = MModdingLib.getMModdingMods();

	public static final Map<String, Config> CONFIGS = new HashMap<>();

	public static final AdvancedModContainer LIBRARY_CONTAINER = AdvancedModContainer.of(QuiltLoader.getModContainer("mmodding_lib").orElseThrow());

	public static final MModdingLibConfig LIBRARY_CONFIG = new MModdingLibConfig();

	@Override
	public void onInitialize(ModContainer mod) {

		MModdingInitializationEvents.START.invoker().onMModdingInitializationStart(LIBRARY_CONTAINER);

		LIBRARY_CONFIG.initializeConfig();

		LIBRARY_CONTAINER.getLogger().info("Initialize {}", LIBRARY_CONTAINER.metadata().name());

		Events.register();

		if (LIBRARY_CONFIG.getContent().getBoolean("showMModdingLibraryMods")) {
			String mods = "MModding Library Mods :";
			for (AdvancedModContainer mmoddingMod : MMODDING_MODS) {
				mods = mods.concat(" " + mmoddingMod.metadata().name() + " [" + mmoddingMod.metadata().id() + "],");
			}

			mods = StringUtils.chop(mods);
			LIBRARY_CONTAINER.getLogger().info(mods);
		}

		MModdingInitializationEvents.END.invoker().onMModdingInitializationEnd(LIBRARY_CONTAINER);
	}

	private static List<AdvancedModContainer> getMModdingMods() {
		List<AdvancedModContainer> advancedContainers = new ArrayList<>();

		QuiltLoader.getEntrypointContainers(ModInitializer.ENTRYPOINT_KEY, ModInitializer.class)
			.stream().filter(mod -> mod.getEntrypoint() instanceof MModdingModInitializer)
			.forEachOrdered(mod -> advancedContainers.add(AdvancedModContainer.of(mod.getProvider())));

		return List.copyOf(advancedContainers);
	}

	public static String id() {
		return "mmodding_lib";
	}

	public static Identifier createId(String path) {
		return new Identifier(MModdingLib.id(), path);
	}
}
