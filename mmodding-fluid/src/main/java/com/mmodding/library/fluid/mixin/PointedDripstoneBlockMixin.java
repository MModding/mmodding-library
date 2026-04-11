package com.mmodding.library.fluid.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.fluid.api.AdvancedFlowableFluid;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {

	@ModifyArg(method = "spawnDripParticle(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/Fluid;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"), index = 0)
	private static ParticleOptions applyDrippingParticle(ParticleOptions particleEffect, @Local(argsOnly = true) Fluid fluid) {
		return fluid instanceof AdvancedFlowableFluid advanced ? advanced.getDrippingParticle() : particleEffect;
	}
}
