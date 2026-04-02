package com.mmodding.library.fluid.api;

import com.mmodding.library.fluid.api.property.FluidProperties;
import com.mmodding.library.fluid.impl.FluidPropertiesImpl;
import net.minecraft.state.property.IntProperty;

/**
 * If you want little extras.
 */
public abstract class AdvancedFlowableFluid extends UnitedFlowableFluid {

	private final FluidProperties properties;

	public AdvancedFlowableFluid(IntProperty levels, boolean still) {
		super(levels, still);
		FluidPropertiesImpl.Builder builder = new FluidPropertiesImpl.Builder();
		this.appendFluidProperties(builder);
		this.properties = builder.build();
	}

	protected abstract void appendFluidProperties(FluidProperties.Builder builder);

	public final FluidProperties getFluidProperties() {
		return this.properties;
	}
}
