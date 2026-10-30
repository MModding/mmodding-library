package com.mmodding.library.block.test;

import com.mmodding.library.block.api.catalog.sized.FacingSizedBlock;
import com.mmodding.library.block.api.catalog.sized.SizeManager;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.BlockState;

public class TestFacingSizedBlock extends FacingSizedBlock {

	private static final SizeManager MANAGER = SizeManager.create(4, 4, 4);

	public TestFacingSizedBlock(Properties properties) {
		super(true, properties);
	}

	@Override
	protected SizeManager getSizeManager() {
		return MANAGER;
	}

	@Override
	protected Vec3i getPlacementOrigin(BlockState state) {
		return new Vec3i(1, 0, 1);
	}
}
