package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.mmodding_lib.library.events.VanillaFluidCollisionEvents;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LavaFluid.class)
public class LavaFluidMixin {

    @ModifyExpressionValue(method = "flow", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/BlockState;"))
    private BlockState flow(BlockState original, WorldAccess world, BlockPos pos, BlockState blockState, Direction direction, FluidState fluidState) {
        BlockState state = VanillaFluidCollisionEvents.STONE_GENERATION_CALLBACK.invoker().apply(world, pos, blockState, direction, fluidState);
        return state != null ? state : original;
    }
}
