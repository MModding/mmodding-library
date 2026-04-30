package com.mmodding.library.block.test;

import com.mmodding.library.block.api.catalog.sized.FacingSizedBlock;
import com.mmodding.library.block.api.catalog.sized.SizedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class TestFacingSizedBlock extends FacingSizedBlock {

	public static final IntegerProperty X = SizedBlock.makeOfSize(SizeAxis.LENGTH, 2);
	public static final IntegerProperty Y = SizedBlock.makeOfSize(SizeAxis.HEIGHT, 2);
	public static final IntegerProperty Z = SizedBlock.makeOfSize(SizeAxis.WIDTH, 2);

	public TestFacingSizedBlock(Properties settings) {
		super(true, settings);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(X, Y, Z);
	}

	@Override
	protected BlockState setInnerX(BlockState state, int x) {
		return state.setValue(X, x);
	}

	@Override
	protected BlockState setInnerY(BlockState state, int y) {
		return state.setValue(Y, y);
	}

	@Override
	protected BlockState setInnerZ(BlockState state, int z) {
		return state.setValue(Z, z);
	}

	@Override
	protected int getInnerX(BlockState state) {
		return state.getValue(X);
	}

	@Override
	protected int getInnerY(BlockState state) {
		return state.getValue(Y);
	}

	@Override
	protected int getInnerZ(BlockState state) {
		return state.getValue(Z);
	}

	@Override
	protected int getLength() {
		return 2;
	}

	@Override
	protected int getHeight() {
		return 2;
	}

	@Override
	protected int getWidth() {
		return 2;
	}
}
