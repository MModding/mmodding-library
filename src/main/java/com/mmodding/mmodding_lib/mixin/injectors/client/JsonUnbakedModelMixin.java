package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.client.render.model.json.NotCollidingItemModelGenerator;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.ItemModelGenerator;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

@Mixin(JsonUnbakedModel.class)
public abstract class JsonUnbakedModelMixin {

	@Shadow
	public abstract JsonUnbakedModel getRootModel();

	@Shadow
	public abstract SpriteIdentifier resolveSprite(String spriteName);

	@Inject(method = "getTextureDependencies", at = @At("TAIL"), cancellable = true)
	private void insertNotCollidingGeneratedModelLogic(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences, CallbackInfoReturnable<Collection<SpriteIdentifier>> cir) {
		if (this.getRootModel() == NotCollidingItemModelGenerator.MARKER) {
			Set<SpriteIdentifier> set2 = (Set<SpriteIdentifier>) cir.getReturnValue();
			ItemModelGenerator.LAYERS.forEach(name -> set2.add(this.resolveSprite(name)));
			cir.setReturnValue(set2);
		}
	}
}
