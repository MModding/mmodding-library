package com.mmodding.library.block.api.catalog.sized;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

public class DoubleWidthBlock extends SizedBlock {

	private static final Property<Integer> X_PROPERTY = SizedBlock.makeOfSize(SizeAxis.LENGTH, 2);
	private static final Property<Integer> Y_PROPERTY = SizedBlock.makeOfSize(SizeAxis.HEIGHT, 1);
	private static final Property<Integer> Z_PROPERTY = SizedBlock.makeOfSize(SizeAxis.WIDTH, 2);

	private static final Property<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

	public DoubleWidthBlock(Properties settings) {
		super(2, 1, 2, settings);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
	}

	@Override
	protected Property<Integer> getXProperty() {
		return DoubleWidthBlock.X_PROPERTY;
	}

	@Override
	protected Property<Integer> getYProperty() {
		return DoubleWidthBlock.Y_PROPERTY;
	}

	@Override
	protected Property<Integer> getZProperty() {
		return DoubleWidthBlock.Z_PROPERTY;
	}
}
