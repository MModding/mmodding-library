package com.mmodding.library.fluid.impl;

import com.mmodding.library.fluid.api.property.FluidProperties;
import com.mmodding.library.fluid.api.property.FluidProperty;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

@ApiStatus.Internal
public class FluidPropertiesImpl implements FluidProperties {

	private final Map<Identifier, Object> properties;

	private FluidPropertiesImpl(Map<Identifier, Object> properties) {
		this.properties = properties;
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

	public static class Builder implements FluidProperties.Builder {

		private final Map<Identifier, Object> properties = new Object2ObjectOpenHashMap<>();

		@Override
		public <T> void withFluidProperty(FluidProperty<T> fluidProperty, T value) {
			this.properties.put(fluidProperty.getIdentifier(), value);
		}

		public FluidProperties build() {
			return new FluidPropertiesImpl(this.properties);
		}
	}
}
