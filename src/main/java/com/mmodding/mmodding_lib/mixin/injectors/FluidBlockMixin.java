package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.fluids.FluidExtensions;
import com.mmodding.mmodding_lib.library.fluids.collisions.FluidCollisionHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidBlock.class)
public class FluidBlockMixin {

    @Shadow
    @Final
    protected FlowableFluid fluid;

    @Inject(method = "receiveNeighborFluids", at = @At("HEAD"), cancellable = true)
    private void receiveNeighborFluids(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (this.fluid instanceof FluidExtensions extensions) {
            FluidCollisionHandler handler = extensions.getCollisionHandler();
            for (Direction direction : FluidBlock.FLOW_DIRECTIONS) {
                BlockPos blockPos = pos.offset(direction.getOpposite());
                BlockState blockState = handler.getCollisionResult(world, pos, state, blockPos, world.getBlockState(blockPos));
                if (blockState.isAir()) {
                    BlockState fluidState = handler.getCollisionResult(world, pos, state, blockPos, world.getFluidState(blockPos));
                    if (!fluidState.isAir()) {
                        world.setBlockState(pos, fluidState);
                        handler.afterCollision(world, pos, state, blockPos, world.getFluidState(blockPos));
                        cir.setReturnValue(false);
                    }
                }
                else {
                    world.setBlockState(pos, blockState);
                    handler.afterCollision(world, pos, state, blockPos, world.getBlockState(blockPos));
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
