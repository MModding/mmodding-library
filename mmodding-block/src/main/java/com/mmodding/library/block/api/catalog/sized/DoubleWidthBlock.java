package com.mmodding.library.block.api.catalog.sized;

import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.Direction;

public class DoubleWidthBlock extends SizedBlock {

	private static final Property<Integer> X_PROPERTY = SizedBlock.makeOfSize(SizeAxis.LENGTH, 2);
	private static final Property<Integer> Y_PROPERTY = SizedBlock.makeOfSize(SizeAxis.HEIGHT, 1);
	private static final Property<Integer> Z_PROPERTY = SizedBlock.makeOfSize(SizeAxis.WIDTH, 2);

	private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public DoubleWidthBlock(Settings settings) {
		super(2, 1, 2, settings);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
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
