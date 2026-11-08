package com.mmodding.library.fluid.api.properties;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.fluid.impl.properties.CustomFluidPropertyImpl;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;

public interface CustomFluidProperty<T> {

	/**
	 * Creates the {@link CustomFluidProperty} instance for these parameters.
	 */
	static <T> CustomFluidProperty<T> create(Identifier identifier, Class<?> type, T defaultValue) {
		return new CustomFluidPropertyImpl<>(identifier, type, defaultValue);
	}

	/**
	 * Gets the value of a {@link CustomFluidProperty}.
	 * @param property the custom fluid property
	 * @return the associated value
	 */
	static <T> T get(Fluid fluid, CustomFluidProperty<T> property) {
		return CustomFluidPropertyImpl.get(fluid, property);
	}

	/**
	 * The identifier of the {@link CustomFluidProperty}.
	 * If you believe that the property you make is conventional, consider using the <code>mmodding</code> namespace (see {@link MModdingLibrary#createId(String)}).
	 * @return the identifier
	 */
	Identifier getIdentifier();

	/**
	 * The class object of the value type.
	 * @return the class object
	 */
	Class<?> getType();

	/**
	 * Returns the default value of this setting for a fluid not specifying it.
	 * @return the value
	 */
	T getDefault();
}
