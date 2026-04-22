package com.mmodding.library.rendering.api.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public interface EntityModelFactory<T extends EntityRenderState> {

	static <T extends EntityRenderState> EntityModelFactory<T> of(ModelLayerLocation location) {
		return context -> new SimpleEntityModel<>(location, context);
	}

	EntityModel<T> createModel(EntityRendererProvider.Context context);
}
