package com.mmodding.library.resource.impl.client.model.data;

import com.mmodding.library.resource.api.client.model.data.DataDrivenModelEvents;
import com.mmodding.library.resource.api.client.model.SimpleEntityModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;

import java.util.Map;

public class EntityModelGetterImpl implements DataDrivenModelEvents.FinalizeEntityModels.ModelGetter {

	private final Map<Identifier, SimpleEntityModel<? extends EntityRenderState>> models;

	public EntityModelGetterImpl(Map<Identifier, SimpleEntityModel<? extends EntityRenderState>> models) {
		this.models = models;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <S extends EntityRenderState> SimpleEntityModel<S> getModel(Identifier identifier) {
		if (!this.models.containsKey(identifier)) throw new IllegalArgumentException("Missing data-driven entity model: " + identifier);
		return (SimpleEntityModel<S>) this.models.get(identifier);
	}
}
