package com.mmodding.mmodding_lib.library.worldgen;

import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ChunkGeneratorRegistration {

	private static final List<Pair<Identifier, ChunkGeneratorSettings>> chunkGeneratorSettings = new ArrayList<>();

	public static void register(Identifier identifier, ChunkGeneratorSettings chunkGeneratorSettings) {
		ChunkGeneratorRegistration.chunkGeneratorSettings.add(new Pair<>(identifier, chunkGeneratorSettings));
	}

	public static void forEachGenerator(Consumer<? super Pair<Identifier, ChunkGeneratorSettings>> consumer) {
		ChunkGeneratorRegistration.chunkGeneratorSettings.forEach(consumer);
	}
}
