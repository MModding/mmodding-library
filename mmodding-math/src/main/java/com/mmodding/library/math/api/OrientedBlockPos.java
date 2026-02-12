package com.mmodding.library.math.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

public class OrientedBlockPos extends BlockPos {

	private final Direction direction;

	private OrientedBlockPos(Direction direction, int i, int j, int k) {
		super(i, j, k);
		this.direction = direction;
	}

	private OrientedBlockPos(Direction direction, Vec3i pos) {
		super(pos);
		this.direction = direction;
	}

	public static OrientedBlockPos of(Direction direction, int i, int j, int k) {
		return new OrientedBlockPos(direction, i, j, k);
	}

	public static OrientedBlockPos of(Direction direction, Vec3i vec3i) {
		return new OrientedBlockPos(direction, vec3i);
	}

	public OrientedBlockPos top() {
		Direction transformed = DirectionMapper.TOP.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed));
	}

	public OrientedBlockPos top(int i) {
		Direction transformed = DirectionMapper.TOP.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed, i));
	}

	public OrientedBlockPos bottom() {
		Direction transformed = DirectionMapper.BOTTOM.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed));
	}

	public OrientedBlockPos bottom(int i) {
		Direction transformed = DirectionMapper.BOTTOM.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed, i));
	}

	public OrientedBlockPos front() {
		Direction transformed = DirectionMapper.FRONT.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed));
	}

	public OrientedBlockPos front(int i) {
		Direction transformed = DirectionMapper.FRONT.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed, i));
	}

	public OrientedBlockPos behind() {
		Direction transformed = DirectionMapper.BEHIND.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed));
	}

	public OrientedBlockPos behind(int i) {
		Direction transformed = DirectionMapper.BEHIND.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed, i));
	}

	public OrientedBlockPos left() {
		Direction transformed = DirectionMapper.LEFT.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed));
	}

	public OrientedBlockPos left(int i) {
		Direction transformed = DirectionMapper.LEFT.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed, i));
	}

	public OrientedBlockPos right() {
		Direction transformed = DirectionMapper.RIGHT.transform(this.direction);
		return new OrientedBlockPos(this.direction, super.offset(transformed));
	}

	public OrientedBlockPos right(int i) {
		Direction transformed = DirectionMapper.RIGHT.transform(this.direction);
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
}
