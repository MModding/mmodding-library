package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.interface_injections.FluidGroupComparable;
import com.mmodding.mmodding_lib.library.fluids.FluidExtensions;
import com.mmodding.mmodding_lib.library.fluids.FluidGroup;
import com.mmodding.mmodding_lib.mixin.accessors.FluidAccessor;
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
public abstract class FluidStateMixin implements FluidGroupComparable {

    @Shadow
    public abstract Fluid getFluid();

    @Inject(method = "getVelocity", at = @At("HEAD"), cancellable = true)
    private void getVelocity(BlockView world, BlockPos pos, CallbackInfoReturnable<Vec3d> cir) {
        if (this.getFluid() instanceof FluidExtensions extensions) {
            cir.setReturnValue(((FluidAccessor) this.getFluid()).getVelocity(world, pos, (FluidState) (Object) this).multiply(extensions.getVelocityMultiplier()));
        }
    }

	@Override
	public boolean isOf(FluidGroup group) {
		return this.getFluid() == group.getStill() || this.getFluid() == group.getFlowing();
	}
}
