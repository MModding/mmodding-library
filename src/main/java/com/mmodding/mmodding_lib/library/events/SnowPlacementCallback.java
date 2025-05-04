package com.mmodding.mmodding_lib.library.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;

@FunctionalInterface
public interface SnowPlacementCallback {

    /**
     * Allows replacing the {@code Blocks.SNOW.getDefaultState()} value in {@link ServerWorld#tickChunk(WorldChunk, int)}}
     */
	Event<SnowPlacementCallback> EVENT = EventFactory.createArrayBacked(SnowPlacementCallback.class, callbacks -> (world, pos, state) -> {
        for (SnowPlacementCallback callback : callbacks) {
            BlockState blockState = callback.apply(world, pos, state);
            if (blockState != null) {
                return blockState;
            }
        }
        return state; // Defaults to Blocks.SNOW
    });

    BlockState apply(ServerWorld world, BlockPos pos, BlockState blockState);
}
