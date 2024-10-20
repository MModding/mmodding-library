package com.mmodding.mmodding_lib.library.entities.goals;

import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class MoveToSpecificPosGoal extends MoveToTargetPosGoal {

	private final BooleanSupplier condition;
	private final Supplier<Vec3d> position;
	private final Runnable after;

	public MoveToSpecificPosGoal(PathAwareEntity mob, double speed, int range, BooleanSupplier condition, Supplier<Vec3d> position) {
		this(mob, speed, range, condition, position, null);
	}

	public MoveToSpecificPosGoal(PathAwareEntity mob, double speed, int range, int maxYDifference, BooleanSupplier condition, Supplier<Vec3d> position) {
		this(mob, speed, range, maxYDifference, condition, position, null);
	}

	public MoveToSpecificPosGoal(PathAwareEntity mob, double speed, int range, BooleanSupplier condition, Supplier<Vec3d> position, @Nullable Runnable after) {
		super(mob, speed, range);
		this.condition = condition;
		this.position = position;
		this.after = after;
	}

	public MoveToSpecificPosGoal(PathAwareEntity mob, double speed, int range, int maxYDifference, BooleanSupplier condition, Supplier<Vec3d> position, @Nullable Runnable after) {
		super(mob, speed, range, maxYDifference);
		this.condition = condition;
		this.position = position;
		this.after = after;
	}

	public Vec3d getPos() {
		return this.position.get();
	}

	@Override
	public boolean canStart() {
		return this.condition.getAsBoolean() && super.canStart();
	}

	@Override
	protected int getInterval(PathAwareEntity mob) {
		return 20;
	}

	@Override
	public boolean shouldContinue() {
		return this.condition.getAsBoolean() && this.tryingTime < 3600 && !this.hasReached() && this.isTargetPos(this.mob.world, this.targetPos);
	}

	@Override
	public void stop() {
		if (this.after != null) {
			this.after.run();
		}
	}

	@Override
	protected boolean isTargetPos(WorldView world, BlockPos pos) {
		return pos.getSquaredDistance(this.position.get().getX(), this.position.get().getY(), this.position.get().getZ()) <= 3;
	}
}
