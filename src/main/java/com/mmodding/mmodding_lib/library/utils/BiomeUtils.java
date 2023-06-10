package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.mixin.accessors.ChunkSectionAccessor;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.palette.PalettedContainer;

public class BiomeUtils {

	public static void changeBiomeForBlock(WorldAccess world, BlockPos pos, RegistryKey<Biome> biomeKey) {
		Chunk chunk = world.getChunk(pos);
		ChunkSection section = chunk.getSection(chunk.getSectionIndex(pos.getY()));
		PalettedContainer<Holder<Biome>> biomeContainer = section.getBiomeContainer().m_enhkzepj();
		biomeContainer.setUnsafe(pos.getX() & 3, pos.getY() & 3, pos.getZ() & 3, BiomeUtils.getBiomeHolder(biomeKey));
		((ChunkSectionAccessor) section).setBiomeContainer(biomeContainer);
		chunk.setNeedsSaving(true);
	}

	public static Biome getBiome(WorldAccess world, Identifier identifier) {
		return world.getRegistryManager().get(Registry.BIOME_KEY).get(identifier);
	}

	public static Holder<Biome> getBiomeHolder(RegistryKey<Biome> biomeKey) {
		return BuiltinRegistries.BIOME.getHolderOrThrow(biomeKey);
	}

	public static RegistryKey<Biome> getBiomeKey(Identifier identifier) {
		return RegistryKey.of(Registry.BIOME_KEY, identifier);
	}
}
