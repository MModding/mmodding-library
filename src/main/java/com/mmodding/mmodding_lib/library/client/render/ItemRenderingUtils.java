package com.mmodding.mmodding_lib.library.client.render;

import com.mmodding.mmodding_lib.ducks.ItemRendererDuckInterface;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class ItemRenderingUtils {

	public static void renderItemWithVertices(ItemRenderer renderer, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, VertexConsumer vertices) {
		((ItemRendererDuckInterface) renderer).mmodding_lib$setVertices(vertices);
		renderer.renderItem(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, model);
		((ItemRendererDuckInterface) renderer).mmodding_lib$clearVertices();
	}
}
