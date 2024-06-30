package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.portals.squared.CustomSquaredPortal;
import com.mmodding.mmodding_lib.library.utils.InternalOf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.border.WorldBorder;

import java.util.Optional;

@InternalOf(CustomSquaredPortal.class)
public interface ServerPlayerDuckInterface {

	Optional<BlockLocating.Rectangle> mmodding_lib$getCustomPortalRect(ServerWorld destWorld, BlockPos destPos, WorldBorder worldBorder);
}
