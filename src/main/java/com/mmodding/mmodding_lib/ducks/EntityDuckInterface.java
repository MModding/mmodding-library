package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.portals.CustomSquaredPortalBlock;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public interface EntityDuckInterface {

	boolean isInCustomPortal();

	Pair<Block, CustomSquaredPortalBlock> getCustomPortalElements();

	void setInCustomPortal(Block frameBlock, CustomSquaredPortalBlock portalBlock, BlockPos pos);

	void tickCustomPortal();
}
