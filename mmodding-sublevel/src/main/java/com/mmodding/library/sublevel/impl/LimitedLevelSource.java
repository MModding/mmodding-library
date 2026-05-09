package com.mmodding.library.sublevel.impl;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.List;
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
	public void applyCarvers(WorldGenRegion region, long seed, RandomState randomState, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunk) {
		if (this.isInBounds(chunk)) {
			this.delegate.applyCarvers(region, seed, randomState, biomeManager, structureManager, chunk);
		}
	}

	@Override
	public void buildSurface(WorldGenRegion level, StructureManager structureManager, RandomState randomState, ChunkAccess protoChunk) {
		if (this.isInBounds(protoChunk)) {
			this.delegate.buildSurface(level, structureManager, randomState, protoChunk);
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
	public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess centerChunk) {
		return this.isInBounds(centerChunk) ? this.delegate.fillFromNoise(blender, randomState, structureManager, centerChunk) : CompletableFuture.completedFuture(centerChunk);
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
	public void addDebugScreenInfo(List<String> result, RandomState randomState, BlockPos feetPos) {
		this.delegate.addDebugScreenInfo(result, randomState, feetPos);
	}

	public ChunkGenerator getDelegate() {
		return this.delegate;
	}
}
