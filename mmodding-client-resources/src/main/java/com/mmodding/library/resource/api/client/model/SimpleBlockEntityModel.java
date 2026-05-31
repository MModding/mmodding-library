package com.mmodding.library.resource.api.client.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class SimpleBlockEntityModel<S extends BlockEntityRenderState> extends Model<S> {

	public SimpleBlockEntityModel(ModelLayerLocation location, BlockEntityRendererProvider.Context context) {
		this(context.bakeLayer(location));
	}

	public SimpleBlockEntityModel(ModelPart root) {
		super(root, RenderTypes::entityCutoutCull);
	}
}
