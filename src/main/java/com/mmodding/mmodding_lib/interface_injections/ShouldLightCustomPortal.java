package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.library.utils.ClassExtension;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@ClassExtension(AbstractFireBlock.class)
public interface ShouldLightCustomPortal {

	default boolean shouldLightCustomPortalAt(World world, BlockPos pos, Direction direction) {
		throw new AssertionError();
	}
}
