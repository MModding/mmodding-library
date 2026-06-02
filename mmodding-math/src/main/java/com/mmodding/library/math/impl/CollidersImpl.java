package com.mmodding.library.math.impl;

import com.mmodding.library.math.api.Colliders;
import net.minecraft.core.Vec3i;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollidersImpl implements Colliders {

	private final Set<Vec3i> collisions;
	private final int maxX;
	private final int maxY;
	private final int maxZ;

	public CollidersImpl(Colliders... toMerge) {
		Set<Vec3i> set = new HashSet<>();
		for (Colliders colliders : toMerge) {
			set.addAll(colliders.collisions());
		}
		this(set);
	}

	public CollidersImpl(Stream<Vec3i> collisions) {
		this(collisions.collect(Collectors.toSet()));
	}

	public CollidersImpl(Set<Vec3i> collisions) {
		this.collisions = Collections.unmodifiableSet(collisions);
		this.maxX = this.collisions.stream().max(Comparator.comparingInt(Vec3i::getX)).map(Vec3i::getX).orElse(-1);
		this.maxY = this.collisions.stream().max(Comparator.comparingInt(Vec3i::getY)).map(Vec3i::getY).orElse(-1);
		this.maxZ = this.collisions.stream().max(Comparator.comparingInt(Vec3i::getZ)).map(Vec3i::getZ).orElse(-1);
	}

	@Override
	public Set<Vec3i> collisions() {
		return this.collisions;
	}

	@Override
	public int getMaxX() {
		return this.maxX;
	}

	@Override
	public int getMaxY() {
		return this.maxY;
	}

	@Override
	public int getMaxZ() {
		return this.maxZ;
	}
}
