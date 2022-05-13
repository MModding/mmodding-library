package com.mmodding.mmodding_lib.lib.utils;

import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

public class RenderLayerUtils {
    public static void setCutout(Block block) {
		BlockRenderLayerMap.put(RenderLayer.getCutout(), block);
    }

    public static void setTranslucent(Block block) {
		BlockRenderLayerMap.put(RenderLayer.getTranslucent(), block);
    }
}
