package com.mmodding.library.block.test;

import com.mmodding.library.block.api.catalog.sized.FacingSizedBlock;
import com.mmodding.library.block.api.catalog.sized.SizeManager;

public class TestFacingSizedBlock extends FacingSizedBlock {

	private static final SizeManager MANAGER = SizeManager.create(2, 2, 2);

	public TestFacingSizedBlock(Properties properties) {
		super(true, properties);
	}

	@Override
	protected SizeManager getSizeManager() {
		return MANAGER;
	}
}
