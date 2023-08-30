package com.mmodding.mmodding_lib.library.blocks;

import com.mmodding.mmodding_lib.ducks.FlowableFluidDuckInterface;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItem;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class CustomFluidBlock extends FluidBlock implements BlockRegistrable, BlockWithItem {

    private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomFluidBlock(FlowableFluid fluid, Settings settings, @Nullable FluidState stillState, @Nullable IntFunction<FluidState> flowingStates) {
		this(((Supplier<FlowableFluid>) () -> {
			((FlowableFluidDuckInterface) fluid).mmodding_lib$putCustomStates(stillState, flowingStates);
			return fluid;
		}).get(), settings);
	}

    public CustomFluidBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }

	@Override
    public BlockItem getItem() {
        return null;
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
