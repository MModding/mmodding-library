package com.mmodding.mmodding_lib.library.integrations.modmenu;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.colors.Color;
import com.mmodding.mmodding_lib.library.base.MModdingBootstrapInitializer;
import com.mmodding.mmodding_lib.library.base.MModdingClientModInitializer;
import com.mmodding.mmodding_lib.library.base.MModdingModInitializer;
import com.mmodding.mmodding_lib.library.base.MModdingServerModInitializer;
import com.mmodding.mmodding_lib.library.utils.BiArrayList;
import com.mmodding.mmodding_lib.library.utils.BiList;
import com.mmodding.mmodding_lib.library.utils.EnvironmentUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModMenuIntegration {

	public static final Map<Identifier, CustomBadge> CUSTOM_BADGES_REGISTRY = new HashMap<>();

	private static class ModsUsingMModdingLibrary implements CustomBadge.Provider {

		@Override
		public List<String> getMods(List<? extends EntrypointContainer<?>> entrypointContainers) {
			List<String> mods = new ArrayList<>();
			entrypointContainers.stream()
				.filter(this::filterMods)
				.forEachOrdered(entrypointContainer -> mods.add(entrypointContainer.getProvider().getMetadata().getId()));
			return mods;
		}

		private boolean filterMods(EntrypointContainer<?> entrypointContainer) {
			if (entrypointContainer.getEntrypoint() instanceof ModInitializer) {
				return entrypointContainer.getEntrypoint() instanceof MModdingModInitializer;
			}
			else if (entrypointContainer.getEntrypoint() instanceof ClientModInitializer) {
				return entrypointContainer.getEntrypoint() instanceof MModdingClientModInitializer;
			}
			else if (entrypointContainer.getEntrypoint() instanceof DedicatedServerModInitializer) {
				return entrypointContainer.getEntrypoint() instanceof MModdingServerModInitializer;
			}
			else {
				return entrypointContainer.getEntrypoint() instanceof MModdingBootstrapInitializer;
			}
		}
	}

	static {
		BiList<String, Class<?>> entrypointInfo = new BiArrayList<>();
		entrypointInfo.add("main", ModInitializer.class);
		if (EnvironmentUtils.isClient()) {
			entrypointInfo.add("client", ClientModInitializer.class);
		}
		else if (EnvironmentUtils.isServer()) {
			entrypointInfo.add("server", DedicatedServerModInitializer.class);
		}
		entrypointInfo.add(MModdingBootstrapInitializer.ENTRYPOINT_KEY, MModdingBootstrapInitializer.class);
		CustomBadge mmoddingLibraryModBadge = new CustomBadge(
			entrypointInfo,
			new ModsUsingMModdingLibrary(),
			Color.rgb(100, 0, 200),
			Color.rgb(80, 0, 180)
		);
		mmoddingLibraryModBadge.register(MModdingLib.createId("uses_mmodding_library"));
	}
}
