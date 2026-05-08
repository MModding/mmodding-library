package com.mmodding.library.sublevel.impl;

import com.mmodding.library.sublevel.api.SublevelType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.ServerLevelData;

import java.util.Collections;
import java.util.Objects;

public class ServerSublevel<T> extends ServerLevel {

	private final SublevelType<T> type;
	private final String mappedAttachment;

	public ServerSublevel(SublevelType<T> type, String mappedAttachment, ServerLevel parent) {
		this.type = type;
		this.mappedAttachment = mappedAttachment;
		MinecraftServer server = parent.getServer();
		super(
			server,
			server.executor,
			server.storageSource,
			new DerivedLevelData(server.getWorldData(), (ServerLevelData) Objects.requireNonNull(server.getLevel(ServerSublevel.OVERWORLD)).getLevelData()),
			SublevelKey.of(type.dimension()),
			parent.registryAccess().lookupOrThrow(Registries.LEVEL_STEM).getValueOrThrow(type.stem()),
			parent.isDebug(),
			parent.getBiomeManager().biomeZoomSeed,
			Collections.emptyList(),
			true
		);
		this.getWorldBorder().setAbsoluteMaxSize(type.chunkSquareRadius() * 16);
		server.getPlayerList().addWorldborderListener(this);
	}

	private boolean isInBounds(ChunkPos pos) {
		int x = pos.x(); if (x > 0) x += 1;
		int z = pos.z(); if (z > 0) z += 1;
		return Math.abs(x) <= this.type.chunkSquareRadius() && Math.abs(z) <= this.type.chunkSquareRadius();
	}

	@Override
	public void tickChunk(LevelChunk chunk, int tickSpeed) {
		if (this.isInBounds(chunk.getPos())) {
			super.tickChunk(chunk, tickSpeed);
		}
	}

	@Override
	protected void tickFluid(BlockPos pos, Fluid type) {
		if (this.isInBounds(ChunkPos.containing(pos))) {
			super.tickFluid(pos, type);
		}
	}

	@Override
	protected void tickBlock(BlockPos pos, Block type) {
		if (this.isInBounds(ChunkPos.containing(pos))) {
			super.tickBlock(pos, type);
		}
	}

	public final SublevelType<T> getType() {
		return this.type;
	}

	public final String getMappedAttachment() {
		return this.mappedAttachment;
	}
}
