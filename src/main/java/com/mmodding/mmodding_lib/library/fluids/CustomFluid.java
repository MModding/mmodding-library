package com.mmodding.mmodding_lib.library.fluids;

import net.minecraft.block.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CustomFluid extends FlowableFluid implements FluidExtensions, FluidRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

	private final boolean source;
	private final FluidBlock block;

	public CustomFluid(AbstractBlock.Settings settings) {
		this.source = true;
		this.block = new FluidBlock(this, settings);
	}

	public CustomFluid() {
		this.source = false;
		this.block = null;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
		if (!this.source) {
			super.appendProperties(builder);
			builder.add(FlowableFluid.LEVEL);
		}
	}

	@Override
	public int getLevel(FluidState state) {
		return !this.source ? state.get(FlowableFluid.LEVEL) : 8;
	}

	@Override
	public boolean isSource(FluidState state) {
		return this.isSource();
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

	public boolean isSource() {
		return this.source;
	}

	@Nullable
	public FluidBlock getBlock() {
        return this.block;
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
