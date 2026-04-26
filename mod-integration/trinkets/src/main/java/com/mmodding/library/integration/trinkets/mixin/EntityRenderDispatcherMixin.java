package com.mmodding.library.integration.trinkets.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.integration.trinkets.client.TrinketRendererImpl;
import com.mmodding.library.item.api.properties.CustomItemProperty;
import com.mmodding.library.item.api.properties.MModdingItemProperties;
import com.mmodding.library.rendering.impl.cosmetic.renderer.CosmeticRendererRegistryImpl;
import eu.pb4.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

	@Inject(method = "onResourceManagerReload", at = @At("TAIL"))
	private void createArmorRenderers(ResourceManager manager, CallbackInfo ci, @Local(name = "context") EntityRendererProvider.Context context) {
		CosmeticRendererRegistryImpl.createArmorRenderers(context);
		CosmeticRendererRegistryImpl.ENTRIES.forEach((item, renderer) -> {
			List<String> slots = CustomItemProperty.get(item, MModdingItemProperties.TRINKET_SLOTS);
			if (slots != null) {
				TrinketRendererRegistry.registerRenderer(item, new TrinketRendererImpl(renderer));
			}
		});
	}
}
