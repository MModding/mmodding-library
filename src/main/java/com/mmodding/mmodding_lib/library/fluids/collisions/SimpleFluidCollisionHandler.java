package com.mmodding.mmodding_lib.library.fluids.collisions;

import com.mmodding.mmodding_lib.library.utils.BiHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class SimpleFluidCollisionHandler implements FluidCollisionHandler {

    private final BiHashMap<BlockState, BiFunction<WorldAccess, BlockState, BlockState>, BiConsumer<WorldAccess, BlockState>> blockHandlers = new BiHashMap<>();
    private final BiHashMap<FluidState, BiFunction<WorldAccess, FluidState, BlockState>, BiConsumer<WorldAccess, FluidState>> fluidHandlers = new BiHashMap<>();

    private BlockState blockCache;
    private FluidState fluidCache;


    /**
     * @apiNote Instantiable at {@link FluidCollisionHandler#ofSimple()}
     */
    SimpleFluidCollisionHandler() {}

    public SimpleFluidCollisionHandler addHandling(BlockState collided, BiFunction<WorldAccess, BlockState, BlockState> result, BiConsumer<WorldAccess, BlockState> after) {
        this.blockHandlers.put(collided, result, after);
        return this;
    }

    public SimpleFluidCollisionHandler addHandling(FluidState collided, BiFunction<WorldAccess, FluidState, BlockState> result, BiConsumer<WorldAccess, FluidState> after) {
        this.fluidHandlers.put(collided, result, after);
        return this;
    }

    @Override
    public BlockState getCollisionResult(WorldAccess world, BlockPos originPos, BlockState originFluid, BlockPos collidedPos, BlockState collidedBlock) {
        AtomicReference<BlockState> state = new AtomicReference<>(Blocks.AIR.getDefaultState());
        this.blockHandlers.forEachFirst((collided, result) -> {
            if (!result.apply(world, collided).isAir()) {
                state.set(result.apply(world, collided));
                this.blockCache = collided;
            }
        });
        return state.get();
    }

    @Override
    public void afterCollision(WorldAccess world, BlockPos originPos, BlockState originFluid, BlockPos collidedPos, FluidState collidedFluid) {
        this.blockHandlers.getSecondValue(this.blockCache).accept(world, this.blockCache);
        this.blockCache = null;
    }

    @Nullable
    @Override
    public BlockState getCollisionResult(WorldAccess world, BlockPos originPos, BlockState originFluid, BlockPos collidedPos, FluidState collidedFluid) {
        AtomicReference<BlockState> state = new AtomicReference<>(Blocks.AIR.getDefaultState());
        this.fluidHandlers.forEachFirst((collided, result) -> {
            if (!result.apply(world, collided).isAir()) {
                state.set(result.apply(world, collided));
                this.fluidCache = collided;
            }
        });
        return state.get();
    }

    @Override
    public void afterCollision(WorldAccess world, BlockPos originPos, BlockState originFluid, BlockPos collidedPos, BlockState collidedBlock) {
        this.fluidHandlers.getSecondValue(this.fluidCache).accept(world, this.fluidCache);
        this.fluidCache = null;
    }
}
