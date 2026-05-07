package com.mmodding.library.sublevel.api;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.dimension.LevelStem;

public class Sublevels {

	public static SublevelType<ChunkPos> createPositionAttachedType(ResourceKey<LevelStem> stem, int chunkSquareRadius) {
		return SublevelType.create(pos -> "chunk_" + pos.x() + "-" + pos.z(), stem, chunkSquareRadius);
	}

	public static SublevelType<Player> createPlayerAttachedType(ResourceKey<LevelStem> stem, int chunkSquareRadius) {
		return SublevelType.create(player -> "player_" + player.getStringUUID(), stem, chunkSquareRadius);
	}
}
