package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.mmodding_lib.library.entities.projectiles.StuckArrowDisplay;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.StuckArrowsFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ArrowEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StuckArrowsFeatureRenderer.class)
public class StuckArrowsFeatureRendererMixin {

	@ModifyExpressionValue(method = "renderObject", at = @At(value = "NEW", target = "(Lnet/minecraft/world/World;DDD)Lnet/minecraft/entity/projectile/ArrowEntity;"))
	private ArrowEntity renderObject(ArrowEntity original, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Entity entity, float directionX, float directionY, float directionZ, float tickDelta) {
		return entity instanceof StuckArrowDisplay<?> display ? display.getArrowInstance(entity.world, entity.getX(), entity.getY(), entity.getZ()) : original;
	}
}
