package com.mmodding.mmodding_lib.library.events;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.WorldChunk;
import org.quiltmc.qsl.base.api.event.Event;

@FunctionalInterface
public interface SnowPlacementCallback {

    /**
     * Allows replacing the {@code Blocks.SNOW.getDefaultState()} value in {@link ServerWorld#tickChunk(WorldChunk, int)}}
     */
	Event<SnowPlacementCallback> EVENT = Event.create(SnowPlacementCallback.class, callbacks -> (world, pos, state) -> {
        for (SnowPlacementCallback callback : callbacks) {
            BlockState blockState = callback.apply(world, pos, state);
            if (blockState != null) {
                return blockState;
            }
        }
        return state; // Defaults to Blocks.SNOW
    });

    BlockState apply(WorldAccess world, BlockPos pos, BlockState blockState);
}
