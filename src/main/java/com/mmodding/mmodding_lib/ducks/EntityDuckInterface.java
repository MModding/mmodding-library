package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.portals.CustomSquaredPortalBlock;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface EntityDuckInterface {

	boolean mmodding_lib$isInCustomPortal();

	Pair<Block, CustomSquaredPortalBlock> mmodding_lib$getCustomPortalElements();

	CustomSquaredPortalBlock mmodding_lib$getCustomPortalCache();

	void mmodding_lib$setInCustomPortal(Block frameBlock, CustomSquaredPortalBlock portalBlock, World world, BlockPos pos);
}
