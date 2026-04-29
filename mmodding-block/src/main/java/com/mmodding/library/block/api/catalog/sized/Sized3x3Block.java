package com.mmodding.library.block.api.catalog.sized;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class Sized3x3Block extends SizedBlock {

	private static final IntegerProperty X_PROPERTY = SizedBlock.makeOfSize(SizeAxis.LENGTH, 2);
	private static final IntegerProperty Y_PROPERTY = SizedBlock.makeOfSize(SizeAxis.HEIGHT, 2);
	private static final IntegerProperty Z_PROPERTY = SizedBlock.makeOfSize(SizeAxis.WIDTH, 2);

	public Sized3x3Block(Properties settings) {
		super(settings);
	}

	@Override
	protected BlockState setInnerX(BlockState state, int x) {
		return state.setValue(X_PROPERTY, x);
	}

	@Override
	protected BlockState setInnerY(BlockState state, int y) {
		return state.setValue(Y_PROPERTY, y);
	}

	@Override
	protected BlockState setInnerZ(BlockState state, int z) {
		return state.setValue(Z_PROPERTY, z);
	}

	@Override
	protected int getInnerX(BlockState state) {
		return state.getValue(X_PROPERTY);
	}

	@Override
	protected int getInnerY(BlockState state) {
		return state.getValue(Y_PROPERTY);
	}

	@Override
	protected int getInnerZ(BlockState state) {
		return state.getValue(Z_PROPERTY);
	}

	@Override
	protected int getLength() {
		return 3;
	}

	@Override
	protected int getHeight() {
		return 3;
	}

	@Override
	protected int getWidth() {
		return 3;
	}
}
