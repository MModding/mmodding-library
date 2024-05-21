package com.mmodding.library.math.api;


import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

public class RelativeBlockPos extends BlockPos {

	private final Direction direction;

	private RelativeBlockPos(Direction direction, int i, int j, int k) {
		super(i, j, k);
		this.direction = direction;
	}

	private RelativeBlockPos(Direction direction, Vec3i pos) {
		super(pos);
		this.direction = direction;
	}

	public static Unsetted of(int i, int j, int k) {
		return new Unsetted(i, j, k);
	}

	public static Unsetted of(Vec3i vec3i) {
		return new Unsetted(vec3i);
	}

	public RelativeBlockPos top() {
		Direction transformed = RelativeDirection.TOP.transform(this.direction);
		return new RelativeBlockPos(this.direction, super.offset(transformed));
	}

	public RelativeBlockPos top(int i) {
		Direction transformed = RelativeDirection.TOP.transform(this.direction);
		return new RelativeBlockPos(this.direction, super.offset(transformed, i));
	}

	public RelativeBlockPos bottom() {
		Direction transformed = RelativeDirection.BOTTOM.transform(this.direction);
		return new RelativeBlockPos(this.direction, super.offset(transformed));
	}

	public RelativeBlockPos bottom(int i) {
		Direction transformed = RelativeDirection.BOTTOM.transform(this.direction);
		return new RelativeBlockPos(this.direction, super.offset(transformed, i));
	}

	public RelativeBlockPos front() {
		Direction transformed = RelativeDirection.FRONT.transform(this.direction);
		return new RelativeBlockPos(this.direction, super.offset(transformed));
	}

	public RelativeBlockPos front(int i) {
		Direction transformed = RelativeDirection.FRONT.transform(this.direction);
		return new RelativeBlockPos(this.direction, super.offset(transformed, i));
	}

	public RelativeBlockPos behind() {
		Direction transformed = RelativeDirection.BEHIND.transform(this.direction);
		return new RelativeBlockPos(this.direction, super.offset(transformed));
	}

	public RelativeBlockPos behind(int i) {
		Direction transformed = RelativeDirection.BEHIND.transform(this.direction);
		return new RelativeBlockPos(this.direction, super.offset(transformed, i));
	}

	public RelativeBlockPos left() {
		Direction transformed = RelativeDirection.LEFT.transform(this.direction);
		return new RelativeBlockPos(this.direction, super.offset(transformed));
	}

	public RelativeBlockPos left(int i) {
		Direction transformed = RelativeDirection.LEFT.transform(this.direction);
		return new RelativeBlockPos(this.direction, super.offset(transformed, i));
	}

	public RelativeBlockPos right() {
		Direction transformed = RelativeDirection.RIGHT.transform(this.direction);
		return new RelativeBlockPos(this.direction, super.offset(transformed));
	}

	public RelativeBlockPos right(int i) {
		Direction transformed = RelativeDirection.RIGHT.transform(this.direction);
		return new RelativeBlockPos(this.direction, super.offset(transformed, i));
	}

	public RelativeBlockPos rotateClockwise(Direction.Axis axis) {
		return new RelativeBlockPos(this.direction.rotateClockwise(axis), this);
	}

	public RelativeBlockPos rotateCounterClockWise(Direction.Axis axis) {
		return new RelativeBlockPos(this.direction.rotateCounterclockwise(axis), this);
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

	public static class Unsetted {

		private final Status status;

		private int i;
		private int j;
		private int k;

		private Vec3i vec3i;

		private Unsetted(int i, int j, int k) {
			this.status = Status.INTEGERS;
			this.i = i;
			this.j = j;
			this.k = k;
		}

		private Unsetted(Vec3i vec3i) {
			this.status = Status.VEC3I;
			this.vec3i = vec3i;
		}

		public RelativeBlockPos apply(Direction direction) {
			return switch (this.status) {
				case INTEGERS -> new RelativeBlockPos(direction, this.i, this.j, this.k);
				case VEC3I -> new RelativeBlockPos(direction, this.vec3i);
			};
		}

		private enum Status {
			INTEGERS,
			VEC3I
		}
	}
}
