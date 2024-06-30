package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.portals.squared.CustomSquaredPortal;
import com.mmodding.mmodding_lib.library.portals.squared.CustomSquaredPortalBlock;
import com.mmodding.mmodding_lib.library.utils.InternalOf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@InternalOf(CustomSquaredPortalBlock.class)
public interface EntityDuckInterface {

	boolean mmodding_lib$isInCustomPortal();

	CustomSquaredPortal mmodding_lib$getCustomPortal();

	CustomSquaredPortalBlock mmodding_lib$getCustomPortalCache();

	void mmodding_lib$setInCustomPortal(CustomSquaredPortal squaredPortal, World world, BlockPos pos);
}
