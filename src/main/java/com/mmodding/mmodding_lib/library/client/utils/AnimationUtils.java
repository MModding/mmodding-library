package com.mmodding.mmodding_lib.library.client.utils;

import com.mmodding.mmodding_lib.mixin.accessors.AnimationStateAccessor;
import com.mmodding.mmodding_lib.mixin.accessors.SinglePartEntityModelAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.animation.Animation;
import net.minecraft.client.render.animation.Animator;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
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
		state.animateIfValid(current -> Animator.animate(model, animation, current.method_43687(), 1.0f, SinglePartEntityModelAccessor.getField39195()));
	}

	public static void reset(AnimationState state) {
		state.stop();
		((AnimationStateAccessor) state).setField_39112(0);
	}

	public static void animateSmoothly(SinglePartEntityModel<?> model, Animation animation, Animation lastAnimation, long duration, float strength, Vec3f animationCache, int smooth) {
		Animator.animate(model, animation, duration, strength, animationCache);
		/* float f = AnimatorAccessor.invokeGetAnimationTimestamp(animation, duration);
		animation.animations().forEach((string, parts) -> {
			List<PartAnimation> lastParts = lastAnimation.animations().get(string);
			if (lastParts != null) {
				Optional<ModelPart> optionalModelPart = model.findPart(string);
				optionalModelPart.ifPresent(modelPart -> parts.forEach(current -> lastParts.forEach(lastPart -> {
					AnimationKeyframe[] animationKeyframes = current.keyframes();
					AnimationKeyframe[] lastAnimationKeyFrames = lastPart.keyframes();
					int i = Math.max(0, MathHelper.binarySearch(0, lastAnimationKeyFrames.length, index -> f <= lastAnimationKeyFrames[index].timestamp()) - 1);
					int j = Math.min(animationKeyframes.length - 1, i + 1);
					AnimationKeyframe lastAnimationKeyFrame = lastAnimationKeyFrames[i];
					AnimationKeyframe endAnimationKeyFrame = animationKeyframes[j];
					float h = f - lastAnimationKeyFrame.timestamp();
					float k = MathHelper.clamp(h / (endAnimationKeyFrame.timestamp() - lastAnimationKeyFrame.timestamp()), 0.0f, 1.0f);
					endAnimationKeyFrame.interpolator().apply(animationCache, k / smooth, animationKeyframes, i, j, strength);
					current.transformation().apply(modelPart, animationCache);
				})));
			}
		}); */
	}
}
