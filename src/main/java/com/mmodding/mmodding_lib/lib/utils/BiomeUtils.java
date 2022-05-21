package com.mmodding.mmodding_lib.lib.utils;

import net.minecraft.util.Holder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class BiomeUtils {

	public static void changeBiomeForBlock(World world, BlockPos pos, Biome biome) {
		Chunk chunk = world.getChunk(pos);
		chunk.getSection(chunk.getSectionIndex(pos.getY())).getBiomeContainer().setUnsafe(
				pos.getX() & 3, pos.getY() & 3, pos.getZ() & 3, Holder.createDirect(biome)
		);
		chunk.setNeedsSaving(true);
	}
}
