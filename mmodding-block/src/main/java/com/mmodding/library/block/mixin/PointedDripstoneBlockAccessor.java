package com.mmodding.library.block.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PointedDripstoneBlock.class)
public interface PointedDripstoneBlockAccessor {

	@Invoker("findTip")
	static BlockPos mmodding$getTipPos(BlockState state, LevelAccessor world, BlockPos pos, int range, boolean allowMerged) {
		return null;
	}

	@Invoker("isStalactiteStartPos")
	static boolean mmodding$isHeldByPointedDripstone(BlockState state, LevelReader world, BlockPos pos) {
		return false;
	}

	@Invoker("canTipGrow")
	static boolean mmodding$canGrow(BlockState state, ServerLevel world, BlockPos pos) {
		return false;
	}

	@Invoker("isValidPointedDripstonePlacement")
	static boolean mmodding$canPlaceAtWithDirection(LevelReader world, BlockPos pos, Direction direction) {
		return false;
	}

	@Invoker("isUnmergedTipWithDirection")
	static boolean mmodding$isTip(BlockState state, Direction direction) {
		return false;
	}

	@Invoker("canDripThrough")
	static boolean mmodding$canDripThrough(BlockGetter world, BlockPos pos, BlockState blockState) {
		return false;
	}
}
