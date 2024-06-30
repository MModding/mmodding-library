package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.blocks.interactions.data.FallingBlockInteractionData;
import com.mmodding.mmodding_lib.library.utils.InternalOf;
import net.minecraft.block.BlockState;

@InternalOf(FallingBlockInteractionData.class)
public interface FallingBlockEntityDuckInterface {

	BlockState mmodding_lib$getInitialBlockState();

	void mmodding_lib$setInitialBlockState(BlockState initialBlockState);

	float mmodding_lib$getFinalFallDistance();

	void mmodding_lib$setFinalFallDistance(float finalFallDistance);
}
