package com.mmodding.library.fluid.mixin;

import com.mmodding.library.fluid.api.properties.CustomFluidProperty;
import com.mmodding.library.fluid.api.properties.MModdingFluidProperties;
import com.mmodding.library.fluid.impl.properties.CustomFluidPropertyImpl;
import com.mmodding.library.fluid.impl.properties.FluidPropertiesDuck;
import com.mmodding.library.java.api.list.BiList;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Fluid.Properties.class)
public class FluidPropertiesMixin implements MModdingFluidProperties, FluidPropertiesDuck {

	@Unique
	private final BiList<Identifier, Object> customFluidPropertiesQueue = BiList.create();

	@Override
	@SuppressWarnings("AddedMixinMembersNamePattern")
	public <T> Fluid.Properties custom(CustomFluidProperty<T> property, T value) {
		this.customFluidPropertiesQueue.add(property.getIdentifier(), value);
		return (Fluid.Properties) (Object) this;
	}

	@Override
	public void mmodding$applyCustomProperties(Fluid fluid) {
		this.customFluidPropertiesQueue.forEach((property, value) ->
			CustomFluidPropertyImpl.PROPERTIES_COMPANION.getOrCreateCompanion(fluid).register(property, value)
		);
		this.customFluidPropertiesQueue.clear();
	}
}
