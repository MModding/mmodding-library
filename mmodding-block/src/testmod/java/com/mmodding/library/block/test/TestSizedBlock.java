package com.mmodding.library.block.test;

import com.mmodding.library.block.api.catalog.sized.SizeManager;
import com.mmodding.library.block.api.catalog.sized.SizedBlock;

public class TestSizedBlock extends SizedBlock {

	private static final SizeManager MANAGER = SizeManager.create(2, 2, 2);

	public TestSizedBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected SizeManager getSizeManager() {
		return MANAGER;
	}
}
