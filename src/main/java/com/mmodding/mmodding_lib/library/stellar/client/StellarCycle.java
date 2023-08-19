package com.mmodding.mmodding_lib.library.stellar.client;

import com.mmodding.mmodding_lib.library.math.LinearFunction;
import com.mmodding.mmodding_lib.library.math.MathFunction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class StellarCycle {

	private final LinearFunction trajectory;
	private final long fullRotationTime;
	private final RegistryKey<World> worldKey;

	public StellarCycle(LinearFunction trajectory, long fullRotationTime, RegistryKey<World> worldKey) {
		this.trajectory = trajectory;
		this.fullRotationTime = fullRotationTime;
		this.worldKey = worldKey;
	}

	private StellarCycle of(float a, long fullRotationTime, RegistryKey<World> worldKey) {
		return new StellarCycle(MathFunction.linear(a), fullRotationTime, worldKey);
	}

	private StellarCycle of(float a, float b, long fullRotationTime, RegistryKey<World> worldKey) {
		return new StellarCycle(MathFunction.linear(a, b), fullRotationTime, worldKey);
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

	public float getYSkyAngle(long time) {
		return (float) this.trajectory.getY(this.getXSkyAngle(time));
	}
}
