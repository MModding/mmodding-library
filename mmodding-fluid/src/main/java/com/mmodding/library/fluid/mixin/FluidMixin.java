package com.mmodding.library.fluid.mixin;

import com.mmodding.library.fluid.impl.properties.FluidPropertiesDuck;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Fluid.class)
public class FluidMixin {

	@Inject(method = "<init>", at = @At("TAIL"))
	private void applyCustomProperties(Fluid.Properties properties, CallbackInfo ci) {
		((FluidPropertiesDuck) properties).mmodding$applyCustomProperties((Fluid) (Object) this);
	}
}
