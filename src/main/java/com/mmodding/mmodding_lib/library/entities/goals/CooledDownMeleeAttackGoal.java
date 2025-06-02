package com.mmodding.mmodding_lib.library.entities.goals;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class CooledDownMeleeAttackGoal extends MeleeAttackGoal {

	private final int cooldownInTicks;

	public CooledDownMeleeAttackGoal(PathAwareEntity mob, int cooldownInTicks, double speed, boolean pauseWhenMobIdle) {
		super(mob, speed, pauseWhenMobIdle);
		this.cooldownInTicks = cooldownInTicks;
	}

	@Override
	protected void resetCooldown() {
		this.cooldown = this.getTickCount(this.cooldownInTicks);
	}

	@Override
	protected int getMaxCooldown() {
		return this.getTickCount(this.cooldownInTicks);
	}
}
