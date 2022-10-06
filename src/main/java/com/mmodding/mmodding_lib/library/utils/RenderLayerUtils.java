package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.mixin.accessors.BufferBuilderStorageAccessor;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

public class RenderLayerUtils {
    public static void setCutout(Block block) {
		BlockRenderLayerMap.put(RenderLayer.getCutout(), block);
    }

    public static void setTranslucent(Block block) {
		BlockRenderLayerMap.put(RenderLayer.getTranslucent(), block);
    }

	public static void addEntityBuilder(RenderLayer layer) {
		((BufferBuilderStorageAccessor) MinecraftClient.getInstance().getBufferBuilders()).getEntityBuilders().put(layer, new BufferBuilder(layer.getExpectedBufferSize()));
	}
}
