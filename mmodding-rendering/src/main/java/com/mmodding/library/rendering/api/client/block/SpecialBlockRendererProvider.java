package com.mmodding.library.rendering.api.client.block;

import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.resources.model.sprite.SpriteGetter;

@FunctionalInterface
public interface SpecialBlockRendererProvider<S extends SpecialBlockRenderState> {

	SpecialBlockRenderer<S> create(Context context);

	record Context(SpecialBlockRenderDispatcher blockEntityRenderDispatcher, BlockModelResolver blockModelResolver, ItemModelResolver itemModelResolver, EntityRenderDispatcher entityRenderer, EntityModelSet entityModelSet, Font font, SpriteGetter sprites, PlayerSkinRenderCache playerSkinRenderCache) {
		public ModelPart bakeLayer(final ModelLayerLocation id) {
			return this.entityModelSet.bakeLayer(id);
		}
	}
}
