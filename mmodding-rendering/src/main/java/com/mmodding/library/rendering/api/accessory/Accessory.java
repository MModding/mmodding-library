package com.mmodding.library.rendering.api.accessory;

import com.mmodding.library.rendering.api.model.EntityModelFactory;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

/**
 * A representation of "armor" model parts with deeply-customized models and textures.
 * It is called an accessory because I had no better idea, but it applies on every thing that you can wear.
 */
public interface Accessory {

	/**
	 * A map which indicates the models that are about to be used, identified by strings for model selection.
	 * @return the model factories
	 */
	Map<String, EntityModelFactory<HumanoidRenderState>> getModelFactories();

	/**
	 * A method which selects the model to use based on the provided information.
	 * @param stack the stack
	 * @param isSlim indicates is the humanoid model is slim
	 * @return the string associated to the model
	 */
	String getModel(ItemStack stack, boolean isSlim);

	/**
	 * A method which selects the texture to use based on the provided information.
	 * @param stack the stack
	 * @param isSlim indicates is the humanoid model is slim
	 * @return the location of the texture
	 */
	Identifier getTexture(ItemStack stack, boolean isSlim);
}
