package com.mmodding.library.rendering.impl;

import com.mmodding.library.rendering.api.AccessoryInfo;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Function;

@ApiStatus.Internal
public class AccessoryInfoImpl implements AccessoryInfo {

	private final Function<EntityRendererProvider.Context, EntityModel<HumanoidRenderState>> factory;
	private final Identifier texture;

	public AccessoryInfoImpl(Function<EntityRendererProvider.Context, EntityModel<HumanoidRenderState>> factory, Identifier texture) {
		this.factory = factory;
		this.texture = texture;
	}

	@Override
	public EntityModel<HumanoidRenderState> getModel(EntityRendererProvider.Context context) {
		return this.factory.apply(context);
	}

	@Override
	public Identifier getTexture() {
		return this.texture;
	}
}
