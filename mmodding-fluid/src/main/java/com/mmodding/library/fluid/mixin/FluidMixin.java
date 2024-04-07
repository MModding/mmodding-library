package com.mmodding.library.fluid.mixin;

import com.mmodding.library.fluid.api.FluidLike;
import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Fluid.class)
public class FluidMixin implements FluidLike {

	@Override
	public Type getType() {
		return Type.VANILLA;
	}
}
