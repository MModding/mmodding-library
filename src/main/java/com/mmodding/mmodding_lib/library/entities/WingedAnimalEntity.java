package com.mmodding.mmodding_lib.library.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableFloat;

public abstract class WingedAnimalEntity extends AnimalEntity implements Winged {

	private final MutableFloat flapProgress = new MutableFloat();
	private final MutableFloat maxWingDeviation = new MutableFloat();
	private final MutableFloat prevMaxWingDeviation = new MutableFloat();
	private final MutableFloat prevFlapProgress = new MutableFloat();
	private final MutableFloat flapSpeed = new MutableFloat(1.0f);
	private final MutableFloat nextFlap = new MutableFloat(1.0f);

	protected WingedAnimalEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public MutableFloat flapProgress() {
		return this.flapProgress;
	}

	@Override
	public MutableFloat maxWingDeviation() {
		return this.maxWingDeviation;
	}

	@Override
	public MutableFloat prevMaxWingDeviation() {
		return this.prevMaxWingDeviation;
	}

	@Override
	public MutableFloat prevFlapProgress() {
		return this.prevFlapProgress;
	}

	@Override
	public MutableFloat flapSpeed() {
		return this.flapSpeed;
	}

	@Override
	public MutableFloat nextFlap() {
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
