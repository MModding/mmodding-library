package com.mmodding.mmodding_lib.library.entities.goals;

import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class MoveToSpecificPosGoal extends MoveToTargetPosGoal {

	private final BooleanSupplier condition;
	private final Supplier<Vec3d> position;

	public MoveToSpecificPosGoal(PathAwareEntity mob, double speed, int range, BooleanSupplier condition, Supplier<Vec3d> position) {
		super(mob, speed, range);
		this.condition = condition;
		this.position = position;
	}

	public MoveToSpecificPosGoal(PathAwareEntity mob, double speed, int range, int maxYDifference, BooleanSupplier condition, Supplier<Vec3d> position) {
		super(mob, speed, range, maxYDifference);
		this.condition = condition;
		this.position = position;
	}

	public Vec3d getPos() {
		return this.position.get();
	}

	@Override
	public boolean canStart() {
		return this.condition.getAsBoolean() && super.canStart();
	}

	@Override
	public boolean shouldContinue() {
		return this.condition.getAsBoolean() && super.shouldContinue();
	}

	@Override
	protected boolean isTargetPos(WorldView world, BlockPos pos) {
		return Math.floor(this.getPos().getX()) == pos.getX()
			&& Math.floor(this.getPos().getY()) == pos.getY()
			&& Math.floor(this.getPos().getZ()) == pos.getZ();
	}
}
