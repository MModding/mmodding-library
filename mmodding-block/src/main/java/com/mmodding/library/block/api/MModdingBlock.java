package com.mmodding.library.block.api;

import com.mmodding.library.core.api.management.info.InjectedContent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

@InjectedContent(BlockBehaviour.class)
public interface MModdingBlock {

	default float getVelocityMultiplier(Level world, BlockPos pos, BlockState state) {
		return 1.0f;
	}
}
