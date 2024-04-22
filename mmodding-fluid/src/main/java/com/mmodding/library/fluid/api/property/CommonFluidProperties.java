package com.mmodding.library.fluid.api.property;

import com.mmodding.library.core.api.MModdingLibrary;

// Common used properties
public class CommonFluidProperties {

	public static final FluidProperty<Boolean> HYDRATABLE = FluidProperty.of(MModdingLibrary.createId("hydratable"), false);

	// kg/m^3
	public static final FluidProperty<Integer> DENSITY = FluidProperty.of(MModdingLibrary.createId("density"), 997);

	// Kelvin
	public static final FluidProperty<Integer> TEMPERATURE = FluidProperty.of(MModdingLibrary.createId("temperature"), 293);

	// mm^2/s
	public static final FluidProperty<Float> VISCOSITY = FluidProperty.of(MModdingLibrary.createId("viscosity"), 0.8927f);
}
