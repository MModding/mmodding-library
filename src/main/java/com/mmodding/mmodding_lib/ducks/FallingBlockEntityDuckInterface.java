package com.mmodding.mmodding_lib.ducks;

import net.minecraft.block.BlockState;

public interface FallingBlockEntityDuckInterface {

	BlockState mmodding_lib$getInitialBlockState();

	void mmodding_lib$setInitialBlockState(BlockState initialBlockState);

	float mmodding_lib$getFinalFallDistance();

	void mmodding_lib$setFinalFallDistance(float finalFallDistance);
}
