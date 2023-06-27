package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.blocks.CustomSquaredPortalBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public interface EntityDuckInterface {

	void setInCustomPortal(Block frameBlock, CustomSquaredPortalBlock portalBlock, BlockPos pos);

	void tickCustomPortal();
}
