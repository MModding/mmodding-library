package com.mmodding.library.resource.api.client.renderer;

import com.mmodding.library.core.api.management.info.InjectedContent;
import com.mmodding.library.resource.api.client.model.SimpleEntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;

@InjectedContent(EntityRendererProvider.Context.class)
public interface EntityRendererProviderContextExtensions {

	/**
	 * Returns a data-driven {@link SimpleEntityModel}.
	 * @param identifier the identifier of the data driven model
	 * @return the model
	 */
	default <S extends EntityRenderState> SimpleEntityModel<S> getDataDrivenModel(Identifier identifier) {
		throw new IllegalStateException();
	}
}
