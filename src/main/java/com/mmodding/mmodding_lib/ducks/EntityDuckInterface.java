package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.portals.CustomSquaredPortalBlock;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface EntityDuckInterface {

	boolean isInCustomPortal();

	Pair<Block, CustomSquaredPortalBlock> getCustomPortalElements();

	CustomSquaredPortalBlock getCustomPortalCache();

	void setInCustomPortal(Block frameBlock, CustomSquaredPortalBlock portalBlock, World world, BlockPos pos);

	void tickCustomPortal();
}
