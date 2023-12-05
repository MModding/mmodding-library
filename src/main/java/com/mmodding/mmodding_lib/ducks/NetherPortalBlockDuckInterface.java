package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.portals.squared.CustomSquaredPortalBlock;
import com.mmodding.mmodding_lib.library.utils.InternalOf;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

@InternalOf(targets = CustomSquaredPortalBlock.class)
public interface NetherPortalBlockDuckInterface {

	BlockState mmodding_lib$getAbstractStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos);
}
