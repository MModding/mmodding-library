package com.mmodding.mmodding_lib.library.entities.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Hand;

public class CooledDownMeleeAttackGoal extends MeleeAttackGoal {

	private final int cooldownInTicks;

	public CooledDownMeleeAttackGoal(PathAwareEntity mob, int cooldownInTicks, double speed, boolean pauseWhenMobIdle) {
		super(mob, speed, pauseWhenMobIdle);
		this.cooldownInTicks = cooldownInTicks;
	}

	@Override
	protected void attack(LivingEntity target, double squaredDistance) {
		if (squaredDistance <= this.getSquaredMaxAttackDistance(target) && this.cooldown <= 0) {
			this.tryAttack(target);
			this.resetCooldown();
		}
	}

	protected void tryAttack(LivingEntity target) {
		this.mob.swingHand(Hand.MAIN_HAND);
		this.mob.tryAttack(target);
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
