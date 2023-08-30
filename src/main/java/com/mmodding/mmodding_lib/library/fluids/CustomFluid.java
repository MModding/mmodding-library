package com.mmodding.mmodding_lib.library.fluids;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CustomFluid extends FlowableFluid implements FluidExtensions, FluidRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

	private final boolean source;

	public CustomFluid(boolean source) {
		this.source = source;
	}

	@Override
	public Item getBucketItem() {
		return this.getGroup().getBucket() != null ? this.getGroup().getBucket() : Items.AIR;
	}

	@Override
	protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
		return false;
	}

	@Override
	protected BlockState toBlockState(FluidState state) {
		return this.getGroup().getBlock().getDefaultState().with(FluidBlock.LEVEL, FlowableFluid.getBlockStateLevel(state));
	}

	@Override
	public boolean isSource(FluidState state) {
		return this.source;
	}

	@Override
	public boolean matchesType(Fluid fluid) {
		return this.getGroup().getStill() == fluid || this.getGroup().getFlowing() == fluid;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
		if (!this.source) {
			super.appendProperties(builder);
			builder.add(FlowableFluid.LEVEL);
		}
	}

	@Override
	public Fluid getStill() {
		return this.getGroup().getStill();
	}

	@Override
	public Fluid getFlowing() {
		return this.getGroup().getFlowing();
	}

	@Override
	protected void flow(WorldAccess world, BlockPos pos, BlockState blockState, Direction direction, FluidState fluidState) {
		if (direction == Direction.DOWN) {
			BlockState state = this.getCollisionHandler().getCollisionResult(world, pos, world.getBlockState(pos), pos, world.getFluidState(pos));
			if (!state.isAir()) {
				if (blockState.getBlock() instanceof FluidBlock) {
					world.setBlockState(pos, state, Block.NOTIFY_ALL);
					this.getCollisionHandler().afterCollision(world, pos, world.getBlockState(pos), pos, world.getFluidState(pos));
				}

				return;
			}
		}

		super.flow(world, pos, blockState, direction, fluidState);
	}

	@Override
	protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
		final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
		Block.dropStacks(state, world, pos, blockEntity);
	}

	@Override
	public int getLevel(FluidState state) {
		return !this.source ? state.get(FlowableFluid.LEVEL) : 8;
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
