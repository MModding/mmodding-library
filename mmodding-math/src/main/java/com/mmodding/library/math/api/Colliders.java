package com.mmodding.library.math.api;

import com.mmodding.library.math.impl.CollidersImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import org.jetbrains.annotations.ApiStatus;

import java.util.Set;
import java.util.stream.IntStream;

@ApiStatus.NonExtendable
public interface Colliders {

	static Colliders single(Vec3i collider) {
		return new CollidersImpl(Set.of(collider));
	}

	static Colliders column(Vec3i collider, Direction.Axis axis, int size) {
		return new CollidersImpl(IntStream.range(0, size).mapToObj(i -> collider.relative(axis, i)));
	}

	static Colliders box(Vec3i a, Vec3i b) {
		return new CollidersImpl(
			BlockPos.betweenClosedStream(
				Math.min(a.getX(), b.getX()),
				Math.min(a.getY(), b.getY()),
				Math.min(a.getZ(), b.getZ()),
				Math.max(a.getX(), b.getX()),
				Math.max(a.getY(), b.getY()),
				Math.max(a.getZ(), b.getZ())
			).map(pos -> new Vec3i(pos.getX(), pos.getY(), pos.getZ()))
		);
	}

	static Colliders combine(Colliders... others) {
		return new CollidersImpl(others);
	}

	Set<Vec3i> collisions();

	int getMaxX();

	int getMaxY();

	int getMaxZ();
}
