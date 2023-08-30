package com.mmodding.mmodding_lib.library.items;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomBucketItem extends BucketItem implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomBucketItem(Fluid fluid, Settings settings) {
        super(fluid, settings);
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
