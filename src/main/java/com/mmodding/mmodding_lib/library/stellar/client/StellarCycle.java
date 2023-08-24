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

	protected final long fullRotationTime;
	protected final RegistryKey<World> worldKey;

	private StellarCycle(long fullRotationTime, Identifier dimensionIdentifier) {
		this.fullRotationTime = fullRotationTime;
		this.worldKey = RegistryKey.of(Registry.WORLD_KEY, dimensionIdentifier);
	}

	public StellarCycle ofAngle(float angle, long fullRotationTime, Identifier dimensionIdentifier) {
		return new WithAngle(angle, fullRotationTime, dimensionIdentifier);
	}

	public StellarCycle ofTrajectory(MathFunction trajectory, long fullRotationTime, Identifier dimensionIdentifier) {
		return new WithTrajectory(trajectory, fullRotationTime, dimensionIdentifier);
	}

	public void register(Identifier identifier) {
		RegistrationUtils.registerStellarCycle(identifier, this);
	}

	public long getFullRotationTime() {
		return this.fullRotationTime;
	}

	public RegistryKey<World> getWorldKey() {
		return this.worldKey;
	}

	public float getXSkyAngle(long time) {
		double a = MathHelper.fractionalPart((double) time / this.fullRotationTime - 0.25);
		double b = 0.5 - Math.cos(a * Math.PI) / 2.0;
		return (float) (a * 2.0 + b) / 3.0f;
	}

	public abstract float getYSkyAngle();

	public static class WithAngle extends StellarCycle {

		private final float angle;

		private WithAngle(float angle, long fullRotationTime, Identifier dimensionIdentifier) {
			super(fullRotationTime, dimensionIdentifier);
			this.angle = angle / 360.0f;
		}

		@Override
		public float getYSkyAngle() {
			return this.angle;
		}
	}

	@ApiStatus.Experimental
	public static class WithTrajectory extends StellarCycle {

		private final MathFunction trajectory;

		private WithTrajectory(MathFunction trajectory, long fullRotationTime, Identifier dimensionIdentifier) {
			super(fullRotationTime, dimensionIdentifier);
			this.trajectory = trajectory;
		}

		@Override
		public float getYSkyAngle() {
			/* double a = MathHelper.fractionalPart(this.trajectory.getY(1) / 360.0f - 0.25);
			double b = 0.5 - Math.cos(a * Math.PI) / 2.0;
			return (float) (a * 2.0 + b) / 3.0f; */
			return 0;
		}
	}
}
