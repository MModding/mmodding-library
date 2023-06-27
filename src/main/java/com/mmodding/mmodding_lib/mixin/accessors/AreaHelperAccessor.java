package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.AreaHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AreaHelper.class)
public interface AreaHelperAccessor {

	@Accessor
	WorldAccess getWorld();

	@Accessor
	Direction.Axis getAxis();

	@Accessor
	Direction getNegativeDir();

	@Accessor
	int getFoundPortalBlocks();

	@Accessor("foundPortalBlocks")
	void setFoundPortalBlocks(int foundPortalBlocks);

	@Accessor
	BlockPos getLowerCorner();

	@Accessor("lowerCorner")
	void setLowerCorner(BlockPos lowerCorner);

	@Accessor
	int getHeight();

	@Accessor("height")
	void setHeight(int height);

	@Accessor
	int getWidth();

	@Accessor("width")
	void setWidth(int width);

	@Invoker("getHeight")
	int invokeGetHeight();

	@Invoker("getWidth")
	int invokeGetWidth();
}
