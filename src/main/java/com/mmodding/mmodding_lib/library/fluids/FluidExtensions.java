package com.mmodding.mmodding_lib.library.fluids;

import com.mmodding.mmodding_lib.library.fluids.collisions.FluidCollisionHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.minecraft.particle.DefaultParticleType;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public interface FluidExtensions {

	float getVelocityMultiplier();

	DefaultParticleType getDrippingParticle();

	FluidCollisionHandler getCollisionHandler();

	@ClientOnly
	FluidRenderHandler getRenderHandler();
}
