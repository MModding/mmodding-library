package com.mmodding.library.block.api.catalog.sized;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class DoubleWidthBlock extends FacingSizedBlock {

	private static final Property<Integer> X_PROPERTY = SizedBlock.makeOfSize(SizeAxis.LENGTH, 2);
	private static final Property<Integer> Z_PROPERTY = SizedBlock.makeOfSize(SizeAxis.WIDTH, 2);

	public DoubleWidthBlock(Properties settings) {
		super(true, settings);
	}

	@Override
	protected BlockState setInnerX(BlockState state, int x) {
		return state.setValue(X_PROPERTY, x);
	}

	@Override
	protected BlockState setInnerY(BlockState state, int y) {
		return state;
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
		return 1;
	}

	@Override
	protected int getInnerZ(BlockState state) {
		return state.getValue(Z_PROPERTY);
	}

	@Override
	protected int getLength() {
		return 2;
	}

	@Override
	protected int getHeight() {
		return 1;
	}

	@Override
	protected int getWidth() {
		return 2;
	}
}
