package com.mmodding.library.worldgen.impl.feature.helper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.block.enums.Thickness;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

import java.util.function.Consumer;

public class AdvancedDripstoneHelper {

	public static void getDripstoneThickness(Block pointedDripstoneBlock, Direction direction, int height, boolean merge, Consumer<BlockState> callback) {
		if (height >= 3) {
			callback.accept(getState(pointedDripstoneBlock, direction, Thickness.BASE));

			for(int i = 0; i < height - 3; ++i) {
				callback.accept(getState(pointedDripstoneBlock, direction, Thickness.MIDDLE));
			}
		}

		if (height >= 2) {
			callback.accept(getState(pointedDripstoneBlock, direction, Thickness.FRUSTUM));
		}

		if (height >= 1) {
			callback.accept(getState(pointedDripstoneBlock, direction, merge ? Thickness.TIP_MERGE : Thickness.TIP));
		}
	}

	public static void generatePointedDripstone(Block pointedDripstoneBlock, WorldAccess world, BlockPos pos, Direction direction, int height, boolean merge) {
		if (canReplace(pointedDripstoneBlock, world.getBlockState(pos.offset(direction.getOpposite())))) {
			BlockPos.Mutable mutable = pos.mutableCopy();
			getDripstoneThickness(pointedDripstoneBlock, direction, height, merge, state -> {
				if (state.isOf(pointedDripstoneBlock)) {
					state = state.with(PointedDripstoneBlock.WATERLOGGED, world.isWater(mutable));
				}

				world.setBlockState(mutable, state, 2);
				mutable.move(direction);
			});
		}
	}

	public static boolean generateDripstoneBlock(Block pointedDripstoneBlock, WorldAccess world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		if (blockState.isIn(BlockTags.DRIPSTONE_REPLACEABLE_BLOCKS)) {
			world.setBlockState(pos, pointedDripstoneBlock.getDefaultState(), 2);
			return true;
		} else {
			return false;
		}
	}

	public static BlockState getState(Block pointedDripstoneBlock, Direction direction, Thickness thickness) {
		return pointedDripstoneBlock.getDefaultState().with(PointedDripstoneBlock.VERTICAL_DIRECTION, direction).with(PointedDripstoneBlock.THICKNESS, thickness);
	}

	public static boolean canReplace(Block pointedDripstoneBlock, BlockState state) {
		return state.isOf(pointedDripstoneBlock) || state.isIn(BlockTags.DRIPSTONE_REPLACEABLE_BLOCKS);
	}
}
