package com.mmodding.mmodding_lib.lib.utils;

import net.minecraft.util.Holder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;

public class BiomeUtils {

	public static void changeBiomeForBlock(World world, BlockPos pos, ChunkSection section, Biome biome) {
		Chunk chunk = world.getChunk(pos);
		HeightLimitView heightLimitView = chunk.getHeightLimitView();

		for (int k = heightLimitView.getBottomSectionCoord(); k < heightLimitView.getTopSectionCoord(); k++) {
			ChunkSection chunkSection = chunk.getSection(chunk.sectionCoordToIndex(k));
			chunkSection.getBiomeContainer().set(pos.getX(), pos.getY(), pos.getZ(), Holder.createDirect(biome));
		}
	}
}
