package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.mmodding_lib.library.client.render.model.json.NotCollidingItemModelGenerator;
import net.minecraft.client.render.model.json.ItemModelGenerator;
import net.minecraft.client.texture.Sprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(ItemModelGenerator.class)
public class ItemModelGeneratorMixin {

	@WrapOperation(method = "addSubComponents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/json/ItemModelGenerator;getFrames(Lnet/minecraft/client/texture/Sprite;)Ljava/util/List;"))
	private List<ItemModelGenerator.Frame> wrapFrames(ItemModelGenerator instance, Sprite sprite, Operation<List<ItemModelGenerator.Frame>> original, @Local(argsOnly = true) int layer) {
		ItemModelGenerator object = (ItemModelGenerator) (Object) this;
		if (object instanceof NotCollidingItemModelGenerator notCollidingItemModelGenerator) {
			return notCollidingItemModelGenerator.frames.get(layer);
		}
		else {
			return original.call(instance, sprite);
		}
	}
}
