package com.mmodding.mmodding_lib.library.client.render.entity.animation;

import com.mmodding.mmodding_lib.library.client.utils.AnimationUtils;
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

	private void switchAnimation(Animation animation, AnimationState state, float animationProgress) {
		AnimationUtils.updateAnimation(this.model, animation, state, animationProgress);
		// data.transition = 20;
	}

	public void handle(AnimationData data, float animationProgress, int age, GroundChecker groundChecker, MovingChecker movingChecker) {
		this.model.getPart().traverse().forEach(ModelPart::resetTransform);
		if (data.dodge.isAnimating()) {
			this.switchAnimation(this.dodge, data.dodge, animationProgress);
		}
		else {
			this.updateFall(data, age, groundChecker);
			if (data.fallingCount <= 3) {
				if (movingChecker.isMoving()) {
					this.switchAnimation(this.moving, data.moving, animationProgress);
				} else {
					this.switchAnimation(this.idle, data.idle, animationProgress);
				}
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
