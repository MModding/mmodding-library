package com.mmodding.library.block.api.catalog.sized;

public class DoubleWidthBlock extends FacingSizedBlock {

	private static final SizeManager MANAGER = SizeManager.create(2, 1, 2);

	public DoubleWidthBlock(Properties settings) {
		super(true, settings);
	}

	@Override
	protected SizeManager getSizeManager() {
		return MANAGER;
	}
}
