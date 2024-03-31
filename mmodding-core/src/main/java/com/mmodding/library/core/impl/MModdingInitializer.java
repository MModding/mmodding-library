package com.mmodding.library.core.impl;

import com.mmodding.library.core.api.management.ElementsManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.registry.api.event.DynamicRegistryManagerSetupContext;
import org.quiltmc.qsl.registry.api.event.RegistryEvents;

import java.util.*;

public class MModdingInitializer implements ModInitializer {

	public static final Map<String, ElementsManager> MANAGERS = new HashMap<>();

	@Override
	public void onInitialize(ModContainer factory) {
		RegistryEvents.DYNAMIC_REGISTRY_SETUP.register(MModdingInitializer::dynamicRegistrySetup);
	}

	private static void dynamicRegistrySetup(DynamicRegistryManagerSetupContext context) {
		MANAGERS.forEach((modId, manager) -> MModdingInitializer.managerSetup(context, modId, manager));
	}

	private static void managerSetup(DynamicRegistryManagerSetupContext context, String modId, ElementsManager manager) {
		if (manager.sealed) return;
		Set<RegistryKey<? extends Registry<?>>> requiredRegistryKeys = new HashSet<>();
		manager.dynamics.forEach(pair -> requiredRegistryKeys.addAll(Arrays.stream(pair.getKey().getRequiredKeys()).toList()));
		context.withRegistries(map -> {
			manager.initDynamicContent(modId, map.registries());
			manager.sealed = true;
		}, requiredRegistryKeys);
	}
}
