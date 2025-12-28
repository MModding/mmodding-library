package com.mmodding.library.block.api.catalog;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

public class AdvancedLeavesBlock extends LeavesBlock {

	public AdvancedLeavesBlock(Settings settings) {
		super(settings);
		this.setDefaultState(
			this.getDefaultState()
				.with(this.getDistanceProperty(), this.getMaxDistance())
				.with(AdvancedLeavesBlock.PERSISTENT, false)
				.with(AdvancedLeavesBlock.WATERLOGGED, false)
		);
	}

	public IntProperty getDistanceProperty() {
		return AdvancedLeavesBlock.DISTANCE;
	}

	protected int getMaxDistance() {
		return 7;
	}

	protected boolean isLogValid(BlockState state) {
		return state.isIn(BlockTags.LOGS);
	}

	protected boolean areLeavesValid(BlockState state) {
		return state.isOf(this);
	}

	public boolean areLeavesConnected() {
		return true;
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return state.get(this.getDistanceProperty()) == this.getMaxDistance() && !state.get(AdvancedLeavesBlock.PERSISTENT);
	}



	@Override
	protected boolean shouldDecay(BlockState state) {
		return !state.get(AdvancedLeavesBlock.PERSISTENT) && state.get(this.getDistanceProperty()) == this.getMaxDistance();
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.setBlockState(pos, this.updateDistanceFromLogs(state, world, pos), Block.NOTIFY_ALL);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(AdvancedLeavesBlock.WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		int i = this.getDistanceFromLog(neighborState) + 1;
		if (i != 1 || state.get(this.getDistanceProperty()) != i) {
			world.scheduleBlockTick(pos, this, 1);
		}

		return state;
	}

	private BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
		int distance = this.getMaxDistance();
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		if (!this.areLeavesConnected()) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					for (int k = -1; k <= 1; k++) {
						mutable.set(pos.add(i, j, k));
						distance = Math.min(distance, this.getDistanceFromLog(world.getBlockState(mutable)) + 1);
						if (distance == 1) {
							break;
						}
					}
				}
			}
		}
		else {
			for (Direction direction : Direction.values()) {
				mutable.set(pos, direction);
				distance = Math.min(distance, this.getDistanceFromLog(world.getBlockState(mutable)) + 1);
				if (distance == 1) {
					break;
				}
			}
		}

		return state.with(this.getDistanceProperty(), distance);
	}

	private int getDistanceFromLog(BlockState state) {
		if (this.isLogValid(state)) {
			return 0;
		}
		else if (this.areLeavesValid(state)) {
			return state.get(this.getDistanceProperty());
		}
		else {
			return this.getMaxDistance();
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(this.getDistanceProperty());
		builder.add(AdvancedLeavesBlock.PERSISTENT);
		builder.add(AdvancedLeavesBlock.WATERLOGGED);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidState = ctx.getWorld()
			.getFluidState(ctx.getBlockPos());

		BlockState blockState = this.getDefaultState()
			.with(AdvancedLeavesBlock.PERSISTENT, true)
			.with(AdvancedLeavesBlock.WATERLOGGED, fluidState.getFluid() == Fluids.WATER);

		return this.updateDistanceFromLogs(blockState, ctx.getWorld(), ctx.getBlockPos());
	}
}
