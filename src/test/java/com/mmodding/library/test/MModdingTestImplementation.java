package com.mmodding.library.test;

import com.mmodding.library.container.api.AdvancedContainer;
import com.mmodding.library.core.api.MModdingRegistries;
import com.mmodding.library.initializer.ExtendedModInitializer;
import com.mmodding.library.registry.api.ContentHolderProvider;
import com.mmodding.library.registry.api.ElementsManager;
import com.mmodding.library.registry.api.context.DoubleRegistryContext;
import net.minecraft.world.World;

public class MModdingTestImplementation implements ExtendedModInitializer {

	@Override
	public void setupManager(ElementsManager.Builder manager) {
		manager
			.withRegistry(DoubleRegistryContext.FEATURE, MModdingTestFeatures::new)
			.withDefaults(
				MModdingTestVeinTypes::new,
				MModdingTestBlocks::new,
				RegistryExperiments::new
			);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {
		MModdingRegistries.DIFFERED_SEED.put(World.OVERWORLD, true);
	}
}
