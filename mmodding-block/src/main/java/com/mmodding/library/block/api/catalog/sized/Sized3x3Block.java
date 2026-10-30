package com.mmodding.library.block.api.catalog.sized;

public class Sized3x3Block extends SizedBlock {

	private static final SizeManager MANAGER = SizeManager.create(3, 3, 3);

	public Sized3x3Block(Properties settings) {
		super(settings);
	}

	@Override
	protected SizeManager getSizeManager() {
		return MANAGER;
	}
}
