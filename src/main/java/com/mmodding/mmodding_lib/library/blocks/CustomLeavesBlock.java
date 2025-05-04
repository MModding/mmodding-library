package com.mmodding.mmodding_lib.library.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.WorldAccess;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomLeavesBlock extends LeavesBlock implements BlockRegistrable, BlockWithItem {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    private BlockItem item = null;

    public CustomLeavesBlock(Settings settings) {
        this(settings, false);
    }

    public CustomLeavesBlock(Settings settings, boolean hasItem) {
        this(settings, hasItem, (ItemGroup) null);
    }

	public CustomLeavesBlock(Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(settings, hasItem, itemGroup != null ? new FabricItemSettings().group(itemGroup) : new FabricItemSettings());
	}

    public CustomLeavesBlock(Settings settings, boolean hasItem, Item.Settings itemSettings) {
        super(settings);
	    this.setDefaultState(
		    this.getDefaultState()
			    .with(this.getDistanceProperty(), this.getMaxDistance())
			    .with(CustomLeavesBlock.PERSISTENT, false)
			    .with(CustomLeavesBlock.WATERLOGGED, false)
	    );
        if (hasItem) this.item = new BlockItem(this, itemSettings);
    }

	public IntProperty getDistanceProperty() {
		return CustomLeavesBlock.DISTANCE;
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
		return state.get(this.getDistanceProperty()) == this.getMaxDistance() && !state.get(CustomLeavesBlock.PERSISTENT);
	}

	@Override
	protected boolean canDecay(BlockState state) {
		return !state.get(CustomLeavesBlock.PERSISTENT) && state.get(this.getDistanceProperty()) == this.getMaxDistance();
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
		world.setBlockState(pos, this.updateDistanceFromLogs(state, world, pos), Block.NOTIFY_ALL);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(CustomLeavesBlock.WATERLOGGED)) {
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
		builder.add(CustomLeavesBlock.PERSISTENT);
		builder.add(CustomLeavesBlock.WATERLOGGED);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidState = ctx.getWorld()
			.getFluidState(ctx.getBlockPos());

		BlockState blockState = this.getDefaultState()
			.with(CustomLeavesBlock.PERSISTENT, true)
			.with(CustomLeavesBlock.WATERLOGGED, fluidState.getFluid() == Fluids.WATER);

		return this.updateDistanceFromLogs(blockState, ctx.getWorld(), ctx.getBlockPos());
	}

	@Override
    public BlockItem getItem() {
        return this.item;
    }

    @Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
        this.registered.set(true);
    }
}
