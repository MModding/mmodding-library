package com.mmodding.library.block.test;

import com.mmodding.library.block.api.catalog.sized.SizeManager;
import com.mmodding.library.block.api.catalog.sized.SizedBlock;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.BlockState;

public class TestSizedBlock extends SizedBlock {

	private static final SizeManager MANAGER = SizeManager.create(3, 3, 3);

	public TestSizedBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected Vec3i getPlacementOrigin(BlockState state) {
		return new Vec3i(1, 0, 1);
	}

	@Override
	protected SizeManager getSizeManager() {
		return MANAGER;
	}
}
