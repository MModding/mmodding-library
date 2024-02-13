package com.mmodding.library.test;

import com.mmodding.library.container.AdvancedContainer;
import com.mmodding.library.initializer.ExtendedModInitializer;
import com.mmodding.library.registry.ElementsManager;
import com.mmodding.library.registry.RegistrableProvider;
import com.mmodding.library.registry.context.DoubleRegistryContext;
import com.mmodding.library.registry.context.SimpleRegistryContext;

public class MModdingTestImplementation implements ExtendedModInitializer {

	@Override
	public void setupManager(ElementsManager.Builder manager) {
		manager
			.ifModLoadedWith("string", SimpleRegistryContext.ITEM, RegistrableProvider.simple((registry, mod) -> null))
			.withRegistry(DoubleRegistryContext.FEATURE, RegistrableProvider.bi(MModdingTestFeatures::new))
			.withDefaults(MModdingTestBlocks::new);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {
		// ...
	}
}
