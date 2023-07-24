package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
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
	static BlockPos getTipPos(BlockState state, WorldAccess world, BlockPos pos, int range, boolean allowMerged) {
		return null;
	}

	@Invoker("isHeldByPointedDripstone")
	static boolean isHeldByPointedDripstone(BlockState state, WorldView world, BlockPos pos) {
		return false;
	}

	@Invoker("canGrow")
	static boolean canGrow(BlockState state, ServerWorld world, BlockPos pos) {
		return false;
	}

	@Invoker("canPlaceAtWithDirection")
	static boolean canPlaceAtWithDirection(WorldView world, BlockPos pos, Direction direction) {
		return false;
	}

	@Invoker("isTip")
	static boolean isTip(BlockState state, Direction direction) {
		return false;
	}

	@Invoker("canDripThrough")
	static boolean canDripThrough(BlockView world, BlockPos pos, BlockState blockState) {
		return false;
	}
}
