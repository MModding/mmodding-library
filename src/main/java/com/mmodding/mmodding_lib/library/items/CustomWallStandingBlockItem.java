package com.mmodding.mmodding_lib.library.items;

import net.minecraft.block.Block;
import net.minecraft.item.WallStandingBlockItem;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomWallStandingBlockItem extends WallStandingBlockItem implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomWallStandingBlockItem(Block standingBlock, Block wallBlock, Settings settings) {
        super(standingBlock, wallBlock, settings);
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
