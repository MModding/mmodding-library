package com.mmodding.library.fluid.api.properties;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.api.management.info.InjectedContent;
import net.minecraft.world.level.material.Fluid;

@InjectedContent(Fluid.Properties.class)
public interface MModdingFluidProperties {

	CustomFluidProperty<Boolean> HYDRATABLE = CustomFluidProperty.create(MModdingLibrary.createId("hydratable"), Boolean.class, false);
	CustomFluidProperty<Integer> DENSITY = CustomFluidProperty.create(MModdingLibrary.createId("density"), Integer.class, 997);
	CustomFluidProperty<Integer> TEMPERATURE = CustomFluidProperty.create(MModdingLibrary.createId("temperature"), Integer.class, 293);
	CustomFluidProperty<Float> VISCOSITY = CustomFluidProperty.create(MModdingLibrary.createId("viscosity"), Float.class, 0.8927f);

	/**
	 * Specifies that the fluid can be used to stay hydrated!
	 * @return the properties
	 */
	default Fluid.Properties hydratable() {
		return this.custom(HYDRATABLE, true);
	}

	/**
	 * Specifies the density of the fluid.
	 * @param density the density (in kg/m^3)
	 * @return the properties
	 */
	default Fluid.Properties density(int density) {
		return this.custom(DENSITY, density);
	}

	/**
	 * Specifies the temperature of the fluid.
	 * @param temperature the temperature (in Kelvin)
	 * @return the properties
	 */
	default Fluid.Properties temperature(int temperature) {
		return this.custom(TEMPERATURE, temperature);
	}

	/**
	 * Specifies the viscosity of the fluid.
	 * @param viscosity the viscosity (in mm^2/s)
	 * @return the properties
	 */
	default Fluid.Properties viscosity(float viscosity) {
		return this.custom(VISCOSITY, viscosity);
	}

	/**
	 * Allows setting custom fluid properties.
	 * @param property the custom fluid property
	 * @param value the value
	 * @return the current fluid properties
	 * @param <T> the class type of the property
	 */
	<T> Fluid.Properties custom(CustomFluidProperty<T> property, T value);
}
