package com.mmodding.mmodding_lib.library.client.render.layer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;

@Environment(EnvType.CLIENT)
public class RenderLayerOperations {

    public static void setCutout(Block block) {
		BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
    }

    public static void setTranslucent(Block block) {
		BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
    }

	public static void setCutout(Fluid fluid) {
		BlockRenderLayerMap.INSTANCE.putFluid(fluid, RenderLayer.getCutout());
	}

	public static void setTranslucent(Fluid fluid) {
		BlockRenderLayerMap.INSTANCE.putFluid(fluid, RenderLayer.getTranslucent());
	}

	public static void setTransparent(Block block) {
		FluidRenderHandlerRegistry.INSTANCE.setBlockTransparency(block, true);
	}
}
