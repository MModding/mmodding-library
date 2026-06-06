package com.mmodding.library.portal.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.Heightmap;

public class PortalLookupImpl {

	// using the plain LevelReader#getHeight doesn't ensure chunk loading, we need to use a little trick here (thanks CelDaemon for pointing this out in the Fabricord)
	public static int getMotionBlockingForceLoad(LevelReader level, BlockPos lookupOrigin) {
		return level.getChunk(lookupOrigin).getHeight(Heightmap.Types.MOTION_BLOCKING, lookupOrigin.getX() & 15, lookupOrigin.getZ() & 15) + 1;
	}
}
