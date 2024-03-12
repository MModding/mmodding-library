package com.mmodding.library.test;

import com.mmodding.library.container.api.AdvancedContainer;
import com.mmodding.library.core.api.MModdingRegistries;
import com.mmodding.library.initializer.ExtendedModInitializer;
import com.mmodding.library.registry.api.ElementsManager;
import com.mmodding.library.registry.api.ContentHolderProvider;
import com.mmodding.library.registry.api.context.DoubleRegistryContext;
import com.mmodding.library.registry.api.context.SimpleRegistryContext;
import net.minecraft.world.World;

public class MModdingTestImplementation implements ExtendedModInitializer {

	@Override
	public void setupManager(ElementsManager.Builder manager) {
		manager
			.ifModLoadedWith("string", SimpleRegistryContext.ITEM, ContentHolderProvider.simple((registry, mod) -> null))
			.withRegistry(DoubleRegistryContext.FEATURE, ContentHolderProvider.bi(MModdingTestFeatures::new))
			.withDefaults(MModdingTestBlocks::new);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {
		MModdingRegistries.DIFFERED_SEED.put(World.OVERWORLD, true);
	}
}
