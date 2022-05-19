package com.mmodding.mmodding_lib.lib.utils;

import net.minecraft.util.Holder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;

public class BiomeUtils {

	public static void changeBiomeForBlock(World world, BlockPos pos, Biome biome) {
		Chunk chunk = world.getChunk(pos);
		ChunkSectionPos sectionPos = ChunkSectionPos.from(new ChunkPos(pos), ChunkSectionPos.getSectionCoord(pos.getY()));

		ChunkSection chunkSection = chunk.getSection(chunk.getSectionIndex(pos.getY()));
		chunkSection.getBiomeContainer().setSync(sectionPos.getX(),	sectionPos.getY(), sectionPos.getZ(), Holder.createDirect(biome));
	}
}
