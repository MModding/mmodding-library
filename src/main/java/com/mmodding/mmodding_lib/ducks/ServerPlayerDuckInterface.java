package com.mmodding.mmodding_lib.ducks;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.border.WorldBorder;

import java.util.Optional;

public interface ServerPlayerDuckInterface {

	Optional<BlockLocating.Rectangle> getCustomPortalRect(ServerWorld destWorld, BlockPos destPos, WorldBorder worldBorder);
}
