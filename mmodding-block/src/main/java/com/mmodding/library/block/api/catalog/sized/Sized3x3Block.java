package com.mmodding.library.block.api.catalog.sized;

import net.minecraft.state.property.IntProperty;

public class Sized3x3Block extends SizedBlock {

	private static final IntProperty X_PROPERTY = IntProperty.of("x", 0, 2);
	private static final IntProperty Y_PROPERTY = IntProperty.of("y", 0, 2);
	private static final IntProperty Z_PROPERTY = IntProperty.of("z", 0, 2);

	public Sized3x3Block(Settings settings) {
		super(3, 3, 3, settings);
	}

	@Override
	protected IntProperty getXProperty() {
		return Sized3x3Block.X_PROPERTY;
	}

	@Override
	protected IntProperty getYProperty() {
		return Sized3x3Block.Y_PROPERTY;
	}

	@Override
	protected IntProperty getZProperty() {
		return Sized3x3Block.Z_PROPERTY;
	}
}
