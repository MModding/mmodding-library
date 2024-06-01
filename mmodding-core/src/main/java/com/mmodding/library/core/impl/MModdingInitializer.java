package com.mmodding.library.core.impl;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.api.management.ElementsManager;
import dev.yumi.commons.event.EventManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.fabricmc.fabric.api.event.registry.DynamicRegistryView;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.*;

public class MModdingInitializer implements ModInitializer {

	public static final EventManager<Identifier> EVENT_MANAGER = new EventManager<>(MModdingLibrary.createId("default"), Identifier::tryParse);

	public static final Map<String, ElementsManager> MANAGERS = new HashMap<>();

	@Override
	public void onInitialize() {
		DynamicRegistrySetupCallback.EVENT.register(MModdingInitializer::dynamicRegistrySetup);
	}

	private static void dynamicRegistrySetup(DynamicRegistryView view) {
		MANAGERS.forEach((modId, manager) -> MModdingInitializer.managerSetup(view, modId, manager));
	}

	private static void managerSetup(DynamicRegistryView view, String modId, ElementsManager manager) {
		if (manager.sealed) return;
		Set<RegistryKey<? extends Registry<?>>> requiredRegistryKeys = new HashSet<>();
		manager.dynamics.forEachFirst(first -> requiredRegistryKeys.addAll(Arrays.stream(first.getRequiredKeys()).toList()));
		List<Registry<?>> requiredRegistries = new ArrayList<>();
		requiredRegistryKeys.forEach(registryKey -> requiredRegistries.add(view.getOptional(registryKey).orElse(null)));
		if (requiredRegistries.stream().filter(Objects::nonNull).toList().size() == requiredRegistryKeys.size()) {
			manager.initDynamicContent(modId, requiredRegistries);
			manager.sealed = true;
		}
	}

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
