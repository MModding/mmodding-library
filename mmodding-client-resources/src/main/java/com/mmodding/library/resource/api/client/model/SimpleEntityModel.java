package com.mmodding.library.resource.api.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class SimpleEntityModel<S extends EntityRenderState> extends EntityModel<S> {

	public SimpleEntityModel(ModelLayerLocation location, EntityRendererProvider.Context context) {
		this(context.bakeLayer(location));
	}

	public SimpleEntityModel(ModelPart root) {
		super(root);
	}
}
