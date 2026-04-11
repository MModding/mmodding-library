package com.mmodding.library.block.api.catalog.sized;

import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class Sized3x3Block extends SizedBlock {

	private static final IntegerProperty X_PROPERTY = IntegerProperty.create("x", 0, 2);
	private static final IntegerProperty Y_PROPERTY = IntegerProperty.create("y", 0, 2);
	private static final IntegerProperty Z_PROPERTY = IntegerProperty.create("z", 0, 2);

	public Sized3x3Block(Properties settings) {
		super(3, 3, 3, settings);
	}

	@Override
	protected IntegerProperty getXProperty() {
		return Sized3x3Block.X_PROPERTY;
	}

	@Override
	protected IntegerProperty getYProperty() {
		return Sized3x3Block.Y_PROPERTY;
	}

	@Override
	protected IntegerProperty getZProperty() {
		return Sized3x3Block.Z_PROPERTY;
	}
}
