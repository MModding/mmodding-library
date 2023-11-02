package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Fluid.class)
public interface FluidAccessor {

	@Invoker("getVelocity")
	Vec3d invokeGetVelocity(BlockView world, BlockPos pos, FluidState state);
}
