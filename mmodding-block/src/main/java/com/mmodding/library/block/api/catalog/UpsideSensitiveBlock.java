package com.mmodding.library.block.api.catalog;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public abstract class UpsideSensitiveBlock<E extends Enum<E> & StringRepresentable> extends Block {

	public static <E extends Enum<E> & StringRepresentable> EnumProperty<E> createInfluenceProperty(Class<E> type) {
		return EnumProperty.create("influence", type);
	}

	@ApiStatus.OverrideOnly
	public abstract EnumProperty<E> getInfluenceProperty();

	public UpsideSensitiveBlock(Properties settings) {
		super(settings);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		return direction == Direction.UP ? state.setValue(this.getInfluenceProperty(), this.getInfluence(neighborState)) : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(this.getInfluenceProperty(), this.getInfluence(ctx.getLevel().getBlockState(ctx.getClickedPos().above())));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(this.getInfluenceProperty());
	}

	public abstract E getInfluence(BlockState state);
}
