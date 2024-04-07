package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.mmodding_lib.library.client.render.entity.animation.DeathAnimation;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Quaternion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {

	@ModifyExpressionValue(method = "getOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/OverlayTexture;getV(Z)I"))
	private static int conditionallyCancelDeathAnimationRedOverlay(int original, LivingEntity entity, float whiteOverlayProgress) {
		if (!(entity instanceof DeathAnimation animation) || animation.executeDeathAnimation() != null) {
			return original;
		}
		else {
			boolean bool = entity.hurtTime > 0 || (animation.applyRedOverlayOnDeath() && entity.deathTime > 0);
			return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(bool));
		}
	}

	@WrapOperation(method = "setupTransforms", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lnet/minecraft/util/math/Quaternion;)V", ordinal = 1))
	private void conditionallyCancelDeathAnimationTransform(MatrixStack instance, Quaternion quaternion, Operation<Void> original, @Local(argsOnly = true) T entity) {
		if (!(entity instanceof DeathAnimation animation) || animation.executeDeathAnimation() != null) {
			original.call(instance, quaternion);
		}
	}
}
