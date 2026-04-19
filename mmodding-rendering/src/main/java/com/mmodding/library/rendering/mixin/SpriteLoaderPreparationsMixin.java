package com.mmodding.library.rendering.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.rendering.impl.TextureAliasesImpl;
import net.minecraft.client.renderer.texture.SpriteLoader;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

@Mixin(SpriteLoader.Preparations.class)
public class SpriteLoaderPreparationsMixin {

	@SuppressWarnings("unchecked")
	@WrapOperation(method = "getSprite", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
	private <K, V> V applyAliases(Map<K, V> instance, K o, Operation<V> original) {
		return (V) TextureAliasesImpl.applyAliases((Identifier) o);
	}
}
