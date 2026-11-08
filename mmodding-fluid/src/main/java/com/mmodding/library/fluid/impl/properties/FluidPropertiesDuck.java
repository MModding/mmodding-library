package com.mmodding.library.fluid.impl.properties;

import com.mmodding.library.core.api.management.info.DuckInterface;
import net.minecraft.world.level.material.Fluid;

@DuckInterface(Fluid.class)
public interface FluidPropertiesDuck {

	void mmodding$applyCustomProperties(Fluid fluid);
}
