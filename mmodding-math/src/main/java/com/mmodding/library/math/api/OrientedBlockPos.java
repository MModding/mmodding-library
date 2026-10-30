package com.mmodding.library.math.api;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

import java.util.Arrays;

/**
 * Moving the block position without knowing directions.
 * Consider you're facing a direction you don't know.
 * We define the relative (ux, uy, uz) as such:
 * - Stepping forwards follows -uz. (not +uz because NORTH follows the general -uz)
 * - Stepping backwards follows uz.
 * - Stepping left follows -ux.
 * - Stepping right follows +ux.
 * - Ascending to the sky follows +uy.
 * - Descending to the earth follows -uy.
 */
public class OrientedBlockPos extends BlockPos {

	private final Direction relativeX;
	private final Direction relativeY;
	private final Direction relativeZ;

	private OrientedBlockPos(Direction relativeX, Direction relativeY, Direction relativeZ, Vec3i vec3i) {
		super(vec3i.getX(), vec3i.getY(), vec3i.getZ());
		this.relativeX = relativeX;
		this.relativeY = relativeY;
		this.relativeZ = relativeZ;
	}

	private OrientedBlockPos(Direction relativeX, Direction relativeY, Direction relativeZ, int x, int y, int z) {
		super(x, y, z);
		this.relativeX = relativeX;
		this.relativeY = relativeY;
		this.relativeZ = relativeZ;
	}

	/**
	 * Orients a movement vector. Coordinates are permuted and possibly multiplied by -1 depending on the new orientation.
	 * @param front the direction given as "front"
	 * @param up the direction given as "up"
	 * @return the oriented movement vector
	 */
	public static OrientedBlockPos orientMotion(Direction front, Direction up, Vec3i vec3i) {
		return OrientedBlockPos.orientedOrigin(front, up).offset(vec3i);
	}

	/**
	 * Defines three relative axis for the origin.
	 * @param front the direction given as "front"
	 * @param up the direction given as "upside"
	 * @return the oriented origin
	 */
	public static OrientedBlockPos orientedOrigin(Direction front, Direction up) {
		return OrientedBlockPos.of(front, up, BlockPos.ZERO);
	}

	/**
	 * Defines three relative axis from the given position. Coordinates do not change here,
	 * while next offsets will be computed with the relative axis.
	 * @param front the direction given as "front"
	 * @param up the direction given as "upside"
	 * @param vec3i the coordinates
	 * @return the oriented block position
	 */
	public static OrientedBlockPos of(Direction front, Direction up, Vec3i vec3i) {
		return OrientedBlockPos.of(front, up, vec3i.getX(), vec3i.getY(), vec3i.getZ());
	}

	/**
	 * Defines three relative axis from the given position. Coordinates do not change here,
	 * while next offsets will be computed with the relative axis.
	 * @param front the direction given as "front"
	 * @param up the direction given as "upside"
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 * @return the oriented block position
	 */
	public static OrientedBlockPos of(Direction front, Direction up, int x, int y, int z) {
		// "north" is negative z, so we make "front" the negative relative z too
		Direction relativeZ = front.getOpposite();
		Direction relativeY = up;
		Direction.Axis last = Arrays.stream(Direction.Axis.VALUES)
			.filter(axis -> !axis.equals(relativeZ.getAxis()) && !axis.equals(relativeY.getAxis()))
			.findFirst()
			.orElseThrow();
		Vec3i uy = new Vec3i(relativeY.getStepX(), relativeY.getStepY(), relativeY.getStepZ());
		Vec3i uz = new Vec3i(relativeZ.getStepX(), relativeZ.getStepY(), relativeZ.getStepZ());
		Vec3i vecProd = uy.cross(uz);
		// if vecProd = ux, then last axis is positive, otherwise it's negative
		Direction relativeX = Direction.fromAxisAndDirection(
			last, vecProd.get(last) == 1 ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE
		);
		return new OrientedBlockPos(relativeX, relativeY, relativeZ, x, y, z);
	}

	public BlockPos defaultBase() {
		return new BlockPos(this.getX(), this.getY(), this.getZ());
	}

	public int getRelativeX() {
		return this.get(this.relativeX.getAxis()) * this.relativeX.getAxisDirection().getStep();
	}

	public int getRelativeY() {
		return this.get(this.relativeY.getAxis()) * this.relativeY.getAxisDirection().getStep();
	}

	public int getRelativeZ() {
		return this.get(this.relativeZ.getAxis()) * this.relativeZ.getAxisDirection().getStep();
	}

	@Override
	public OrientedBlockPos relative(Direction direction) {
		return new OrientedBlockPos(
			this.relativeX,
			this.relativeY,
			this.relativeZ,
			super.relative(direction)
		);
	}

	@Override
	public OrientedBlockPos relative(Direction direction, int steps) {
		return new OrientedBlockPos(
			this.relativeX,
			this.relativeY,
			this.relativeZ,
			super.relative(direction, steps)
		);
	}

	@Override
	public OrientedBlockPos offset(int x, int y, int z) {
		return this.relative(this.relativeX, x)
			.relative(this.relativeY, y)
			.relative(this.relativeZ, z);
	}

	@Override
	public OrientedBlockPos offset(Vec3i vec) {
		return this.offset(vec.getX(), vec.getY(), vec.getZ());
	}

	/**
	 * Relative Z--
	 */
	public OrientedBlockPos front() {
		return this.relative(this.relativeZ.getOpposite());
	}

	public OrientedBlockPos front(int step) {
		return this.relative(this.relativeZ.getOpposite(), step);
	}

	/**
	 * Relative Z++
	 */
	public OrientedBlockPos back() {
		return this.relative(this.relativeZ);
	}

	public OrientedBlockPos back(int steps) {
		return this.relative(this.relativeZ, steps);
	}


	/**
	 * Relative X--
	 */
	public OrientedBlockPos left() {
		return this.relative(this.relativeX.getOpposite());
	}

	public OrientedBlockPos left(int steps) {
		return this.relative(this.relativeX.getOpposite(), steps);
	}

	/**
	 * Relative X++
	 */
	public OrientedBlockPos right() {
		return this.relative(this.relativeX);
	}

	public OrientedBlockPos right(int steps) {
		return this.relative(this.relativeX, steps);
	}

	/**
	 * Relative Y--
	 */
	public OrientedBlockPos bottom() {
		return this.relative(this.relativeY.getOpposite());
	}

	public OrientedBlockPos bottom(int steps) {
		return this.relative(this.relativeY.getOpposite(), steps);
	}

	/**
	 * Relative Y++
	 */
	public OrientedBlockPos top() {
		return this.relative(this.relativeY);
	}

	public OrientedBlockPos top(int steps) {
		return this.relative(this.relativeY, steps);
	}

	@Override
	@Deprecated
	public BlockPos above() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public BlockPos above(int i) {
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
	public BlockPos below() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public BlockPos below(int i) {
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
