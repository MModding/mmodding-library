package com.mmodding.library.sublevel.impl;

import com.mmodding.library.sublevel.api.SublevelType;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
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
			type.dimension(),
			parent.registryAccess().lookupOrThrow(Registries.LEVEL_STEM).getValueOrThrow(type.stem()),
			parent.isDebug(),
			parent.getBiomeManager().biomeZoomSeed,
			Collections.emptyList(),
			true
		);
		this.getWorldBorder().setAbsoluteMaxSize(type.chunkSquareRadius() * 16);
		server.getPlayerList().addWorldborderListener(this);
	}

	public final SublevelType<T> getType() {
		return this.type;
	}

	public final String getMappedAttachment() {
		return this.mappedAttachment;
	}
}
