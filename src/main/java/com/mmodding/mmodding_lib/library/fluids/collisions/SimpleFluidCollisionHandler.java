package com.mmodding.mmodding_lib.library.fluids.collisions;

import com.mmodding.mmodding_lib.library.utils.BiHashMap;
import com.mmodding.mmodding_lib.library.utils.BiMap;
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

    private final BiMap<BlockState, BiFunction<WorldAccess, BlockState, BlockState>, BiConsumer<WorldAccess, BlockState>> blockHandlers = new BiHashMap<>();
    private final BiMap<FluidState, BiFunction<WorldAccess, FluidState, BlockState>, BiConsumer<WorldAccess, FluidState>> fluidHandlers = new BiHashMap<>();

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
        this.blockHandlers.forEach((collided, result, after) -> {
            if (collidedBlock.isOf(collided.getBlock())) {
                state.set(result.apply(world, collidedBlock));
				after.accept(world, collidedBlock);
            }
        });
        return state.get();
    }

    @Override
    public void afterCollision(WorldAccess world, BlockPos originPos, BlockState originFluid, BlockPos collidedPos, FluidState collidedFluid) {}

    @Nullable
    @Override
    public BlockState getCollisionResult(WorldAccess world, BlockPos originPos, BlockState originFluid, BlockPos collidedPos, FluidState collidedFluid) {
        AtomicReference<BlockState> state = new AtomicReference<>(Blocks.AIR.getDefaultState());
        this.fluidHandlers.forEach((collided, result, after) -> {
            if (collidedFluid.isOf(collided.getFluid())) {
                state.set(result.apply(world, collidedFluid));
				after.accept(world, collidedFluid);
            }
        });
        return state.get();
    }

    @Override
    public void afterCollision(WorldAccess world, BlockPos originPos, BlockState originFluid, BlockPos collidedPos, BlockState collidedBlock) {}
}
