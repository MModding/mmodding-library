package com.mmodding.mmodding_lib.library.entities.goals;

import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.ParrotEntity;

public class FlyingAroundFarGoal extends ParrotEntity.ParrotWanderGoal {

	public FlyingAroundFarGoal(PathAwareEntity pathAwareEntity, double speed) {
		super(pathAwareEntity, speed);
	}
}
