package com.mmodding.library.worldgen.test;

import com.mmodding.library.block.api.util.RandomStateContainer;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.ExtendedModInitializer;
import com.mmodding.library.worldgen.api.vein.VeinType;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

public class VeinTypeTests implements ExtendedModInitializer {

	public static final VeinType FIRST_TYPE = new VeinType.Builder(
		-32, 32,
		RandomStateContainer.create(Blocks.AIR),
		RandomStateContainer.create(Blocks.AIR),
		RandomStateContainer.create(Blocks.AIR)
	).build();

	public static final VeinType SECOND_TYPE = new VeinType.Builder(
		-16, 16,
		RandomStateContainer.create(Blocks.BEDROCK),
		RandomStateContainer.create(Blocks.BEDROCK),
		RandomStateContainer.create(Blocks.BEDROCK)
	).build();

	@Override
	public void setupManager(ElementsManager.Builder manager) {
		manager.content(VeinTypeTests::register);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {}

	public static void register(AdvancedContainer mod) {
		VeinType.REGISTRY.getOrCreateCompanion(ChunkGeneratorSettings.OVERWORLD).register(mod.getMetadata().getId(), factory -> {
			factory.register("hi", VeinTypeTests.FIRST_TYPE);
			factory.register("ih", VeinTypeTests.SECOND_TYPE);
		});
	}
}
