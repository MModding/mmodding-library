package com.mmodding.library.rendering.api;

import com.mmodding.library.rendering.impl.AccessoryInfoImpl;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

import java.util.function.Function;

/**
 * An object which gathers information about a modeled and textured accessory for a humanoid model.
 */
public interface AccessoryInfo {

	/**
	 * Creates a {@link AccessoryInfo} object.
	 * @param factory the model factory
	 * @param texture the texture location
	 * @return the accessory info
	 */
	static AccessoryInfo create(Function<EntityRendererProvider.Context, EntityModel<HumanoidRenderState>> factory, Identifier texture) {
		return new AccessoryInfoImpl(factory, texture);
	}

	/**
	 * Creates the model of an accessory from the entity renderer's context.
	 * @param context the context
	 * @return the accessory's model
	 */
	EntityModel<HumanoidRenderState> createModel(EntityRendererProvider.Context context);

	/**
	 * @return the accessory's texture
	 */
	Identifier getTexture();
}
