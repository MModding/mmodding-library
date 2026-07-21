package com.mmodding.library.resource.mixin.client;

import com.mmodding.library.resource.api.client.model.SimpleEntityModel;
import com.mmodding.library.resource.api.client.renderer.EntityRendererProviderContextExtensions;
import com.mmodding.library.resource.impl.client.model.data.EntityModelReloader;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRendererProvider.Context.class)
public abstract class EntityRendererProviderContextMixin implements EntityRendererProviderContextExtensions {

	@Override
	@SuppressWarnings("unchecked")
	public <S extends EntityRenderState> SimpleEntityModel<S> getDataDrivenModel(Identifier identifier) {
		return (SimpleEntityModel<S>) EntityModelReloader.INSTANCE.getModel(identifier);
	}
}
