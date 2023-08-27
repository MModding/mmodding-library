package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.fluids.FluidExtensions;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidState.class)
public abstract class FluidStateMixin {

    @Shadow
    public abstract Fluid getFluid();

    @Inject(method = "getVelocity", at = @At("HEAD"), cancellable = true)
    private void getVelocity(BlockView world, BlockPos pos, CallbackInfoReturnable<Vec3d> cir) {
        if (this.getFluid() instanceof FluidExtensions extensions) {
            cir.setReturnValue(cir.getReturnValue().multiply(extensions.getVelocityMultiplier()));
        }
    }
}
