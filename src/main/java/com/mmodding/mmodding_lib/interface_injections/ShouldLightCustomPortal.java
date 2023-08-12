package com.mmodding.mmodding_lib.interface_injections;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface ShouldLightCustomPortal {

	default boolean shouldLightCustomPortalAt(World world, BlockPos pos, Direction direction) {
		throw new AssertionError();
	}
}
