package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.interface_injections.FluidGroupComparable;
import com.mmodding.mmodding_lib.interface_injections.TagRuntimeManagement;
import com.mmodding.mmodding_lib.library.fluids.FluidExtensions;
import com.mmodding.mmodding_lib.library.fluids.FluidGroup;
import com.mmodding.mmodding_lib.library.tags.modifiers.TagModifier;
import com.mmodding.mmodding_lib.mixin.accessors.FluidAccessor;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidState.class)
public abstract class FluidStateMixin implements TagRuntimeManagement.FluidStateTagRuntimeManagement, FluidGroupComparable {

    @Shadow
    public abstract Fluid getFluid();

	@Shadow
	public abstract boolean isIn(TagKey<Fluid> tag);

	@Inject(method = "getVelocity", at = @At("HEAD"), cancellable = true)
    private void getVelocity(BlockView world, BlockPos pos, CallbackInfoReturnable<Vec3d> cir) {
        if (this.getFluid() instanceof FluidExtensions extensions) {
            cir.setReturnValue(((FluidAccessor) this.getFluid()).invokeGetVelocity(world, pos, (FluidState) (Object) this).multiply(extensions.getVelocityMultiplier()));
        }
    }

	@Override
	public boolean isIn(TagModifier<Fluid> modifier) {
		return modifier.result(this.getFluid(), this::isIn);
	}

	@Override
	public boolean isOf(FluidGroup group) {
		return this.getFluid() == group.getStill() || this.getFluid() == group.getFlowing();
	}
}
