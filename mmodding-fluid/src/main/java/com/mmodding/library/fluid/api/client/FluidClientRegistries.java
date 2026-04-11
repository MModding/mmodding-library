package com.mmodding.library.fluid.api.client;

import com.mmodding.library.core.api.registry.attachment.ResourceKeyAttachment;
import com.mmodding.library.java.api.color.Color;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;

public class FluidClientRegistries {

	/**
	 * @apiNote You can only register still fluids. It will spread to flowing fluids.
	 */
	public static final ResourceKeyAttachment<Fluid, Color> FOG_COLOR = ResourceKeyAttachment.create(BuiltInRegistries.FLUID);
}
