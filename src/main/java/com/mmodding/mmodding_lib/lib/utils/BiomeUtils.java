package com.mmodding.mmodding_lib.lib.utils;

import net.minecraft.util.Holder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkSection;

public class BiomeUtils {

	public static void changeBiomeForBlock(BlockPos pos, ChunkSection section, Biome biome) {
		section.getBiomeContainer().set(pos.getX(), pos.getY(), pos.getZ(), Holder.createDirect(biome));
	}
}
