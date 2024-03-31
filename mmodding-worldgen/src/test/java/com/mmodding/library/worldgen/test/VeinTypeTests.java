package com.mmodding.library.worldgen.test;

import com.mmodding.library.block.api.util.RandomStateContainer;
import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.content.DefaultContentHolder;
import com.mmodding.library.worldgen.api.vein.VeinType;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

public class VeinTypeTests implements DefaultContentHolder {

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
	public void register(AdvancedContainer mod) {
		mod.withRegistry(VeinType.REGISTRY.getOrCreateCompanion(ChunkGeneratorSettings.OVERWORLD)).execute(init -> {
			VeinTypeTests.FIRST_TYPE.register(init.createId("hi"));
			VeinTypeTests.SECOND_TYPE.register(init.createId("ih"));
		});
	}
}
