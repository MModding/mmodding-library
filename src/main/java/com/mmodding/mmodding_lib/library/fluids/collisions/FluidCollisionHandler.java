package com.mmodding.mmodding_lib.library.fluids.collisions;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public interface FluidCollisionHandler {

    static SimpleFluidCollisionHandler ofSimple() {
        return new SimpleFluidCollisionHandler();
    }

    BlockState getCollisionResult(WorldAccess world, BlockPos originPos, BlockState originFluid, BlockPos collidedPos, BlockState collidedBlock);

    void afterCollision(WorldAccess world, BlockPos originPos, BlockState originFluid, BlockPos collidedPos, FluidState collidedFluid);

    BlockState getCollisionResult(WorldAccess world, BlockPos originPos, BlockState originFluid, BlockPos collidedPos, FluidState collidedFluid);

    void afterCollision(WorldAccess world, BlockPos originPos, BlockState originFluid, BlockPos collidedPos, BlockState collidedBlock);
}
