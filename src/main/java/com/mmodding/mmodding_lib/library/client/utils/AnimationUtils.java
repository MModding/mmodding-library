package com.mmodding.mmodding_lib.library.client.utils;

import com.mmodding.mmodding_lib.mixin.accessors.SinglePartEntityModelAccessor;
import net.minecraft.client.render.animation.Animation;
import net.minecraft.client.render.animation.Animator;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class AnimationUtils {

	public static boolean isMoving(LivingEntity livingEntity, float limbDistance) {
		return AnimationUtils.isMoving(livingEntity, limbDistance, 0.015f);
	}

	public static boolean isMoving(LivingEntity livingEntity, float limbDistance, float motionThreshold) {
		Vec3d velocity = livingEntity.getVelocity();
		float averageVelocity = (MathHelper.abs((float) velocity.x) + MathHelper.abs((float) velocity.z)) / 2f;
		return averageVelocity >= motionThreshold && limbDistance != 0;
	}

	public static void updateAnimation(SinglePartEntityModel<?> model, Animation animation, AnimationState state, float animationProgress) {
		AnimationUtils.updateAnimation(model, animation, state, animationProgress, 1.0f);
	}

	public static void updateAnimation(SinglePartEntityModel<?> model, Animation animation, AnimationState state, float animationProgress, float speedFactor) {
		state.method_43686(animationProgress, speedFactor);
		state.animateIfValid(current -> Animator.animate(model, animation, current.method_43687(), 1.0F, SinglePartEntityModelAccessor.getField39195()));
	}
}
