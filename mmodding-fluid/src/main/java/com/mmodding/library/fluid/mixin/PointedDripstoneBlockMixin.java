package com.mmodding.library.fluid.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mmodding.library.fluid.api.AdvancedFlowableFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {

	@WrapMethod(method = "getDripParticle")
	private static ParticleOptions replaceDripParticle(Level level, Fluid fluidAbove, BlockPos posAbove, Operation<ParticleOptions> original) {
		return fluidAbove instanceof AdvancedFlowableFluid advanced ? advanced.getDrippingParticle() : original.call(level, fluidAbove, posAbove);
	}
}
