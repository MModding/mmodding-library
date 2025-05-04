package com.mmodding.mmodding_lib.library.fluids;

import com.mmodding.mmodding_lib.library.client.render.layer.RenderLayerOperations;
import com.mmodding.mmodding_lib.library.utils.EnvironmentUtils;
import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;

public interface FluidRegistrable extends Registrable {

    default void register(Identifier identifier) {
        if (this instanceof FlowableFluid fluid && this.isNotRegistered()) {
            RegistrationUtils.registerFluid(identifier, fluid);
            if (this instanceof FluidExtensions extensions) {
                if (EnvironmentUtils.isClient()) {
                    FluidRenderHandlerRegistry.INSTANCE.register(fluid, extensions.getRenderHandler());
                }
            }
            this.setRegistered();
        }
    }

	@Environment(EnvType.CLIENT)
	default void cutout() {
		if (this instanceof Fluid fluid) RenderLayerOperations.setCutout(fluid);
	}

	@Environment(EnvType.CLIENT)
	default void translucent() {
		if (this instanceof Fluid fluid) RenderLayerOperations.setTranslucent(fluid);
	}
}
