package com.mmodding.library;

import com.mmodding.library.container.AdvancedContainer;
import com.mmodding.library.registry.ElementsManager;
import com.mmodding.library.registry.RegistrableProvider;
import com.mmodding.library.registry.context.SimpleRegistryContext;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.registry.api.event.DynamicRegistryManagerSetupContext;
import org.quiltmc.qsl.registry.api.event.RegistryEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MModdingExperiments implements ModInitializer {

	public static final Map<String, ElementsManager> MANAGERS = new HashMap<>();

	public static final Logger LOGGER = LoggerFactory.getLogger("Quilt Experiments");

	@Override
	public void onInitialize(ModContainer factory) {
		RegistryEvents.DYNAMIC_REGISTRY_SETUP.register(MModdingExperiments::dynamicRegistrySetup);
		AdvancedContainer mod = AdvancedContainer.of(factory);
		LOGGER.info("Hello MModding Testing World from {}!", mod.metadata().name());
		ElementsManager.Builder builder = ElementsManager.Builder.common();
		builder.withRegistry(SimpleRegistryContext.ITEM, RegistrableProvider.simple(RegistryExperiments::new));
		MANAGERS.put(factory.metadata().id(), builder.build());
	}

	private static void dynamicRegistrySetup(DynamicRegistryManagerSetupContext context) {
		MANAGERS.forEach((modId, manager) -> MModdingExperiments.managerSetup(context, modId, manager));
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

	public static String id() {
		return "mmodding_experiments";
	}

	public static Identifier createId(String path) {
		return new Identifier(MModdingExperiments.id(), path);
	}
}
