package com.mmodding.mmodding_lib.library.client.render.entity.animation;

import com.mmodding.mmodding_lib.library.client.utils.AnimationUtils;
import com.mmodding.mmodding_lib.mixin.accessors.SinglePartEntityModelAccessor;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.animation.Animation;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.AnimationState;

public class AnimationManager {

	private final SinglePartEntityModel<?> model;

	private final Animation moving;
	private final Animation idle;
	private final Animation falling;
	private final Animation dodge;

	private Animation animationCache = null;

	public AnimationManager(SinglePartEntityModel<?> model, Animation moving, Animation idle, Animation falling, Animation dodge) {
		this.model = model;
		this.moving = moving;
		this.idle = idle;
		this.falling = falling;
		this.dodge = dodge;
	}

	private void updateFall(AnimationData data, int age, GroundChecker groundChecker) {
		if (groundChecker.isInAir()) {
			if (data.fallingAge != age) {
				data.fallingAge = age;
				data.fallingCount++;
			}
		}
		else {
			data.fallingCount = 0;
		}
	}

	private void smoothTransition(Animation animation, AnimationState state, float animationProgress, int smooth) {
		state.method_43686(animationProgress, 1.0f);
		state.animateIfValid(current -> AnimationUtils.animateSmoothly(this.model, animation, this.animationCache, current.method_43687(), 1.0f, SinglePartEntityModelAccessor.getField39195(), smooth));
	}

	private void transition(Animation animation, AnimationState state, float animationProgress) {
		this.animationCache = animation;
		AnimationUtils.updateAnimation(this.model, animation, state, animationProgress);
	}

	private void switchAnimation(AnimationData data, Animation animation, AnimationState state, float animationProgress, int age) {
		if (animation != null) {
			if (data.transitionCount != 0) {
				if (data.transitionAge != age) {
					data.transitionAge = age;
					data.transitionCount--;
				}
				this.smoothTransition(animation, state, animationProgress, data.transitionCount);
			}
			else if (this.animationCache != null && this.animationCache != animation) {
				data.transitionAge = age;
				data.transitionCount = 60;
				this.smoothTransition(animation, state, animationProgress, data.transitionCount);
			}
			else {
				this.transition(animation, state, animationProgress);
			}
		}
	}

	public void handle(AnimationData data, float animationProgress, int age, GroundChecker groundChecker, MovingChecker movingChecker) {
		this.model.getPart().traverse().forEach(ModelPart::resetTransform);
		if (data.dodge.isAnimating()) {
			this.switchAnimation(data, this.dodge, data.dodge, animationProgress, age);
		}
		else {
			this.updateFall(data, age, groundChecker);
			if (data.fallingCount < 13) {
				if (movingChecker.isMoving()) {
					this.switchAnimation(data, this.moving, data.moving, animationProgress, age);
				} else {
					this.switchAnimation(data, this.idle, data.idle, animationProgress, age);
				}
			}
			else {
				this.switchAnimation(data, this.falling, data.falling, animationProgress, age);
			}
		}
	}

	@FunctionalInterface
	public interface GroundChecker {

		boolean isOnGround();

		default boolean isInAir() {
			return !this.isOnGround();
		}
	}

	@FunctionalInterface
	public interface MovingChecker {

		boolean isMoving();

		default boolean isStatic() {
			return !this.isMoving();
		}
	}
}
