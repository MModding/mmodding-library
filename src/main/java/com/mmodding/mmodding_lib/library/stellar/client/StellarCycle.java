package com.mmodding.mmodding_lib.library.stellar.client;

import com.mmodding.mmodding_lib.library.math.MathFunction;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

public abstract class StellarCycle {

	protected final float baseXAngle;
	protected final float baseZAngle;
	protected final long fullRotationTime;
	protected final RegistryKey<World> worldKey;

	private StellarCycle(float baseXAngle, float baseZAngle, long fullRotationTime, Identifier dimensionIdentifier) {
		this.baseXAngle = baseXAngle;
		this.baseZAngle = baseZAngle;
		this.fullRotationTime = fullRotationTime;
		this.worldKey = RegistryKey.of(Registry.WORLD_KEY, dimensionIdentifier);
	}

	public static StellarCycle ofAngle(float angle, float baseXAngle, float baseZAngle, long fullRotationTime, Identifier dimensionIdentifier) {
		return new WithAngle(angle, baseXAngle, baseZAngle, fullRotationTime, dimensionIdentifier);
	}

	@ApiStatus.Experimental
	public static StellarCycle ofTrajectory(MathFunction trajectory, float baseXAngle, float baseZAngle, long fullRotationTime, Identifier dimensionIdentifier) {
		return new WithTrajectory(trajectory, baseXAngle, baseZAngle, fullRotationTime, dimensionIdentifier);
	}

	public void register(Identifier identifier) {
		RegistrationUtils.registerStellarCycle(identifier, this);
	}

	public float getBaseXAngle() {
		return this.baseXAngle;
	}

	public float getBaseZAngle() {
		return this.baseZAngle;
	}

	public long getFullRotationTime() {
		return this.fullRotationTime;
	}

	public RegistryKey<World> getWorldKey() {
		return this.worldKey;
	}

	public float getSkyXAngle(long time) {
		double a = MathHelper.fractionalPart((double) time / this.fullRotationTime - 0.25);
		double b = 0.5 - Math.cos(a * Math.PI) / 2.0;
		return (float) (a * 2.0 + b) / 3.0f;
	}

	public abstract float getSkyYAngle(long time);

	public static class WithAngle extends StellarCycle {

		private final float angle;

		private WithAngle(float angle, float baseXAngle, float baseZAngle, long fullRotationTime, Identifier dimensionIdentifier) {
			super(baseXAngle, baseZAngle, fullRotationTime, dimensionIdentifier);
			this.angle = angle / 360.0f;
		}

		@Override
		public float getSkyYAngle(long time) {
			return this.angle;
		}
	}

	@ApiStatus.Experimental
	public static class WithTrajectory extends StellarCycle {

		private final MathFunction trajectory;

		private WithTrajectory(MathFunction trajectory, float baseXAngle, float baseZAngle, long fullRotationTime, Identifier dimensionIdentifier) {
			super(baseXAngle, baseZAngle, fullRotationTime, dimensionIdentifier);
			this.trajectory = trajectory;
		}

		@Override
		public float getSkyYAngle(long time) {
			if (time >= this.fullRotationTime / 2) {
				return (float) (this.trajectory.getY(this.getSkyXAngle(this.fullRotationTime - time)));
			}
			else {
				return (float) (this.trajectory.getY(this.getSkyXAngle(time)));
			}
		}
	}
}
