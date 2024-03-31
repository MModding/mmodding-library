package com.mmodding.library.test;

import com.mmodding.library.block.api.util.RandomStateContainer;
import com.mmodding.library.container.api.AdvancedContainer;
import com.mmodding.library.core.api.MModdingRegistries;
import com.mmodding.library.initializer.ExtendedModInitializer;
import com.mmodding.library.registry.api.ElementsManager;
import com.mmodding.library.registry.api.ContentHolderProvider;
import com.mmodding.library.registry.api.context.DoubleRegistryContext;
import com.mmodding.library.worldgen.vein.api.VeinType;
import net.minecraft.block.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

public class MModdingTests implements ExtendedModInitializer {

	@Override
	public void setupManager(ElementsManager.Builder manager) {
		manager
			.withRegistry(DoubleRegistryContext.FEATURE, ContentHolderProvider.bi(FeatureTests::new))
			.withDefaults(BlockTests::new, RegistryTests::new);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {
		MModdingRegistries.VEIN_TYPE.getOrCreateCompanion(ChunkGeneratorSettings.OVERWORLD).execute(registry -> {
			registry.register(mod.createId("hi"), new VeinType.Builder(
				-32, 32,
				RandomStateContainer.create(Blocks.AIR),
				RandomStateContainer.create(Blocks.AIR),
				RandomStateContainer.create(Blocks.AIR)
			).build());
			registry.register(mod.createId("ih"), new VeinType.Builder(
				-16, 16,
				RandomStateContainer.create(Blocks.BEDROCK),
				RandomStateContainer.create(Blocks.BEDROCK),
				RandomStateContainer.create(Blocks.BEDROCK)
			).build());
		});
		MModdingRegistries.DIFFERED_SEED.put(World.OVERWORLD, true);
	}
}
