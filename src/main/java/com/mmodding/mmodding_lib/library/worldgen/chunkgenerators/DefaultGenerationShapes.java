package com.mmodding.mmodding_lib.library.worldgen.chunkgenerators;

import net.minecraft.world.gen.chunk.GenerationShapeConfig;

public class DefaultGenerationShapes {

	public static final GenerationShapeConfig OVERWORLD = GenerationShapeConfig.create(-64, 384, 1, 2);
	public static final GenerationShapeConfig NETHER = GenerationShapeConfig.create(0, 128, 1, 2);
	public static final GenerationShapeConfig END = GenerationShapeConfig.create(0, 128, 2, 1);
	public static final GenerationShapeConfig CAVES = GenerationShapeConfig.create(-64, 192, 1, 2);
	public static final GenerationShapeConfig FLOATING_ISLANDS = GenerationShapeConfig.create(0, 256, 2, 1);
}
