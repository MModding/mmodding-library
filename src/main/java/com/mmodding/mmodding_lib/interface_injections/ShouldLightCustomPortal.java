package com.mmodding.mmodding_lib.interface_injections;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@InjectedInterface(AbstractFireBlock.class)
public interface ShouldLightCustomPortal {

	default boolean shouldLightCustomPortalAt(World world, BlockPos pos, Direction direction) {
		throw new AssertionError();
	}
}
