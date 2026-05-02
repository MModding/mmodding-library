package com.mmodding.library.block.api.catalog;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

/**
 * A {@link BedBlock} that does not contain a {@link BlockEntity}.
 */
public class SimpleBedBlock extends BedBlock {

	public SimpleBedBlock(Properties settings) {
		super(DyeColor.WHITE, settings);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return null;
	}

	/**
	 * {@link SimpleBedBlock} are made to be using simple block models instead.
	 * Vanilla beds are part of the only blocks which are not getting the opposite horizontal direction of the player.
	 * That should not be a thing. So simple bed blocks will follow the usual standard.
	 */
	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction facing = context.getHorizontalDirection().getOpposite();
		BlockPos pos = context.getClickedPos();
		BlockPos relative = pos.relative(facing);
		Level level = context.getLevel();
		return level.getBlockState(relative).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(relative)
			? this.defaultBlockState().setValue(FACING, facing)
			: null;
	}
}
