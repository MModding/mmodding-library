package com.mmodding.library.worldgen.impl.feature.helper;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

public class AdvancedDripstoneUtils {

	public static void getDripstoneThickness(Block pointedDripstoneBlock, Direction direction, int height, boolean merge, Consumer<BlockState> callback) {
		if (height >= 3) {
			callback.accept(getState(pointedDripstoneBlock, direction, DripstoneThickness.BASE));

			for(int i = 0; i < height - 3; ++i) {
				callback.accept(getState(pointedDripstoneBlock, direction, DripstoneThickness.MIDDLE));
			}
		}

		if (height >= 2) {
			callback.accept(getState(pointedDripstoneBlock, direction, DripstoneThickness.FRUSTUM));
		}

		if (height >= 1) {
			callback.accept(getState(pointedDripstoneBlock, direction, merge ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
		}
	}

	public static void generatePointedDripstone(Block pointedDripstoneBlock, LevelAccessor world, BlockPos pos, Direction direction, int height, boolean merge) {
		if (canReplace(pointedDripstoneBlock, world.getBlockState(pos.relative(direction.getOpposite())))) {
			BlockPos.MutableBlockPos mutable = pos.mutable();
			getDripstoneThickness(pointedDripstoneBlock, direction, height, merge, state -> {
				if (state.is(pointedDripstoneBlock)) {
					state = state.setValue(PointedDripstoneBlock.WATERLOGGED, world.isWaterAt(mutable));
				}

				world.setBlock(mutable, state, 2);
				mutable.move(direction);
			});
		}
	}

	public static boolean generateDripstoneBlock(Block pointedDripstoneBlock, LevelAccessor world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		if (blockState.is(BlockTags.DRIPSTONE_REPLACEABLE)) {
			world.setBlock(pos, pointedDripstoneBlock.defaultBlockState(), 2);
			return true;
		} else {
			return false;
		}
	}

	public static BlockState getState(Block pointedDripstoneBlock, Direction direction, DripstoneThickness thickness) {
		return pointedDripstoneBlock.defaultBlockState().setValue(PointedDripstoneBlock.TIP_DIRECTION, direction).setValue(PointedDripstoneBlock.THICKNESS, thickness);
	}

	public static boolean canReplace(Block pointedDripstoneBlock, BlockState state) {
		return state.is(pointedDripstoneBlock) || state.is(BlockTags.DRIPSTONE_REPLACEABLE);
	}
}
