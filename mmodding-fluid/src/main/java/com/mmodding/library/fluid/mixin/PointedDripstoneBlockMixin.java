package com.mmodding.library.fluid.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.fluid.api.AdvancedFlowableFluid;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {

	@ModifyArg(method = "createParticle(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/fluid/Fluid;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"), index = 0)
	private static ParticleEffect applyDrippingParticle(ParticleEffect particleEffect, @Local(argsOnly = true) Fluid fluid) {
		return fluid instanceof AdvancedFlowableFluid advanced ? advanced.getDrippingParticle() : particleEffect;
	}
}
