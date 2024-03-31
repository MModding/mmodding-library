package com.mmodding.library.core.test;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.initializer.ExtendedModInitializer;
import com.mmodding.library.core.api.management.ElementsManager;

public class MModdingTests implements ExtendedModInitializer {

	@Override
	public void setupManager(ElementsManager.Builder manager) {
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {
		// MModdingRegistries.DIFFERED_SEED.put(World.OVERWORLD, true);
	}
}
