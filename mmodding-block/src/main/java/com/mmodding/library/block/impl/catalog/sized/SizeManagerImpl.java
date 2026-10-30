package com.mmodding.library.block.impl.catalog.sized;

import com.mmodding.library.block.api.catalog.sized.SizeManager;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jspecify.annotations.Nullable;

public class SizeManagerImpl implements SizeManager {

	private final int xMaxSize;
	private final int yMaxSize;
	private final int zMaxSize;

	private final @Nullable IntegerProperty xProperty;
	private final @Nullable IntegerProperty yProperty;
	private final @Nullable IntegerProperty zProperty;

	public SizeManagerImpl(int xMaxSize, int yMaxSize, int zMaxSize) {
		if (xMaxSize < 1) {
			throw new IllegalArgumentException("xMaxSize must be greater than 0");
		}
		if (yMaxSize < 1) {
			throw new IllegalArgumentException("yMaxSize must be greater than 0");
		}
		if (zMaxSize < 1) {
			throw new IllegalArgumentException("zMaxSize must be greater than 0");
		}

		this.xMaxSize = xMaxSize;
		this.yMaxSize = yMaxSize;
		this.zMaxSize = zMaxSize;

		this.xProperty = this.xMaxSize != 1 ? IntegerProperty.create("x", 0, xMaxSize - 1) : null;
		this.yProperty = this.yMaxSize != 1 ? IntegerProperty.create("y", 0, yMaxSize - 1) : null;
		this.zProperty = this.zMaxSize != 1 ? IntegerProperty.create("z", 0, zMaxSize - 1) : null;
	}

	@Override
	public IntegerProperty getXProperty() {
		return this.xProperty;
	}

	@Override
	public IntegerProperty getYProperty() {
		return this.yProperty;
	}

	@Override
	public IntegerProperty getZProperty() {
		return this.zProperty;
	}

	@Override
	public void defineSizeProperties(StateDefinition.Builder<Block, BlockState> builder) {
		if (this.xProperty != null) builder.add(this.xProperty);
		if (this.yProperty != null) builder.add(this.yProperty);
		if (this.zProperty != null) builder.add(this.zProperty);
	}

	private BlockState setInnerX(BlockState state, int x) {
		return this.xProperty != null ? state.setValue(this.xProperty, x) : state;
	}

	private BlockState setInnerY(BlockState state, int y) {
		return this.yProperty != null ? state.setValue(this.yProperty, y) : state;
	}

	private BlockState setInnerZ(BlockState state, int z) {
		return this.zProperty != null ? state.setValue(this.zProperty, z) : state;
	}

	private int getInnerX(BlockState state) {
		return this.xProperty != null ? state.getValue(this.xProperty) : 0;
	}

	private int getInnerY(BlockState state) {
		return this.yProperty != null ? state.getValue(this.yProperty) : 0;
	}

	private int getInnerZ(BlockState state) {
		return this.zProperty != null ? state.getValue(this.zProperty) : 0;
	}

	@Override
	public int getLength(BlockState state) {
		return this.xMaxSize;
	}

	@Override
	public int getHeight(BlockState state) {
		return this.yMaxSize;
	}

	@Override
	public int getWidth(BlockState state) {
		return this.zMaxSize;
	}

	@Override
	public BlockState setInnerPos(BlockState state, Vec3i pos) {
		return this.setInnerZ(this.setInnerY(this.setInnerX(state, pos.getX()), pos.getY()), pos.getZ());
	}

	@Override
	public Vec3i getInnerPos(BlockState state) {
		return new Vec3i(this.getInnerX(state), this.getInnerY(state), this.getInnerZ(state));
	}
}
