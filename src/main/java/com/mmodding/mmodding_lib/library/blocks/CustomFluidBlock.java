package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BlockItem;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomFluidBlock extends FluidBlock implements BlockRegistrable, BlockWithItem {

    private final AtomicBoolean registered = new AtomicBoolean(false);

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
