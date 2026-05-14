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
	private final ChunkPos centerPos;

	public ServerSublevel(SublevelType<T> type, String mappedAttachment, ServerLevel parent) {
		this.type = type;
		this.mappedAttachment = mappedAttachment;
		MinecraftServer server = parent.getServer();
		ChunkPos centerPos;
		if (type.alwaysOrigin()) {
			centerPos = ChunkPos.ZERO;
		}
		else {
			int seed = (int) server.getWorldGenSettings().options().seed();
			int x = (mappedAttachment.hashCode() * seed) % (server.getAbsoluteMaxWorldSize() / 16);
			int z = String.valueOf(x).hashCode() % (server.getAbsoluteMaxWorldSize() / 16);
			centerPos = new ChunkPos(x, z);
		}
		this.centerPos = centerPos;
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
		// "absoluteMaxSize" is misnamed, it clamps the world border area
		// to an area centered at the origin and of the given size
		// so we just set the normal size instead of the "clamp"
		this.getWorldBorder().setAbsoluteMaxSize(server.getAbsoluteMaxWorldSize());
		server.getPlayerList().addWorldborderListener(this);
		this.getWorldBorder().setSize(type.chunkSquareRadius() * 16 * 2); // size needs the diameter, we have the radius
		this.getWorldBorder().setCenter(this.centerPos.getWorldPosition().getX(), this.centerPos.getWorldPosition().getZ());
	}

	private boolean isInBounds(ChunkPos pos) {
		int x = pos.x() - this.centerPos.x(); if (x > 0) x += 1;
		int z = pos.z() - this.centerPos.z(); if (z > 0) z += 1;
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

	public final ChunkPos getCenterPos() {
		return this.centerPos;
	}
}
