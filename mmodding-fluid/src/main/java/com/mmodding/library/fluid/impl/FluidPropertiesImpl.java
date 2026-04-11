package com.mmodding.library.fluid.impl;

import com.mmodding.library.fluid.api.property.FluidProperties;
import com.mmodding.library.fluid.api.property.FluidProperty;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;
import net.minecraft.resources.ResourceLocation;

@ApiStatus.Internal
public class FluidPropertiesImpl implements FluidProperties {

	private final Map<ResourceLocation, Object> properties;

	private FluidPropertiesImpl(Map<ResourceLocation, Object> properties) {
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

		private final Map<ResourceLocation, Object> properties = new Object2ObjectOpenHashMap<>();

		@Override
		public <T> void withFluidProperty(FluidProperty<T> fluidProperty, T value) {
			this.properties.put(fluidProperty.getIdentifier(), value);
		}

		public FluidProperties build() {
			return new FluidPropertiesImpl(this.properties);
		}
	}
}
