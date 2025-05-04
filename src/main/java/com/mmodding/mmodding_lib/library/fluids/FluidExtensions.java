package com.mmodding.mmodding_lib.library.fluids;

import com.mmodding.mmodding_lib.library.colors.Color;
import com.mmodding.mmodding_lib.library.fluids.collisions.FluidCollisionHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.minecraft.particle.DefaultParticleType;

public interface FluidExtensions {

	FluidGroup getGroup();

	float getVelocityMultiplier();

	DefaultParticleType getDrippingParticle();

	FluidCollisionHandler getCollisionHandler();

	@Environment(EnvType.CLIENT)
	FluidRenderHandler getRenderHandler();

	@Environment(EnvType.CLIENT)
	Color getFogColor();
}
