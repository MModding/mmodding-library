package com.mmodding.mmodding_lib.library.helpers;

import java.util.function.Consumer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.block.enums.Thickness;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.util.DripstoneHelper;

public class CustomDripstoneHelper extends DripstoneHelper {

	public static double scaleHeightFromRadius(double radius, double scale, double heightScale, double bluntness) {
		return DripstoneHelper.scaleHeightFromRadius(radius, scale, heightScale, bluntness);
	}

	public static boolean canGenerateBase(StructureWorldAccess world, BlockPos pos, int height) {
		return DripstoneHelper.canGenerateBase(world, pos, height);
	}

	public static boolean canGenerate(WorldAccess world, BlockPos pos) {
		return DripstoneHelper.canGenerate(world, pos);
	}

	public static boolean canGenerateOrLava(WorldAccess world, BlockPos pos) {
		return DripstoneHelper.canGenerateOrLava(world, pos);
	}

	public static void getDripstoneThickness(Block block, Direction direction, int height, boolean merge, Consumer<BlockState> callback) {
		if (height >= 3) {
			callback.accept(CustomDripstoneHelper.getState(block, direction, Thickness.BASE));

			for(int i = 0; i < height - 3; ++i) {
				callback.accept(CustomDripstoneHelper.getState(block, direction, Thickness.MIDDLE));
			}
		}

		if (height >= 2) {
			callback.accept(CustomDripstoneHelper.getState(block, direction, Thickness.FRUSTUM));
		}

		if (height >= 1) {
			callback.accept(CustomDripstoneHelper.getState(block, direction, merge ? Thickness.TIP_MERGE : Thickness.TIP));
		}
	}

	public static void generatePointedDripstone(Block pointedDripstoneBlock, WorldAccess world, BlockPos pos, Direction direction, int height, boolean merge) {
		if (CustomDripstoneHelper.canReplace(pointedDripstoneBlock, world.getBlockState(pos.offset(direction.getOpposite())))) {
			BlockPos.Mutable mutable = pos.mutableCopy();

			CustomDripstoneHelper.getDripstoneThickness(pointedDripstoneBlock, direction, height, merge, state -> {
				if (state.isOf(pointedDripstoneBlock)) {
					state = state.with(PointedDripstoneBlock.WATERLOGGED, world.isWater(mutable));
				}

				world.setBlockState(mutable, state, Block.NOTIFY_LISTENERS);
				mutable.move(direction);
			});
		}
	}

	public static boolean generateDripstoneBlock(Block dripstoneBlock, WorldAccess world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		if (blockState.isIn(BlockTags.DRIPSTONE_REPLACEABLE_BLOCKS)) {
			world.setBlockState(pos, dripstoneBlock.getDefaultState(), Block.NOTIFY_LISTENERS);
			return true;
		} else {
			return false;
		}
	}

	public static BlockState getState(Block block, Direction direction, Thickness thickness) {
		return block.getDefaultState().with(PointedDripstoneBlock.VERTICAL_DIRECTION, direction).with(PointedDripstoneBlock.THICKNESS, thickness);
	}

	public static boolean canReplaceOrLava(Block dripstoneBlock, BlockState state) {
		return CustomDripstoneHelper.canReplace(dripstoneBlock, state) || state.isOf(Blocks.LAVA);
	}

	public static boolean canReplace(Block dripstoneBlock, BlockState state) {
		return state.isOf(dripstoneBlock) || state.isIn(BlockTags.DRIPSTONE_REPLACEABLE_BLOCKS);
	}

	public static boolean canGenerate(BlockState state) {
		return DripstoneHelper.canGenerate(state);
	}

	public static boolean isNeitherEmptyNorWater(BlockState state) {
		return DripstoneHelper.isNeitherEmptyNorWater(state);
	}

	public static boolean canGenerateOrLava(BlockState state) {
		return DripstoneHelper.canGenerateOrLava(state);
	}
}
