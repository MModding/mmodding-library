package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PointedDripstoneBlock.class)
public interface PointedDripstoneBlockAccessor {

	@Invoker("getTipPos")
	static BlockPos invokeGetTipPos(BlockState state, WorldAccess world, BlockPos pos, int range, boolean allowMerged) {
		return null;
	}

	@Invoker("isHeldByPointedDripstone")
	static boolean invokeIsHeldByPointedDripstone(BlockState state, WorldView world, BlockPos pos) {
		return false;
	}

	@Invoker("canGrow")
	static boolean invokeCanGrow(BlockState state, ServerWorld world, BlockPos pos) {
		return false;
	}

	@Invoker("canPlaceAtWithDirection")
	static boolean invokeCanPlaceAtWithDirection(WorldView world, BlockPos pos, Direction direction) {
		return false;
	}

	@Invoker("isTip")
	static boolean invokeIsTip(BlockState state, Direction direction) {
		return false;
	}

	@Invoker("canDripThrough")
	static boolean invokeCanDripThrough(BlockView world, BlockPos pos, BlockState blockState) {
		return false;
	}
}
