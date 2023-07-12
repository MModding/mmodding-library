package com.mmodding.mmodding_lib.library.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.mutable.MutableFloat;

public interface Winged {

	default Entity entity() {
		return (Entity) this;
	}

	MutableFloat flapProgress();

	MutableFloat maxWingDeviation();

	MutableFloat prevMaxWingDeviation();

	MutableFloat prevFlapProgress();

	SoundEvent getFlapSound();

	MutableFloat flapSpeed();

	MutableFloat nextFlap();

	default boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	default void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
	}

	default boolean hasWings() {
		return this.entity().flyDistance > this.nextFlap().getValue();
	}

	default void addFlapEffects() {
		this.entity().playSound(this.getFlapSound(), 0.15f, 1.0f);
		this.nextFlap().setValue(this.entity().flyDistance + this.maxWingDeviation().getValue() / 2.0f);
	}

	default void flapWings() {
		this.prevMaxWingDeviation().setValue(this.maxWingDeviation().getValue());
		this.prevFlapProgress().setValue(this.flapProgress().getValue());
		this.maxWingDeviation().add((!this.entity().isOnGround() && !this.entity().hasVehicle() ? 4 : -1) * 0.3f);
		this.maxWingDeviation().setValue(MathHelper.clamp(this.maxWingDeviation().getValue(), 0.0f, 1.0f));

		if (!this.entity().isOnGround() && this.flapSpeed().getValue() < 1.0f) {
			this.flapSpeed().setValue(1.0f);
		}

		this.flapSpeed().setValue(this.flapSpeed().getValue() * 0.9f);
		Vec3d vec3d = this.entity().getVelocity();

		if (!this.entity().isOnGround() && vec3d.y < 0.0) {
			this.entity().setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
		}

		this.flapProgress().add(this.flapSpeed().getValue() * 2.0f);
	}
}
