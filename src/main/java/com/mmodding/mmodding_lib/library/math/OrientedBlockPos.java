package com.mmodding.mmodding_lib.library.math;

import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.*;

public class OrientedBlockPos extends BlockPos {

	private final Direction direction;

	private OrientedBlockPos(Direction direction, int i, int j, int k) {
		super(i, j, k);
		this.direction = direction;
	}

	private OrientedBlockPos(Direction direction, double d, double e, double f) {
		super(d, e, f);
		this.direction = direction;
	}

	private OrientedBlockPos(Direction direction, Vec3d pos) {
		super(pos);
		this.direction = direction;
	}

	private OrientedBlockPos(Direction direction, Position pos) {
		super(pos);
		this.direction = direction;
	}

	private OrientedBlockPos(Direction direction, Vec3i pos) {
		super(pos);
		this.direction = direction;
	}

	public static WithoutOrientation of(int i, int j, int k) {
		return new WithoutOrientation(i, j, k);
	}

	public static WithoutOrientation of(double d, double e, double f) {
		return new WithoutOrientation(d, e, f);
	}

	public static WithoutOrientation of(Vec3d vec3d) {
		return new WithoutOrientation(vec3d);
	}

	public static WithoutOrientation of(Position position) {
		return new WithoutOrientation(position);
	}

	public static WithoutOrientation of(Vec3i vec3i) {
		return new WithoutOrientation(vec3i);
	}

	public OrientedBlockPos top() {
		Direction transformed = NonOriented.TOP.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed));
	}

	public OrientedBlockPos top(int i) {
		Direction transformed = NonOriented.TOP.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed, i));
	}

	public OrientedBlockPos bottom() {
		Direction transformed = NonOriented.BOTTOM.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed));
	}

	public OrientedBlockPos bottom(int i) {
		Direction transformed = NonOriented.BOTTOM.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed, i));
	}

	public OrientedBlockPos front() {
		Direction transformed = NonOriented.FRONT.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed));
	}

	public OrientedBlockPos front(int i) {
		Direction transformed = NonOriented.FRONT.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed, i));
	}

	public OrientedBlockPos behind() {
		Direction transformed = NonOriented.BEHIND.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed));
	}

	public OrientedBlockPos behind(int i) {
		Direction transformed = NonOriented.BEHIND.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed, i));
	}

	public OrientedBlockPos left() {
		Direction transformed = NonOriented.LEFT.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed));
	}

	public OrientedBlockPos left(int i) {
		Direction transformed = NonOriented.LEFT.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed, i));
	}

	public OrientedBlockPos right() {
		Direction transformed = NonOriented.RIGHT.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed));
	}

	public OrientedBlockPos right(int i) {
		Direction transformed = NonOriented.RIGHT.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed, i));
	}

	public OrientedBlockPos rotateClockwise(Direction.Axis axis) {
		return new OrientedBlockPos(this.direction.rotateClockwise(axis), this);
	}

	public OrientedBlockPos rotateCounterClockWise(Direction.Axis axis) {
		return new OrientedBlockPos(this.direction.rotateCounterclockwise(axis), this);
	}

	@Override
	@Deprecated
	public BlockPos up() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public BlockPos up(int i) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public BlockPos north() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public BlockPos north(int i) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public BlockPos down() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public BlockPos down(int i) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public BlockPos south() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public BlockPos south(int i) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public BlockPos west() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public BlockPos west(int i) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public BlockPos east() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public BlockPos east(int i) {
		throw new UnsupportedOperationException();
	}

	public static class WithoutOrientation {

		private final Status status;

		private int i;
		private int j;
		private int k;

		private double d;
		private double e;
		private double f;

		private Vec3d vec3d;

		private Position position;

		private Vec3i vec3i;

		private WithoutOrientation(int i, int j, int k) {
			this.status = Status.INTEGERS;
			this.i = i;
			this.j = j;
			this.k = k;
		}

		private WithoutOrientation(double d, double e, double f) {
			this.status = Status.DOUBLES;
			this.d = d;
			this.e = e;
			this.f = f;
		}

		private WithoutOrientation(Vec3d vec3d) {
			this.status = Status.VEC3D;
			this.vec3d = vec3d;
		}

		private WithoutOrientation(Position position) {
			this.status = Status.POSITION;
			this.position = position;
		}

		private WithoutOrientation(Vec3i vec3i) {
			this.status = Status.VEC3I;
			this.vec3i = vec3i;
		}

		public OrientedBlockPos apply(Direction direction) {
			return switch (this.status) {
				case INTEGERS -> new OrientedBlockPos(direction, this.i, this.j, this.k);
				case DOUBLES -> new OrientedBlockPos(direction, this.d, this.e, this.f);
				case VEC3D -> new OrientedBlockPos(direction, this.vec3d);
				case POSITION -> new OrientedBlockPos(direction, this.position);
				case VEC3I -> new OrientedBlockPos(direction, this.vec3i);
			};
		}

		private enum Status {
			INTEGERS,
			DOUBLES,
			VEC3D,
			POSITION,
			VEC3I
		}
	}
}
