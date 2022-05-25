package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class BiomeUtils {

	public static void changeBiomeForBlock(WorldAccess world, BlockPos pos, Biome biome) {
		Chunk chunk = world.getChunk(pos);
		chunk.getSection(chunk.getSectionIndex(pos.getY())).getBiomeContainer().setUnsafe(
				pos.getX() & 3, pos.getY() & 3, pos.getZ() & 3, Holder.createDirect(biome)
		);
		chunk.setNeedsSaving(true);
	}

	public static Biome getBiome(WorldAccess world, Identifier identifier) {
		return world.getRegistryManager().get(Registry.BIOME_KEY).get(identifier);
	}
}
