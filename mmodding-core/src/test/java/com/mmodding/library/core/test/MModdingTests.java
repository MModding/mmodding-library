package com.mmodding.library.core.test;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.initializer.ExtendedModInitializer;
import com.mmodding.library.core.api.management.ElementsManager;
import net.minecraft.registry.RegistryKeys;

public class MModdingTests implements ExtendedModInitializer {

	@Override
	public void setupManager(ElementsManager.Builder manager) {
		manager.content(RegistryTests::register);
		manager.data(RegistryKeys.BIOME, DataTests::register);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {
		// MModdingRegistries.DIFFERED_SEED.put(World.OVERWORLD, true);
	}
}
