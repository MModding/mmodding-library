package com.mmodding.mmodding_lib.library.events;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.quiltmc.qsl.base.api.event.Event;

public class VanillaFluidCollisionEvents {

    /**
     * Allows replacing the {@code Blocks.STONE.getDefaultState()} value in {@link LavaFluid#flow(WorldAccess, BlockPos, BlockState, Direction, FluidState)}
     */
    public static final Event<StoneGenerationCallback> STONE_GENERATION_CALLBACK = Event.create(StoneGenerationCallback.class, callbacks -> (world, pos, blockState, direction, fluidState) -> {
        for (StoneGenerationCallback callback : callbacks) {
            BlockState state = callback.apply(world, pos, blockState, direction, fluidState);
            if (state != null) {
                return state;
            }
        }
        return null;
    });

    /**
     * Allows replacing the {@code Blocks.COBBLESTONE} value in {@link FluidBlock#receiveNeighborFluids(World, BlockPos, BlockState)}
     */
    public static final Event<CobblestoneGenerationCallback> COBBLESTONE_GENERATION_CALLBACK = Event.create(CobblestoneGenerationCallback.class, callbacks -> (world, pos, state, direction, offsetPos) -> {
        for (CobblestoneGenerationCallback callback : callbacks) {
            Block block = callback.apply(world, pos, state, direction, offsetPos);
            if (block != null) {
                return block;
            }
        }
        return null;
    });

    /**
     * Allows replacing the {@code Blocks.OBSIDIAN} value in {@link FluidBlock#receiveNeighborFluids(World, BlockPos, BlockState)}
     */
    public static final Event<ObsidianGenerationCallback> OBSIDIAN_GENERATION_CALLBACK = Event.create(ObsidianGenerationCallback.class, callbacks -> (world, pos, state, direction, offsetPos) -> {
        for (ObsidianGenerationCallback callback : callbacks) {
            Block block = callback.apply(world, pos, state, direction, offsetPos);
            if (block != null) {
                return block;
            }
        }
        return null;
    });

    /**
     * Allows replacing the {@code Blocks.BASALT.getDefaultState()} value in {@link FluidBlock#receiveNeighborFluids(World, BlockPos, BlockState)}
     */
    public static final Event<BasaltGenerationCallback> BASALT_GENERATION_CALLBACK = Event.create(BasaltGenerationCallback.class, callbacks -> (world, pos, blockState, direction, offsetPos) -> {
        for (BasaltGenerationCallback callback : callbacks) {
            BlockState state = callback.apply(world, pos, blockState, direction, offsetPos);
            if (state != null) {
                return state;
            }
        }
        return null;
    });

    @FunctionalInterface
    public interface StoneGenerationCallback {

        BlockState apply(WorldAccess world, BlockPos pos, BlockState blockState, Direction direction, FluidState fluidState);
    }

    @FunctionalInterface
    public interface CobblestoneGenerationCallback {

        Block apply(World world, BlockPos pos, BlockState state, Direction direction, BlockPos offsetPos);
    }

    @FunctionalInterface
    public interface ObsidianGenerationCallback {

        Block apply(World world, BlockPos pos, BlockState state, Direction direction, BlockPos offsetPos);
    }

    @FunctionalInterface
    public interface BasaltGenerationCallback {

        BlockState apply(World world, BlockPos pos, BlockState state, Direction direction, BlockPos offsetPos);
    }
}
