package com.mmodding.library.fluid.api.client;

import com.mmodding.library.core.api.registry.attachment.RegistryKeyAttachment;
import com.mmodding.library.java.api.color.Color;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;

public class FluidClientRegistries {

	/**
	 * @apiNote You can only register still fluids. It will spread to flowing fluids.
	 */
	public static final RegistryKeyAttachment<Fluid, Color> FOG_COLOR = RegistryKeyAttachment.create(Registries.FLUID);
}
