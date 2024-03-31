package com.mmodding.library.test;

import com.mmodding.library.container.api.AdvancedContainer;
import com.mmodding.library.core.api.MModdingRegistries;
import com.mmodding.library.initializer.ExtendedModInitializer;
import com.mmodding.library.registry.api.ElementsManager;
import com.mmodding.library.registry.api.context.DoubleRegistryContext;
import net.minecraft.world.World;

public class MModdingTests implements ExtendedModInitializer {

	@Override
	public void setupManager(ElementsManager.Builder manager) {
		manager
			.withRegistry(DoubleRegistryContext.FEATURE, FeatureTests::new)
			.withDefaults(VeinTypeTests::new, BlockTests::new, RegistryTests::new);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {
		MModdingRegistries.DIFFERED_SEED.put(World.OVERWORLD, true);
	}
}
