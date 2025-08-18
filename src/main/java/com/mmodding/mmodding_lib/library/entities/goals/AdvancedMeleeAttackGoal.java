package com.mmodding.mmodding_lib.library.entities.goals;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class AdvancedMeleeAttackGoal extends MeleeAttackGoal {

	private final double minimalDistance;

	/**
	 * @param minimalDistance the minimal distance required so that the entity moves to the target
	 */
	public AdvancedMeleeAttackGoal(PathAwareEntity mob, double speed, double minimalDistance, boolean pauseWhenMobIdle) {
		super(mob, speed, pauseWhenMobIdle);
		this.minimalDistance = minimalDistance;
	}

	public double getMinimalDistance() {
		return this.minimalDistance;
	}
}
