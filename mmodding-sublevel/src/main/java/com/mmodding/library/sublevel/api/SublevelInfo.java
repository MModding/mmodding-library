package com.mmodding.library.sublevel.api;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;

/**
 * Sublevels are making use of the level seed to generate themselves. If they were generating at the center, it would
 * lead to exact same positions everytime. This is why, if toggled, the center chunk will be determined from the
 * attachment hash code.
 * @param sublevel the sublevel
 * @param centerChunkPos the center chunk pos
 * @param centerBlockPos the center block pos
 */
public record SublevelInfo(ServerLevel sublevel, ChunkPos centerChunkPos, BlockPos centerBlockPos) {
}
