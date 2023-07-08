package com.mmodding.mmodding_lib.library.entities;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public interface Winged {

	default Entity entity() {
		return (Entity) this;
	}

	AtomicDouble flapProgress();

	AtomicDouble maxWingDeviation();

	AtomicDouble prevMaxWingDeviation();

	AtomicDouble prevFlapProgress();

	SoundEvent getFlapSound();

	AtomicDouble flapSpeed();

	AtomicDouble nextFlap();

	default boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	default void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
	}

	default boolean hasWings() {
		return this.entity().flyDistance > this.nextFlap().get();
	}

	default void addFlapEffects() {
		this.entity().playSound(this.getFlapSound(), 0.15f, 1.0f);
		this.nextFlap().set(this.entity().flyDistance + this.maxWingDeviation().get() / 2.0f);
	}

	default void flapWings() {
		this.prevMaxWingDeviation().set(this.maxWingDeviation().get());
		this.prevFlapProgress().set(this.flapProgress().get());
		this.maxWingDeviation().set(this.maxWingDeviation().get() + (!this.entity().isOnGround() && !this.entity().hasVehicle() ? 4 : -1) * 0.3f);
		this.maxWingDeviation().set(MathHelper.clamp(this.maxWingDeviation().get(), 0.0F, 1.0f));

		if (!this.entity().isOnGround() && this.flapSpeed().get() < 1.0f) {
			this.flapSpeed().set(1.0f);
		}

		this.flapSpeed().set(this.flapSpeed().get() * 0.9f);
		Vec3d vec3d = this.entity().getVelocity();

		if (!this.entity().isOnGround() && vec3d.y < 0.0) {
			this.entity().setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
		}

		this.flapProgress().set(this.flapProgress().get() + this.flapSpeed().get() * 2.0f);
	}
}
