package com.mmodding.mmodding_lib.library.entities;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class WingedAnimalEntity extends AnimalEntity implements Winged {

	private final AtomicDouble flapProgress = new AtomicDouble();
	private final AtomicDouble maxWingDeviation = new AtomicDouble();
	private final AtomicDouble prevMaxWingDeviation = new AtomicDouble();
	private final AtomicDouble prevFlapProgress = new AtomicDouble();
	private final AtomicDouble flapSpeed = new AtomicDouble(1.0f);
	private final AtomicDouble nextFlap = new AtomicDouble(1.0f);

	protected WingedAnimalEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public AtomicDouble flapProgress() {
		return this.flapProgress;
	}

	@Override
	public AtomicDouble maxWingDeviation() {
		return this.maxWingDeviation;
	}

	@Override
	public AtomicDouble prevMaxWingDeviation() {
		return this.prevMaxWingDeviation;
	}

	@Override
	public AtomicDouble prevFlapProgress() {
		return this.prevFlapProgress;
	}

	@Override
	public AtomicDouble flapSpeed() {
		return this.flapSpeed;
	}

	@Override
	public AtomicDouble nextFlap() {
		return this.nextFlap;
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return Winged.super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
	}

	@Override
	public void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
		Winged.super.fall(heightDifference, onGround, landedState, landedPosition);
	}

	@Override
	public boolean hasWings() {
		return Winged.super.hasWings();
	}

	@Override
	public void addFlapEffects() {
		Winged.super.addFlapEffects();
	}
}
