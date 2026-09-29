package com.mmodding.library.sublevel.impl;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.densityfunction.SamplerContext;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LimitedLevelSource extends ChunkGenerator {

	private final ChunkGenerator delegate;
	private final int squareChunkRadius;
	private final ChunkPos centerPos;

	public LimitedLevelSource(ChunkGenerator delegate, int squareChunkRadius, ChunkPos centerPos) {
		super(delegate.getBiomeSource(), delegate.generationSettingsGetter);
		this.delegate = delegate;
		this.squareChunkRadius = squareChunkRadius;
		this.centerPos = centerPos;
	}

	private boolean isInBounds(ChunkAccess chunk) {
		int x = chunk.getPos().x() - this.centerPos.x(); if (x > 0) x += 1;
		int z = chunk.getPos().z() - this.centerPos.z(); if (z > 0) z += 1;
		return Math.abs(x) <= this.squareChunkRadius && Math.abs(z) <= this.squareChunkRadius;
	}

	@Override
	public MapCodec<? extends ChunkGenerator> codec() {
		return this.delegate.codec();
	}

	@Override
	public void applyBiomeDecoration(WorldGenLevel level, ChunkAccess chunk, StructureManager structureManager) {
		if (this.isInBounds(chunk)) {
			super.applyBiomeDecoration(level, chunk, structureManager);
		}
	}

	@Override
	public CompletableFuture<ChunkAccess> buildTerrain(ChunkAccess chunk, Blender blender, RandomState randomState, StructureManager structureManager, BiomeManager biomeManager, @Nullable WorldGenRegion carverBiomeRegion, Set<Holder<Biome>> possibleBiomes) {
		if (this.isInBounds(chunk)) {
			return this.delegate.buildTerrain(chunk, blender, randomState, structureManager, biomeManager, carverBiomeRegion, possibleBiomes);
		}
		else {
			return CompletableFuture.completedFuture(chunk);
		}
	}

	@Override
	public void spawnOriginalMobs(WorldGenRegion worldGenRegion) {
		this.delegate.spawnOriginalMobs(worldGenRegion);
	}

	@Override
	public int getGenDepth() {
		return this.delegate.getGenDepth();
	}

	@Override
	public boolean tryGenerateStructure(final StructureSet.StructureSelectionEntry selected, final StructureManager structureManager, final RegistryAccess registryAccess, final RandomState randomState, final StructureTemplateManager structureTemplateManager, final long seed, final ChunkAccess centerChunk, final ChunkPos sourceChunkPos, final ResourceKey<Level> level, final Climate.Sampler climateSampler) {
		if (this.isInBounds(centerChunk)) {
			return this.tryGenerateStructure(selected, structureManager, registryAccess, randomState, structureTemplateManager, seed, centerChunk, sourceChunkPos, level, climateSampler);
		}
		else {
			return false;
		}
	}

	@Override
	public int getSeaLevel() {
		return this.delegate.getSeaLevel();
	}

	@Override
	public int getMinY() {
		return this.delegate.getMinY();
	}

	@Override
	public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor heightAccessor, RandomState randomState) {
		return this.delegate.getBaseHeight(x, z, type, heightAccessor, randomState);
	}

	@Override
	public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor heightAccessor, RandomState randomState) {
		return this.delegate.getBaseColumn(x, z, heightAccessor, randomState);
	}

	@Override
	public void addDebugScreenInfo(List<String> result, RandomState randomState, BlockPos feetPos, SamplerContext samplerContext) {
		this.delegate.addDebugScreenInfo(result, randomState, feetPos, samplerContext);
	}

	public ChunkGenerator getDelegate() {
		return this.delegate;
	}
}
