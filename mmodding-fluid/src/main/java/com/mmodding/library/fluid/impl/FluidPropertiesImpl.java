package com.mmodding.library.fluid.impl;

import com.mmodding.library.fluid.api.FluidBehavior;
import com.mmodding.library.fluid.api.property.FluidProperty;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.Identifier;

import java.util.Map;

public class FluidPropertiesImpl implements FluidBehavior.FluidProperties {

	private final Map<Identifier, Object> properties = new Object2ObjectOpenHashMap<>();

	@Override
	public <T> void withFluidProperty(FluidProperty<T> fluidProperty) {
		this.withFluidProperty(fluidProperty, fluidProperty.getFallback());
	}

	@Override
	public <T> void withFluidProperty(FluidProperty<T> fluidProperty, T value) {
		this.properties.put(fluidProperty.getIdentifier(), value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getFluidProperty(FluidProperty<T> fluidProperty) {
		if (this.properties.containsKey(fluidProperty.getIdentifier())) {
			return (T) this.properties.get(fluidProperty.getIdentifier());
		}
		else {
			return fluidProperty.getFallback();
		}
	}
}
