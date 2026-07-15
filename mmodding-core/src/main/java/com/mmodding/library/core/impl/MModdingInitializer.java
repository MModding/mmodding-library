package com.mmodding.library.core.impl;

import com.mmodding.library.core.api.MModdingLibrary;
import dev.yumi.commons.event.EventManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.resources.Identifier;

import java.util.*;

public class MModdingInitializer implements ModInitializer {

	public static final EventManager<Identifier> EVENT_MANAGER = new EventManager<>(MModdingLibrary.createId("default"), Identifier::parse);

	private static final Map<String, Class<?>> SUPPORTED_ENTRYPOINT_TYPES = Map.of(
		"main", ModInitializer.class,
		"client", ClientModInitializer.class,
		"server", DedicatedServerModInitializer.class,
		"fabric-datagen", DataGeneratorEntrypoint.class
	);

	@Override
	public void onInitialize() {}

	public static ModContainer getModContainer(Class<?> entrypoint) {
		for (Map.Entry<String, Class<?>> entrypointType : SUPPORTED_ENTRYPOINT_TYPES.entrySet()) {
			if (entrypointType.getValue().isAssignableFrom(entrypoint)) {
				for (EntrypointContainer<?> container : FabricLoader.getInstance().getEntrypointContainers(entrypointType.getKey(), entrypointType.getValue())) {
					if (container.getEntrypoint().getClass().equals(entrypoint)) {
						return container.getProvider();
					}
				}
			}
		}
		return null;
	}

	public static ModContainer getModContainer(String mod) {
		return FabricLoader.getInstance().getModContainer(mod).orElseThrow();
	}
}
