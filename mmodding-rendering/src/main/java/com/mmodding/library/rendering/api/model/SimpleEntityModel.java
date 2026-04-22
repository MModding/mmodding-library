package com.mmodding.library.rendering.api.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class SimpleEntityModel<T extends EntityRenderState> extends EntityModel<T> {

	public SimpleEntityModel(ModelLayerLocation location, EntityRendererProvider.Context context) {
		this(context.bakeLayer(location));
	}

	public SimpleEntityModel(ModelPart root) {
		super(root);
	}
}
