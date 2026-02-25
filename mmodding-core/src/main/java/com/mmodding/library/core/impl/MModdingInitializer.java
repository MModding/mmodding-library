package com.mmodding.library.core.impl;

import com.mmodding.library.core.api.MModdingLibrary;
import dev.yumi.commons.event.EventManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.util.Identifier;

import java.util.*;

public class MModdingInitializer implements ModInitializer {

	public static final EventManager<Identifier> EVENT_MANAGER = new EventManager<>(MModdingLibrary.createId("default"), Identifier::tryParse);

	@Override
	public void onInitialize() {}

	public static ModContainer getModContainer(Class<?> entrypoint) {
		for (EntrypointContainer<?> container : FabricLoader.getInstance().getEntrypointContainers("main", ModInitializer.class)) {
			if (container.getEntrypoint().getClass().equals(entrypoint)) {
				return container.getProvider();
			}
		}
		return null;
	}

	public static ModContainer getModContainer(String mod) {
		return FabricLoader.getInstance().getModContainer(mod).orElseThrow();
	}
}
