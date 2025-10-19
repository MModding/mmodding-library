package com.mmodding.library.block.api.catalog;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public abstract class UpsideSensitiveBlock<E extends Enum<E> & StringIdentifiable> extends Block {

	public static <E extends Enum<E> & StringIdentifiable> EnumProperty<E> createInfluenceProperty(Class<E> type) {
		return EnumProperty.of("influence", type);
	}

	@ApiStatus.OverrideOnly
	public abstract EnumProperty<E> getInfluenceProperty();

	public UpsideSensitiveBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return direction == Direction.UP ? state.with(this.getInfluenceProperty(), this.getInfluence(neighborState)) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(this.getInfluenceProperty(), this.getInfluence(ctx.getWorld().getBlockState(ctx.getBlockPos().up())));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(this.getInfluenceProperty());
	}

	public abstract E getInfluence(BlockState state);
}
