package com.mmodding.library.resource.impl.client.model.data;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.resource.api.client.model.data.DataDrivenModelEvents;
import com.mmodding.library.resource.api.client.model.SimpleBlockEntityModel;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.resources.Identifier;

public class BlockEntityModelGetterImpl implements DataDrivenModelEvents.FinalizeBlockEntityModels.ModelGetter {

	private final LiteRegistry<SimpleBlockEntityModel<? extends BlockEntityRenderState>> models;

	public BlockEntityModelGetterImpl(LiteRegistry<SimpleBlockEntityModel<? extends BlockEntityRenderState>> models) {
		this.models = models;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <S extends BlockEntityRenderState> SimpleBlockEntityModel<S> getModel(Identifier identifier) {
		if (!this.models.contains(identifier)) throw new IllegalArgumentException("Missing data-driven block entity model: " + identifier);
		return (SimpleBlockEntityModel<S>) this.models.get(identifier);
	}
}
