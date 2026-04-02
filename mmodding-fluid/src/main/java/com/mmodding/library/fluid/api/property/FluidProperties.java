package com.mmodding.library.fluid.api.property;

public interface FluidProperties {

	<T> T getFluidProperty(FluidProperty<T> fluidProperty);

	interface Builder {

		<T> void withFluidProperty(FluidProperty<T> fluidProperty, T value);
	}
}
