package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.mmodding_lib.library.client.render.model.InventoryModels;
import com.mmodding.mmodding_lib.library.client.render.model.json.NotCollidingItemModelGenerator;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {

	@Shadow
	private @Nullable SpriteAtlasManager spriteAtlasManager;

	@Shadow
	@Final
	private Map<Identifier, UnbakedModel> unbakedModels;

	@Shadow
	@Final
	private Map<Identifier, UnbakedModel> modelsToBake;

	@Shadow
	public abstract UnbakedModel getOrLoadModel(Identifier id);

	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", shift = At.Shift.AFTER, ordinal = 1))
	private void appendModdedModels(ResourceManager resourceManager, BlockColors blockColors, Profiler profiler, int i, CallbackInfo ci) {
		InventoryModels.REGISTRY.forEach(this::mmodding_lib$addModdedModel);
	}

	@Inject(method = "bake", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/UnbakedModel;bake(Lnet/minecraft/client/render/model/ModelLoader;Ljava/util/function/Function;Lnet/minecraft/client/render/model/ModelBakeSettings;Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/model/BakedModel;"), cancellable = true)
	private void useNotCollidingItemModelGeneratorConditionallyInstead(Identifier id, ModelBakeSettings settings, CallbackInfoReturnable<BakedModel> cir, @Local UnbakedModel unbakedModel) {
		if (unbakedModel instanceof JsonUnbakedModel jsonUnbakedModel && jsonUnbakedModel.getRootModel() == NotCollidingItemModelGenerator.MARKER) {
			assert this.spriteAtlasManager != null;
			cir.setReturnValue(NotCollidingItemModelGenerator.INSTANCE.create(this.spriteAtlasManager::getSprite, jsonUnbakedModel)
				.bake((ModelLoader) (Object) this, jsonUnbakedModel, this.spriteAtlasManager::getSprite, settings, id, false));
		}
	}

	@Inject(method = "loadModelFromJson", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Identifier;getPath()Ljava/lang/String;", shift = At.Shift.AFTER, ordinal = 0), cancellable = true)
	private void insertNotCollidingItemModelGenerationMarkerCondition(Identifier id, CallbackInfoReturnable<JsonUnbakedModel> cir) {
		if ("mmodding".equals(id.getNamespace()) && "builtin/not_colliding_generated".equals(id.getPath())) {
			cir.setReturnValue(NotCollidingItemModelGenerator.MARKER);
		}
	}

	@Unique
	public void mmodding_lib$addModdedModel(Identifier id) {
		UnbakedModel unbakedModel = this.getOrLoadModel(id);
		this.unbakedModels.put(id, unbakedModel);
		this.modelsToBake.put(id, unbakedModel);
	}
}
